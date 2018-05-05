(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            [notifikator.events :as events]))

(defn close-button
  [id]
  [:div {:class "close"
         :on-click #(re-frame.core/dispatch [::events/close-message id])}
   "X"])

(defn message
  [{:keys [id title description class]}]
  ^{:key (str "msg-" id)}
  [:div {:class class}
   [:div {:class "title"} title
    (close-button id)]
   [:div {:class "description"} description]])

(defn messages-container
  "If there are messages in the app-state create a message component
   for each of them. Otherwise return nil. Which is fine for Hiccup."
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
