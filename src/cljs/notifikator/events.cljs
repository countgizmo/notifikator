(ns notifikator.events
  (:require [re-frame.core :as re-frame]
            [notifikator.db :as db]
            ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
