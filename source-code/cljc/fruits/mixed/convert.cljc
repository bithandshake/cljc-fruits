
(ns fruits.mixed.convert
    (:require [fruits.map.api    :as map]
              [fruits.mixed.type :as type]
              [fruits.reader.api :as reader]
              [fruits.regex.api :as regex]
              [fruits.vector.api :as vector]))

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
  (cond (map?    n) (-> n map/to-vector)
        (vector? n) (-> n)
        (nil?    n) (-> [])
        :return     [n]))

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
  (cond (vector? n) (-> n vector/to-map)
        (map?    n) (-> n)
        (nil?    n) (-> {})
        :return     {0 n}))

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
  ; (to-number "-123")
  ; =>
  ; -123
  ;
  ; @example
  ; (to-number "123.456")
  ; =>
  ; 123.456
  ;
  ; @example
  ; (to-number "abc-123.456def789")
  ; =>
  ; -123.456
  ;
  ; @example
  ; (to-number "abc-008")
  ; =>
  ; 8
  ;
  ; @return (number)
  [n]
  ; The applied regex pattern asserts that the first digit of the number cannot be 0.
  ; Otherwise, the 'read-edn' function would read it as a non-decimal number (e.g., 008).
  (cond (nil?    n) (-> 0)
        (number? n) (-> n)
        :else       (-> n (regex/re-first #"[\-]?[1-9][\d]*[\.]*[\d]*")
                          (or 0)
                          (reader/read-edn))))
