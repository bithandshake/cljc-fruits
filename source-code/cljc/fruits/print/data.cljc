
(ns fruits.print.data)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn map-kv
  ; @description
  ; Prints the given 'n' map to the console, displaying each key-value pair on a separate row.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (map-kv {:a "A" :b "B"})
  ; =>
  ; (println "a:" "A")
  ; (println "b:" "B")
  [n]
  (doseq [[k v] n]
         (if (keyword? k)
             (println (-> k name (str ":")) (str v))
             (println (-> k      (str ":")) (str v)))))
