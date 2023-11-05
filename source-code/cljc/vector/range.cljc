
(ns vector.range
    (:require [vector.cursor :as cursor]
              [vector.dex    :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ranged-items
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ;
  ; @usage
  ; (ranged-items [:a :b :c] 1 3)
  ;
  ; @usage
  ; (ranged-items [:a :b :c] 1)
  ;
  ; @example
  ; (ranged-items [:a :b :c :d :e :f] 1 3)
  ; =>
  ; [:b :c]
  ;
  ; @example
  ; (ranged-items [:a :b :c :d :e :f] 2)
  ; =>
  ; [:c :d :e :f]
  ;
  ; @return (vector)
  ([n from]
   (let [to (count n)]
        (ranged-items n from to)))

  ([n from to]
   (if (and (-> n  vector?)
            (-> to integer?)
            (-> n (cursor/cursor-in-bounds? from)))
       (let [to   (min to (count n))
             from (min from to)]
            (subvec n from to))
       (-> []))))

(defn last-items
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (last-items [:a :b :c] 2)
  ;
  ; @example
  ; (last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:d :e]
  ;
  ; @return (vector)
  [n length]
  (if (dex/dex-in-bounds? n length)
      (subvec n (-> n count (- length)))
      (->     n)))

(defn first-items
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (first-items [:a :b :c] 2)
  ;
  ; @example
  ; (first-items [:a :b :c :d :e] 3)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n length]
  (if (dex/dex-in-bounds? n length)
      (subvec n 0 length)
      (->     n)))

(defn trim
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (trim [:a :b :c] 2)
  ;
  ; @example
  ; (trim [:a :b :c :d :e] 3)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n length]
  (first-items n length))

(defn count!
  ; @param (vector) n
  ; @param (integer) x
  ;
  ; @usage
  ; (count! [:a :b :c] 3)
  ;
  ; @example
  ; (count! [:a :b :c] 3)
  ; =>
  ; [:a :b :c]
  ;
  ; @example
  ; (count! [:a :b :c] 2)
  ; =>
  ; [:a :b]
  ;
  ; @example
  ; (count! [:a :b :c] 5)
  ; =>
  ; [:a :b :c nil nil]
  ;
  ; @return (vector)
  [n x]
  (if (and (-> n vector?)
           (-> x integer?)
           (-> n count (not= x)))
      (if (> (count n) x)
          (first-items n x)
          (vec (concat n (repeat nil (- x (count n))))))
      (-> n)))

(defn items-before-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (items-before-first-occurence [:a :b :c] :c)
  ;
  ; @example
  ; (items-before-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n x]
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (subvec n 0 item-first-dex)
          (-> [])))

(defn items-after-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @example
  ; (items-after-first-occurence [:a :b :c] :b)
  ;
  ; @example
  ; (items-after-first-occurence [:a :b :c :d :d :e] :d)
  ; =>
  ; [:d :e]
  ;
  ; @return (vector)
  [n x]
  ; BUG#1130 (source-code/cljc/vector/remove.cljc)
  (if-let [item-first-dex (dex/item-first-dex n x)]
          (if (number? item-first-dex)
              (subvec n (inc item-first-dex)))
          (-> [])))
