(ns notifikator.events
  (:require [re-frame.core :as re-frame]
            [notifikator.db :as db]))


(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
  ::spawn-message
  (fn [{:keys [db]} [_ flavor]]
    (let [msg {:id 1
               :title "Please note"
               :description "Your fridge is running"
               :class flavor}]
      (update db :messages conj msg))))
