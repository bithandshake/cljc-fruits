
(ns map.value)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-values
  ; @param (map) n
  ;
  ; @usage
  ; (get-values {:a {:b "B"}})
  ;
  ; @example
  ; (get-values {:a {:b "B"} :c "C"})
  ; =>
  ; [{:b "B"} "C"]
  ;
  ; @return (vector)
  [n]
  (-> n vals vec))

(defn get-first-value
  ; @param (map) n
  ;
  ; @usage
  ; (get-first-value {:a "A" :b "B"})
  ;
  ; @example
  ; (get-first-value {:a "A" :b "B"})
  ; =>
  ; "A"
  ;
  ; @return (*)
  [n]
  ; WARNING! Clojure maps are an unordered data structure.
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
  (letfn [(f [%] (= x (val %)))]
         (some f n)))

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
