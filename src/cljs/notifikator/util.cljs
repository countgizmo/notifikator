(ns notifikator.util)

(defn remove-by-id
  [coll id]
  (remove #(= id (:id %)) coll))
