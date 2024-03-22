
(ns fruits.shorthand.apply
    (:require [fruits.map.api    :as map]
              [fruits.vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn apply-shorthand-key
  ; @description
  ; Converts the given 'n' value into longhand form (map) in case it is provided in shorthand form (not as a map).
  ;
  ; @param (* or map) n
  ; @param (*) shorthand
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
  [n shorthand]
  (cond (-> n map?) (-> n)
        (-> n nil?) (-> n)
        (-> shorthand keyword?) {shorthand n}
        (-> shorthand string?)  {shorthand n}
        (-> shorthand number?)  {shorthand n}
        :return n))

(defn apply-shorthand-map
  ; @description
  ; Converts nested values within the given 'n' map into longhand form (map) in case they are provided in shorthand form (not as a map),
  ; using the given shorthand map as the blueprint for the data structure.
  ;
  ; @param (map) n
  ; @param (map) shorthand
  ;
  ; @usage
  ; (apply-shorthand-map "A" :a)
  ; =>
  ; {:a "A"}
  ;
  ; @usage
  ; (apply-shorthand-map ["A" "B" "C"] [:a])
  ; =>
  ; [{:a "A"} {:a "B"} {:a "C"}]
  ;
  ; @usage
  ; (apply-shorthand-map {:a "A"} {:a :b})
  ; =>
  ; {:a {:b "A"}}
  ;
  ; @usage
  ; (apply-shorthand-map {:a [{:b "A"}]} {:a [{:b :c}]})
  ; =>
  ; {:a [{:b {:c "A"}}]}
  ;
  ; @return (map)
  [n shorthand]
  (letfn [(f0 [n k v]       (map/update-some n k f3 v))
          (f1 [n shorthand] (reduce-kv f0 n shorthand))
          (f2 [n shorthand] (if-some [shorthand (-> shorthand first)] (-> n (vector/update-all-item f3 shorthand))))
          (f3 [n shorthand] (cond (and (-> shorthand vector?) (-> n vector?)) (f2 n shorthand)
                                  (and (-> shorthand map?)    (-> n map?))    (f1 n shorthand)
                                  :to-longhand (apply-shorthand-key n shorthand)))]
         (f3 n shorthand)))
