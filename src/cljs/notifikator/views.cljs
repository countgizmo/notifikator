(ns notifikator.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            [notifikator.events :as events]))

(defn close-button
  [id]
  [:div {:class "close"
         :on-click #(re-frame.core/dispatch [::events/close-message id])}
   "X"])

(defn message
  [ind {:keys [id title description class]}]
  ^{:key (str "msg-" id)}
  [:div {:class class}
   [:div {:class "title"} title (close-button id)]
   [:div {:class "description"} description]])

(defn messages-container
  "If there are messages in the app-state create a message component
   for each of them. Otherwise return nil. Which is fine for Hiccup."
  []
  (when-let [messages (re-frame/subscribe [::subs/messages])]
    [:div {:class "messages-container"}
     (map-indexed message @messages)]))


;;; The Control Panel for playing with the messages
;;; Will not go to prod! ;)

(defn generate-message-button
  [title event]
  [:button
     {:on-click #(re-frame.core/dispatch event)}
     title])

(defn generate-info-message
  [title description]
  [generate-message-button
    "Spawn Info Message!"
    [::events/spawn-info-message title description]])

(defn generate-warning-message
  [title description]
  [generate-message-button
    "Spawn Warning Message!"
    [::events/spawn-warning-message title description]])

(defn generate-error-message
  [title description]
  [generate-message-button
    "Spawn Error Message!"
    [::events/spawn-error-message title description]])

(defn playground
  []
  (let [title (reagent/atom "Title")
        description (reagent/atom "Description")]
    (fn []
      [:div.playground
       [:div
        [:input {:type :text
                 :value @title
                 :on-change #(reset! title (-> % .-target .-value))}]
        [:div
         [:textarea {:value @description
                     :on-change #(reset! description (-> % .-target .-value))}]]]
       [:div.buttons
        [generate-info-message @title @description]
        [generate-warning-message @title @description]
        [generate-error-message @title @description]]])))

(defn test-view
  []
  ^{:key "wold-is-a-simulation"}
  [:div.world
   [playground]
   [messages-container]])
