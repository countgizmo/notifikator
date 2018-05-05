(ns notifikator.events
  (:require [re-frame.core :as re-frame]
            [notifikator.db :as db]
            [notifikator.util :refer [remove-by-id next-msg-id]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn handle-message
  ([db effect flavor] (handle-message db (conj effect flavor)))
  ([db [_ title description flavor]]
   (let [id (next-msg-id db)
         msg {:id id
              :title title
              :description description
              :class flavor}]
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
