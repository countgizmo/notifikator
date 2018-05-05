(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div "Hello from " @name]))
