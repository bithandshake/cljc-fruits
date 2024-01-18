
(ns fruits.mixed.convert
    (:require [fruits.reader.api :as reader]
              [fruits.mixed.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-kv
  ; @description
  ; Converts the given 'n' value to a key-value pair vector in case it is a possible key-value pair.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-kv [:a "A"])
  ; =>
  ; [:a "A"]
  ;
  ; @usage
  ; (to-kv '(:a "A"))
  ; =>
  ; [:a "A"]
  ;
  ; @usage
  ; (to-kv {:a "A"})
  ; =>
  ; [:a "A"]
  ;
  ; @usage
  ; (to-kv "ab")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-kv nil)
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-kv [:a :b :c])
  ; =>
  ; nil
  ;
  ; @return (vector)
  [n]
  (cond (and (-> n map?)  (-> n count (= 1))) [(-> n keys first) (-> n vals first)]
        (and (-> n coll?) (-> n count (= 2))) [(-> n first)      (-> n second)]))

(defn to-kvs
  ; @description
  ; Converts the items within the given 'n' collection to key-value pair vectors in case they are possible key-value pairs.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-kvs [[:a "A"] '(:b "B") {:c "C"}])
  ; =>
  ; [[:a "A"] [:b "B"] [:c "C"]]
  ;
  ; @usage
  ; (to-kvs '([:a "A"] '(:b "B") {:c "C"}))
  ; =>
  ; [[:a "A"] [:b "B"] [:c "C"]]
  ;
  ; @usage
  ; (to-kvs ["ab" "cd" "ef"])
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-kvs [nil])
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-kvs [[:a :b :c]])
  ; =>
  ; nil
  ;
  ; @return (vectors in vector)
  [n]
  (if (->  n check/kvs?)
      (->> n (map to-kv) vec)
      (->  [])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-data-url
  ; @description
  ; Converts the given 'n' value to a string type data URL.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-data-url "My text file content")
  ; =>
  ; "data:text/plain;charset=utf-8,My text file content"
  ;
  ; @return (string)
  [n]
  (str "data:text/plain;charset=utf-8," n))

(defn to-string
  ; @description
  ; Converts the given 'n' value to a string.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-string [{:a "a"}])
  ; =>
  ; "[{:a a}]"
  ;
  ; @return (string)
  [n]
  (str n))

(defn to-vector
  ; @description
  ; Converts the given 'n' value to a vector.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-vector [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (to-vector nil)
  ; =>
  ; []
  ;
  ; @usage
  ; (to-vector {:a "A" :b "B" :c "C"})
  ; =>
  ; [[:a "A"] [:b "B"] [:c "C"]]
  ;
  ; @usage
  ; (to-vector :a)
  ; =>
  ; [:a]
  ;
  ; @usage
  ; (to-vector "abc")
  ; =>
  ; ["abc"]
  ;
  ; @return (vector)
  [n]
  (cond (vector? n) (-> n)
        (nil?    n) (-> [])
        (coll?   n) (-> n vec)
        :else       (-> [n])))

(defn to-map
  ; @description
  ; Converts the given 'n' value to a map.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-map {:a "A" :b "B" :c "C"})
  ; =>
  ; {:a "A" :b "B" :c "C"}
  ;
  ; @usage
  ; (to-map nil)
  ; =>
  ; {}
  ;
  ; @usage
  ; (to-map [[:a "A"] [:b "B"] [:c "C"]])
  ; =>
  ; {:a "A" :b "B" :c "C"}
  ;
  ; @usage
  ; (to-map [:a :b :c])
  ; =>
  ; {0 [:a :b :c]}
  ;
  ; @usage
  ; (to-map "abc")
  ; =>
  ; {0 "abc"}
  ;
  ; @usage
  ; (to-map :a)
  ; =>
  ; {0 :a}
  ;
  ; @return (map)
  [n]
  (cond (map?       n) (->  n)
        (nil?       n) (->  {})
        (check/kvs? n) (->> n (map to-kv) vec (into {}))
        :else          (-> {0 n})))

