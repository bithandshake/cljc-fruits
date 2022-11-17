
(ns mixed.convert
    (:require [candy.api  :refer [return]]
              [map.api    :as map]
              [mixed.type :as type]
              [reader.api :as reader]
              [vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (*) n
  ;
  ; @example
  ; (to-string [{:a "a"}])
  ; =>
  ; "[{:a a}]"
  ;
  ; @return (string)
  [n]
  (str n))

(defn to-data-url
  ; @param (*) n
  ;
  ; @example
  ; (to-data-url "My text file content")
  ; =>
  ; "data:text/plain;charset=utf-8,My text file content"
  ;
  ; @return (string)
  [n]
  (str "data:text/plain;charset=utf-8," n))

(defn to-vector
  ; @param (*) n
  ;
  ; @example
  ; (to-vector [:a])
  ; =>
  ; [:a]
  ;
  ; @example
  ; (to-vector nil)
  ; =>
  ; []
  ;
  ; @example
  ; (to-vector {:a "a" :b "b"})
  ; =>
  ; ["a" "b"]
  ;
  ; @example
  ; (to-vector :x)
  ; =>
  ; [:x]
  ;
  ; @return (vector)
  [n]
  (cond (map?    n) (map/to-vector n)
        (vector? n) (return        n)
        (nil?    n) (return        [])
        :return                    [n]))

(defn to-map
  ; @param (*) n
  ;
  ; @example
  ; (to-map {:a})
  ; =>
  ; {:a}
  ;
  ; @example
  ; (to-map nil)
  ; =>
  ; {}
  ;
  ; @example
  ; (to-map [:x :y :z])
  ; =>
  ; {0 :x 1 :y 2 :z}
  ;
  ; @example
  ; (to-map :x)
  ; =>
  ; {0 :x}
  ;
  ; @return (map)
  [n]
  (cond (vector? n) (vector/to-map n)
        (map?    n) (return        n)
        (nil?    n) (return        {})
        :return                    {0 n}))

(defn to-number
  ; @param (*) n
  ;
  ; @example
  ; (to-number 3)
  ; =>
  ; 3
  ;
  ; @example
  ; (to-number nil)
  ; =>
  ; 0
  ;
  ; @example
  ; (to-number "a")
  ; =>
  ; 0
  ;
  ; @example
  ; (to-number "-3")
  ; =>
  ; -3
  ;
  ; @example
  ; (to-number "1.1")
  ; =>
  ; 1.1
  ;
  ; @return (number)
  [n]
  (cond (nil?                  n) (return               0)
        (number?               n) (return               n)
        (type/whole-number?    n) (reader/string->mixed n)
        (type/rational-number? n) (reader/string->mixed n)
        :return 0))
