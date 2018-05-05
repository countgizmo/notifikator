(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]))

(defn message
  [{:keys [title text class]}]
  [:div {:class class}
   [:div title
    [:p text]]])

(defn messages-container
  []
  (let [messages (re-frame/subscribe [::subs/messages])]
    [:div.messages
     (map message @messages)]))
