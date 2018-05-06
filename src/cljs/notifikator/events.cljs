(ns notifikator.events
  (:require [re-frame.core :as re-frame]
            [notifikator.db :as db]
            [notifikator.util :refer [remove-by-id
                                      next-msg-id
                                      add-ms-to-now
                                      remove-by-time]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn handle-message
  ([db effect flavor]
   (let [effect (assoc-in effect [1 :flavor] (str flavor))]
     (handle-message db effect)))
  ([db [_ message]]
   (let [id (next-msg-id db)
         msg (conj message
                {:id id
                 :destroy-at (add-ms-to-now (:ttl message))})]
     (update db :messages conj msg))))

(re-frame/reg-event-db
  ::spawn-message
  (fn [db effect]
    (handle-message db effect)))

(re-frame/reg-event-db
  ::spawn-info-message
  (fn [db effect]
    (handle-message db effect "info message")))

(re-frame/reg-event-db
  ::spawn-warning-message
  (fn [db effect]
    (handle-message db effect "warning message")))

(re-frame/reg-event-db
  ::spawn-error-message
  (fn [db effect]
    (handle-message db effect "error message")))

(re-frame/reg-event-db
  ::close-message
  (fn [db [_ id]]
    (update db :messages remove-by-id id)))

(re-frame/reg-cofx
  :now
  (fn [coeffects _]
    (assoc coeffects :now (js/Date.))))

(re-frame/reg-event-fx
  ::clean-old-messages
  [(re-frame/inject-cofx :now)]
  (fn [{:keys [db now]} _]
    {:db (update db :messages remove-by-time now)}))
