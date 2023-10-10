
(ns map.inherit)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inherit
  ; @param (map) n
  ; @param (vector) keys
  ;
  ; @usage
  ; (inherit {:a "A" :b "B" :c "C"} [:a :b])
  ;
  ; @example
  ; (inherit {:a "A" :b "B" :c "C"} [:a :b])
  ; =>
  ; {:a "A" :b "B"}
  ;
  ; @return (map)
  [n keys]
  (select-keys n keys))

(defn inherit-in
  ; @param (map) n
  ; @param (vectors in vector) keys
  ;
  ; @usage
  ; (inherit-in {:a {:value "A"} :b {:value "B"} :c {:value "C"}} [[:a :value] [:b :value]])
  ;
  ; @example
  ; (inherit-in {:a {:value "A"} :b {:value "B"} :c {:value "C"}} [[:a :value] [:b :value]])
  ; =>
  ; {:a {:value "A"} :b {:value "B"}}
  ;
  ; @return (map)
  [n paths]
  (letfn [(f [result path]
             (assoc-in result path (get-in n path)))]
         (reduce f {} paths)))
