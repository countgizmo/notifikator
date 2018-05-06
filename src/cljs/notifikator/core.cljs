(ns notifikator.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [notifikator.events :as events]
            [notifikator.views :as views]
            [notifikator.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/test-view]
                  (.getElementById js/document "app")))

(defn dispatch-timer-event
  []
  (re-frame/dispatch [::events/clean-old-messages]))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (defonce do-timer (js/setInterval dispatch-timer-event 1000))
  (dev-setup)
  (mount-root))
