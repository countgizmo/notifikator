(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]))

(defn message
  [{:keys [title description class]}]
  [:div {:class class}
   [:div {:class "title"} title]
   [:div {:class "description"} description]])

(defn messages-container
  []
  (when-let [messages (re-frame/subscribe [::subs/messages])]
    [:div.messages
     (map message @messages)]))
