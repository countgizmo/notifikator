(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            [notifikator.events :as events]))

(defn message
  [{:keys [id title description class]}]
  ^{:key (str "msg-" id)}
  [:div {:class class}
   [:div {:class "title"} title]
   [:div {:class "description"} description]])

(defn messages-container
  []
  (when-let [messages (re-frame/subscribe [::subs/messages])]
    [:div.messages
     (map message @messages)]))


;;; The Control Panel for playing with the messages
;;; Will not go to prod! ;)

(defn generate-info-message
  []
  (let [event [::events/spawn-message "info message"]]
    [:button
     {:on-click
       #(re-frame.core/dispatch event)}
     "Spawn Info Message!"]))

(defn playground
  []
  [:div.playground
   (generate-info-message)])

(defn test-view
  []
  ^{:key "wold-is-a-simulation"}
  [:div.world
   (playground)
   (messages-container)])
