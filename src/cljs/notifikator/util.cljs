(ns notifikator.util)

(defn remove-by-id
  "Looking for an element with the specified id and removing it
   from the collection."
  [coll id]
  (remove #(= id (:id %)) coll))

(defn next-msg-id
  "Return 0 if there are no messages.
   If not empty, find the max ID and return its incremente value."
  [{:keys [messages]}]
  (if (or (empty? messages)
          (nil? messages))
    0
    (-> (apply max-key :id messages)
        :id
        inc)))

(defn add-ms-to-now
  [ms]
  (when ms
    (let [now (js/Date.)]
      (js/Date. (+ (.getTime now) ms)))))

(defn remove-by-time
  "Removing all elements that have destroy-at value greater or equal to now."
  [coll t]
  (remove
    (fn [msg]
      (if-let [destroy-at (:destroy-at msg)]
        (>= t destroy-at)
        false))
    coll))