(defn to-integer
  ; @description
  ; Converts the given 'n' value to an integer.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-integer 3)
  ; =>
  ; 3
  ;
  ; @usage
  ; (to-integer nil)
  ; =>
  ; 0
  ;
  ; @usage
  ; (to-integer "a")
  ; =>
  ; 0
  ;
  ; @usage
  ; (to-integer "-123")
  ; =>
  ; -123
  ;
  ; @usage
  ; (to-integer "123.456")
  ; =>
  ; 123
  ;
  ; @usage
  ; (to-integer "abc-123.456def789")
  ; =>
  ; -123
  ;
  ; @usage
  ; (to-integer "abc-008")
  ; =>
  ; 8
  ;
  ; @return (number)
  [n]
  ; @bug (#0550)
  ; The applied regex pattern asserts that the first digit of the number cannot be 0.
  ; Otherwise, the 'read-edn' function would read it as a non-decimal number (e.g., 008).
  (cond (integer? n) (-> n)
        (nil?     n) (-> 0)
        :else (if-let [x (-> (re-seq #"[\-]?[1-9][\d]*" (str n)) first)]
                      (reader/read-edn x)
                      (-> 0))))

(defn to-number
  ; @description
  ; Converts the given 'n' value to a number.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-number 3)
  ; =>
  ; 3
  ;
  ; @usage
  ; (to-number nil)
  ; =>
  ; 0
  ;
  ; @usage
  ; (to-number "abc")
  ; =>
  ; 0
  ;
  ; @usage
  ; (to-number "-123")
  ; =>
  ; -123
  ;
  ; @usage
  ; (to-number "123.456")
  ; =>
  ; 123.456
  ;
  ; @usage
  ; (to-number "abc-123.456def789")
  ; =>
  ; -123.456
  ;
  ; @usage
  ; (to-number "abc-008")
  ; =>
  ; 8
  ;
  ; @return (number)
  [n]
  ; @bug (#0550)
  (cond (number? n) (-> n)
        (nil?    n) (-> 0)
        :else (if-let [x (-> (re-seq #"[\-]?[1-9][\d]*[\.]*[\d]*" (str n)) first)]
                      (reader/read-edn x)
                      (-> 0))))

(defn to-fn
  ; @description
  ; Converts the given 'n' value to a function.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-fn (fn [a b c] ...))
  ; =>
  ; (fn [a b c] ...)
  ;
  ; @usage
  ; (to-fn nil)
  ; =>
  ; (fn [& _])
  ;
  ; @usage
  ; (to-fn "abc")
  ; =>
  ; (fn [& _])
  ;
  ; @return (function)
  [n]
  (cond (fn? n) (-> n)
        :return (fn [& _])))

(defn to-ifn
  ; @description
  ; Converts the given 'n' value to a function.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-ifn (fn [a b c] ...))
  ; =>
  ; (fn [a b c] ...)
  ;
  ; @usage
  ; (to-ifn :abc)
  ; =>
  ; :abc
  ;
  ; @usage
  ; (to-ifn nil)
  ; =>
  ; (fn [& _])
  ;
  ; @usage
  ; (to-ifn "abc")
  ; =>
  ; (fn [& _])
  ;
  ; @return (function)
  [n]
  (cond (ifn? n) (-> n)
        :return (fn [& _])))

(defn to-seqable
  ; @description
  ; Converts the given 'n' value to a string to make it seqable in case it is not seqable.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (to-seqable [:a :b :c])
  ; =>
  ; [:a :b :c]
  ;
  ; @usage
  ; (to-seqable "abc")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (to-seqable nil)
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-seqable 123)
  ; =>
  ; "123"
  ;
  ; @return (*)
  [n]
  (if (-> n seqable?)
      (-> n)
      (-> n str)))
