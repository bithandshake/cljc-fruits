
(ns vector.remove
    (:require [vector.dex   :as dex]
              [vector.item  :as item]
              [vector.range :as range]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-first-item [:a :b :c])
  ;
  ; @example
  ; (remove-first-item [:a :b :c :d :e])
  ; =>
  ; [:b :c :d :e]
  ;
  ; @return (vector)
  [n]
  ;(if (check/nonempty? n)
  ;    (subvec          n 1)
  ;    (->              [])])
  (vec (drop 1 n)))

(defn remove-first-items
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-first-items [:a :b :c] 2)
  ;
  ; @example
  ; (remove-first-items [:a :b :c :d :e] 2)
  ; =>
  ; [:c :d :e]
  ;
  ; @return (vector)
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n cut)))

(defn remove-last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-last-item [:a :b :c])
  ;
  ; @example
  ; (remove-last-item [:a :b :c :d :e])
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  [n]
  ;(if (check/nonempty? n)
  ;    (subvec          n 0 (-> n count dec))
  ;    (->              [])])
  (-> n drop-last vec))

(defn remove-last-items
  ; @param (vector) n
  ; @param (integer) cut
  ;
  ; @usage
  ; (remove-last-items [:a :b :c] 2)
  ;
  ; @example
  ; (remove-last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n cut]
  (cond (-> cut integer? not) (-> n)
        (>= cut (count n))    (-> [])
        :return (subvec n 0 (-> n count (- cut)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-item
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item [:a :b] :b)
  ;
  ; @example
  ; (remove-item [:a :b] :b)
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-item [:a :b :a] :a)
  ; =>
  ; [:b]
  ;
  ; @return (vector)
  [n x]
  (letfn [(f [%] (= % x))]
         (vec (remove f n))))

(defn remove-item-once
  ; @param (vector) n
  ; @param (*) dex
  ;
  ; @usage
  ; (remove-item-once [:a :b :b] :b)
  ;
  ; @example
  ; (remove-item-once [:a :b :b] :b)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (remove-item-once [:a :b :a] :a)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  [n x]
  (let [count (count n)]
       (letfn [(f [dex]
                        ; If the dex is the highest index in the vector, and the element being examined
                        ; matches the element to be deleted...
                  (cond (and (= dex count)
                             (= x (nth n dex)))
                        ; ... it deletes the last element from the vector.
                        (subvec n 0 (-> n count dec))
                        ; If the dex is the highest index in the vector, and the examined element
                        ; does NOT match the element to be deleted...
                        (= dex count)
                        ; ... változtatás nélkül visszatér a vektorral.
                        ; ... it returns the vector without making any changes.
                        (-> n)
                        ; If the dex is NOT the highest index in the vector and
                        ; the examined element matches the element to be deleted...
                        (= x (nth n dex))
                        ; ... it deletes the element from the vector.
                        (vec (concat (subvec n 0 dex)
                                     (subvec n (inc dex))))
                        ; If the dex is NOT the highest index in the vector and the examined
                        ; element does NOT match the element to be deleted...
                        :else
                        (-> dex inc f)))]
              (f 0))))

(defn remove-items
  ; @param (vector) n
  ; @param (vector) xyz
  ;
  ; @usage
  ; (remove-items [:a :b :c] [:b :c])
  ;
  ; @example
  ; (remove-items [:a :b :c] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @example
  ; (remove-items [:a :b :b :c ] [:b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n xyz]
  ;(letfn [(f [result x]
  ;           (if (check/contains-item? xyz x)
  ;               (->                   result)
  ;               (conj                 result x)))]
  ;       (reduce f [] n))
  (vec (remove (set xyz) n)))

(defn remove-items-by
  ; @param (vector) n
  ; @param (function) f
  ;
  ; @usage
  ; (remove-items-by [:a :b :c] keyword?)
  ;
  ; @example
  ; (remove-items-by [:a :b :c] keyword?)
  ; =>
  ; []
  ;
  ; @return (vector)
  [n test-f]
  (letfn [(f [result x]
             (if (-> x test-f)
                 (->   result)
                 (conj result x)))]
         (reduce f [] n)))

(defn remove-items-kv
  ; @param (maps in vector) n
  ; @param (*) k
  ; @param (*) v
  ;
  ; @example
  ; (remove-items-kv [{:a "a"} {:b "b"} {:c "c"}] :b "b")
  ; =>
  ; [{:a "a"} {:c "c"}]
  ;
  ; @return (maps in vector)
  [n k v]
  (letfn [(f [%] (= (k %) v))]
         (vec (remove f n))))

(defn remove-duplicates
  ; @param (vector) n
  ;
  ; @usage
  ; (remove-duplicates [:a :b :c :a])
  ;
  ; @example
  ; (remove-duplicates [:a :b :c :a])
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n]
  (letfn [(f [result x]
             (if (item/contains-item? result x)
                 (->   result)
                 (conj result x)))]
         (reduce f [] n)))

(defn remove-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (remove-first-occurence [:a :b :b :c] :b)
  ;
  ; @example
  ; (remove-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:a :b :c :d :e]
  ;
  ; @return (vector)
  [n x]
  ; BUG#1130
  ; The shadow-cljs protects the 'inc' function from receiving nil as a parameter.
  ; The 'item-first-dex' function returns through a when condition, therefore
  ; without using the (if (number? item-dex) ...) condition, the shadow-cljs
  ; throws the following error message:
  ; "cljs.core/+, all arguments must be numbers, got [#{nil clj-nil} number] instead"
  (if-let [item-dex (dex/item-first-dex n x)]
          (if (number? item-dex)
              (vec (concat (subvec n 0 item-dex)
                           (subvec n (inc item-dex)))))
          (-> n)))
