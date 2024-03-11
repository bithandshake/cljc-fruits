
(ns fruits.shorthand.apply
    (:require [fruits.map.api :as map]
              [fruits.vector.api :as vector]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-shorthand-key
  ; @description
  ; Converts the given 'n' value into longhand form (map) in case it is provided in shorthand form (not as a map).
  ;
  ; @param (* or map) n
  ; @param (*) shorthand-key
  ;
  ; @usage
  ; (apply-shorthand-key "A" :a)
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (apply-shorthand-key {:a "A"} :a)
  ; =>
  ; {:a "A"}
  ;
  ; @return (map)
  [n shorthand-key]
  (map/to-longhand n shorthand-key))

(defn apply-shorthand-map
  ; @description
  ; Converts nested values within the given 'n' map into longhand form (map) in case they are provided in shorthand form (not as a map),
  ; using the given shorthand map as the blueprint for the data structure.
  ;
  ; @param (map) n
  ; @param (map) shorthand-map
  ;
  ; @usage
  ; (apply-shorthand-map {:a "A/B"} {:a :b})
  ; =>
  ; {:a {:b "A/B"}}
  ;
  ; @usage
  ; (apply-shorthand-map {:a {:b "A/B/C"}} {:a {:b :c}})
  ; =>
  ; {:a {:b {:c "A/B/C"}}}
  ;
  ; @usage
  ; (apply-shorthand-map {:a ["A/B"]} {:a :b})
  ; =>
  ; {:a [{:b "A/B"}]}
  ;
  ; @usage
  ; (apply-shorthand-map {:a [{:b "A/B/C"}]} {:a {:b :c}})
  ; =>
  ; {:a [{:b {:c "A/B/C"}}]}
  ;
  ; @return (map)
  [n shorthand-map]
  (let [shorthand-map (mixed/to-map shorthand-map)]
       (letfn [(f0 [v shorthand]   (cond (-> shorthand map?)  (-> v)
                                         (-> shorthand some?) (-> v (apply-shorthand-key       shorthand))))
               (f1 [v shorthand]   (cond (-> shorthand map?)  (-> v (apply-shorthand-map       shorthand))
                                         (-> shorthand some?) (-> v (apply-shorthand-key       shorthand))))
               (f2 [v shorthand]   (cond (-> v vector?)       (-> v (vector/update-all-item f2 shorthand))
                                         (-> v map?)          (-> v (f1                        shorthand))
                                         (-> v some?)         (-> v (f0                        shorthand))))
               (f3 [n k shorthand] (cond (-> n vector?)       (-> n (vector/update-all-item f2 shorthand))
                                         (-> n map?)          (-> n (map/update-some      k f2 shorthand))
                                         (-> n some?)         (-> n (f2                        shorthand))))]
              (reduce-kv f3 n shorthand-map))))
