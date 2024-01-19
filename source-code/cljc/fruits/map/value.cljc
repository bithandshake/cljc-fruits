
(ns fruits.map.value
    (:require [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn values
  ; @param (map) n
  ;
  ; @usage
  ; (values {:a {:b "B"} :c "C"})
  ; =>
  ; [{:b "B"} "C"]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-map n)]
       (-> n vals vec)))

(defn first-value
  ; @note
  ; Clojure maps are unordered data structures.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (first-value {:a "A" :b "B"})
  ; =>
  ; "A"
  ;
  ; @return (*)
  [n]
  (let [n (mixed/to-map n)]
       (-> n vals first)))

(defn contains-value?
  ; @param (map) n
  ; @param (*) x
  ;
  ; @usage
  ; (contains-value? {} "A")
  ; =>
  ; false
  ;
  ; @usage
  ; (contains-value? {:a "A"} "A")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (let [n (mixed/to-map n)]
       (letfn [(f0 [%] (= x (val %)))]
              (some f0 n))))

(defn values-equal?
  ; @param (map) n
  ; @param (vector) a-path
  ; @param (vector) b-path
  ;
  ; @usage
  ; (values-equal? {:a {:b "X"} :c {:d "X"}}
  ;                [:a :b] [:c :d])
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n a-path b-path]
  (let [n      (mixed/to-map n)
        a-path (mixed/to-vector a-path)
        b-path (mixed/to-vector b-path)]
       (= (get-in n a-path)
          (get-in n b-path))))
