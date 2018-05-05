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
  (reagent/render [views/messages-container]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
