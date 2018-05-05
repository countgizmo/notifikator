(ns notifikator.views
  (:require [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            [notifikator.events :as events]
            [notifikator.util :refer [top-position-px-from-ind]]))

(defn close-button
  [id]
  [:div {:class "close"
         :on-click #(re-frame.core/dispatch [::events/close-message id])}
   "X"])

(defn message
  [ind {:keys [id title description class]}]
  ^{:key (str "msg-" id)}
  [:div {:class class
         :style {:top (top-position-px-from-ind ind)}}
   [:div {:class "title"} title (close-button id)]
   [:div {:class "description"} description]])

(defn messages-container
  "If there are messages in the app-state create a message component
   for each of them. Otherwise return nil. Which is fine for Hiccup."
  []
  (when-let [messages (re-frame/subscribe [::subs/messages])]
    [:div.messages
     (map-indexed message @messages)]))


;;; The Control Panel for playing with the messages
;;; Will not go to prod! ;)

(defn generate-message-button
  [title event]
  [:button
     {:on-click #(re-frame.core/dispatch event)}
     title])

(defn generate-info-message
  []
  (generate-message-button
    "Spawn Info Message!"
    [::events/spawn-message "info message"]))

(defn generate-warning-message
  []
  (generate-message-button
    "Spawn Warning Message!"
    [::events/spawn-message "warning message"]))

(defn generate-error-message
  []
  (generate-message-button
    "Spawn Error Message!"
    [::events/spawn-message "error message"]))

(defn playground
  []
  [:div.playground
   (generate-info-message)
   (generate-warning-message)
   (generate-error-message)])

(defn test-view
  []
  ^{:key "wold-is-a-simulation"}
  [:div.world
   (playground)
   (messages-container)])
