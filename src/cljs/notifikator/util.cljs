(ns notifikator.util
  (:require [goog.dom :as dom]))

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

(defn calculate-offset-of
  "Find a message element by id based on the index.
   Get the message's Y coordinate and height value.
   Plust them together to get the offset for the next message."
  [ind]
  (let [el (nth (array-seq (dom/getElementsByClass "message")) ind)]
    (if-let [rect (.getBoundingClientRect el)]
      (+ (.-y rect) (.-height rect))
      0)))

(defn num->px
  [num]
  (str num "px"))

(defn top-position-px-from-ind
  "The higher the index the lower the top position.
   Also inserts additional margin. If not specified the default
   margin is 10px. This is the value that gets added to the offset.
   Return a CSS friendly string in px."
   ([ind] (top-position-px-from-ind ind 10))
   ([ind margin]
    (num->px
      (if (<= ind 0)
        margin
        (+ margin (calculate-offset-of (dec ind)))))))
