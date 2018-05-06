(ns notifikator.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [notifikator.subs :as subs]
            [notifikator.events :as events]
            [clojure.string :refer [blank?]]))

(defn close-button
  [id]
  [:div {:class "close"
         :on-click #(re-frame.core/dispatch [::events/close-message id])}
   "X"])

(def css-transition-group
  (reagent/adapt-react-class js/React.addons.CSSTransitionGroup))

(defn message
  [ind {:keys [id title description flavor]}]
  ^{:key (str "msg-" id)}
  [:div {:class flavor}
   [:div {:class "title"} title (close-button id)]
   [:div {:class "description"} description]])

(defn messages-container
  "If there are messages in the app-state create a message component
   for each of them. Otherwise return nil. Which is fine for Hiccup."
  []
  (let [messages (re-frame/subscribe [::subs/messages])]
    [:div {:class "messages-container"}
     [css-transition-group {:transitionName "message"
                            :transitionEnterTimeout 500
                            :transitionLeaveTimeout 300}
      (map-indexed message @messages)]]))

;;; The Control Panel for playing with the messages
;;; Will not go to prod! ;)

(defn generate-message-button
  [title event]
  [:button
     {:on-click #(re-frame.core/dispatch event)}
     title])

(defn generate-info-message
  [title description ttl]
  [generate-message-button
    "Spawn Info Message!"
    [::events/spawn-info-message
     {:title title :description description
      :ttl (when-not (blank? ttl) (int ttl))}]])

(defn generate-warning-message
  [title description ttl]
  [generate-message-button
    "Spawn Warning Message!"
    [::events/spawn-warning-message
     {:title title :description description
       :ttl (when-not (blank? ttl) (int ttl))}]])

(defn generate-error-message
  [title description ttl]
  [generate-message-button
    "Spawn Error Message!"
    [::events/spawn-error-message
     {:title title :description description
      :ttl (when-not (blank? ttl) (int ttl))}]])

(defn playground
  []
  (let [title (reagent/atom "Title")
        description (reagent/atom "Description")
        ttl (reagent/atom "")]
    (fn []
      [:div.playground
       [:div
        [:input {:type :text
                 :value @title
                 :on-change #(reset! title (-> % .-target .-value))}]
        [:div
         [:textarea {:value @description
                     :on-change #(reset! description (-> % .-target .-value))}]]
        [:div
         [:div "TTL (ms):"]
         [:input {:type :text
                  :value @ttl
                  :on-change #(reset! ttl (-> % .-target .-value))}]]]
       [:div.buttons
        [generate-info-message @title @description @ttl]
        [generate-warning-message @title @description @ttl]
        [generate-error-message @title @description @ttl]]])))

(defn test-view
  []
  [:div.world
   [playground]
   [messages-container]])
