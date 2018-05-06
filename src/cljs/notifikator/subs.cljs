(ns notifikator.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::messages
  (fn [{:keys [messages]}] messages))
