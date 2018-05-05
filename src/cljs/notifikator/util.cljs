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

(defn top-position-px-from-ind
  "The higher the index the lower the top position.
   Return a CSS friendly string in px."
  [ind offset]
  (str (* ind offset) "px"))
