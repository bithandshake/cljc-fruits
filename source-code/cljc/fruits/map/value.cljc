
(ns fruits.map.value)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn values
  ; @param (map) n
  ;
  ; @usage
  ; (values {:a {:b "B"}})
  ;
  ; @example
  ; (values {:a {:b "B"} :c "C"})
  ; =>
  ; [{:b "B"} "C"]
  ;
  ; @return (vector)
  [n]
  (-> n vals vec))

(defn first-value
  ; @warning
  ; Clojure maps are unordered data structures.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (first-value {:a "A" :b "B"})
  ;
  ; @example
  ; (first-value {:a "A" :b "B"})
  ; =>
  ; "A"
  ;
  ; @return (*)
  [n]
  (-> n vals first))

(defn contains-value?
  ; @param (map) n
  ; @param (*) x
  ;
  ; @example
  ; (contains-value? {:a "A"} "A")
  ;
  ; @example
  ; (contains-value? {} "A")
  ; =>
  ; false
  ;
  ; @example
  ; (contains-value? {:a "A"} "A")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (letfn [(f0 [%] (= x (val %)))]
         (some f0 n)))

(defn values-equal?
  ; @param (map) n
  ; @param (vector) a-path
  ; @param (vector) b-path
  ;
  ; @usage
  ; (values-equal? {:a "X" :b "X"} [:a] [:b])
  ;
  ; @example
  ; (values-equal? {:a {:b "X"}
  ;                 :c {:d "X"}}
  ;                [:a :b] [:c :d])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n a-path b-path]
  (= (get-in n a-path)
     (get-in n b-path)))
