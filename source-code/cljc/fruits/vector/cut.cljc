
(ns fruits.vector.cut
    (:require [fruits.mixed.api   :as mixed]
              [fruits.seqable.api :as seqable]
              [fruits.vector.dex  :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-range
  ; @description
  ; Keeps the items of the given 'n' vector that fall into the given range.
  ;
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ;
  ; @usage
  ; (keep-range [:a :b :c :d :e :f] 1 3)
  ; =>
  ; [:b :c]
  ;
  ; @usage
  ; (keep-range [:a :b :c :d :e :f] 2)
  ; =>
  ; [:c :d :e :f]
  ;
  ; @return (vector)
  ([n from]
   (let [to (count n)]
        (keep-range n from to)))

  ([n from to]
   (let [n    (mixed/to-vector n)
         from (seqable/normalize-cursor n from {:adjust? true :mirror? true})
         to   (seqable/normalize-cursor n to   {:adjust? true :mirror? true})]
        (into [] (subvec n (min from to)
                           (max from to))))))

(defn cut-range
  ; @description
  ; Removes the items of the given 'n' vector that don't fall into the given range.
  ;
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ;
  ; @usage
  ; (cut-range [:a :b :c :d :e :f] 1 3)
  ; =>
  ; [:a :d :e :f]
  ;
  ; @usage
  ; (cut-range [:a :b :c :d :e :f] 2)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  ([n from]
   (let [to (count n)]
        (cut-range n from to)))

  ([n from to]
   (let [n    (mixed/to-vector n)
         from (seqable/normalize-cursor n from {:adjust? true :mirror? true})
         to   (seqable/normalize-cursor n to   {:adjust? true :mirror? true})]
        (vec (concat (subvec n 0 (min from to))
                     (subvec n   (max from to)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-first-item
  ; @description
  ; Returns a vector that contains the first item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (keep-first-item [:a :b :c])
  ; =>
  ; [:a]
  ;
  ; @usage
  ; (keep-first-item [nil nil nil])
  ; =>
  ; [nil]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (if (-> n count (= 1))
           (-> n)
           (if-let [first-item (-> n first)]
                   [first-item] []))))

(defn keep-first-items
  ; @description
  ; Returns a vector that contains the first specific amount of items of the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (keep-first-items [:a :b :c :d :e] 3)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n length]
  (let [n      (mixed/to-vector n)
        length (seqable/normalize-cursor n length {:adjust? true :mirror? false})]
       (into [] (subvec n 0 length))))

(defn keep-last-item
  ; @description
  ; Returns a vector that contains the last item of the given 'n' vector.
  ;
  ; @param (vector) n
  ;
  ; @usage
  ; (keep-last-item [:a :b :c])
  ; =>
  ; [:c]
  ;
  ; @usage
  ; (keep-last-item [nil nil nil])
  ; =>
  ; [nil]
  ;
  ; @return (vector)
  [n]
  (let [n (mixed/to-vector n)]
       (if (-> n count (= 1))
           (-> n)
           (if-let [last-item (-> n last)]
                   [last-item] []))))

(defn keep-last-items
  ; @description
  ; Returns a vector that contains the last specific amount of items of the given 'n' vector.
  ;
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (keep-last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:d :e]
  ;
  ; @return (vector)
  [n length]
  (let [n      (mixed/to-vector n)
        length (seqable/normalize-cursor n length {:adjust? true :mirror? false})]
       (into [] (subvec n (-> n count (- length))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  ([n x]
   (before-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (mixed/to-vector n)]
        (if-let [item-first-dex (dex/first-dex-of n x)]
                (into [] (subvec n 0 item-first-dex))
                (if return? n [])))))

(defn before-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-match [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  ([n f]
   (before-first-match n f {}))

  ([n f {:keys [return?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-first-dex (dex/first-dex-by n f)]
                (into [] (subvec n 0 item-first-dex))
                (if return? n [])))))

(defn before-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  ([n x]
   (before-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (mixed/to-vector n)]
        (if-let [item-last-dex (dex/last-dex-of n x)]
                (into [] (subvec n 0 item-last-dex))
                (if return? n [])))))

(defn before-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  ([n f]
   (before-last-match n f {}))

  ([n f {:keys [return?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-last-dex (dex/last-dex-by n f)]
                (into [] (subvec n 0 item-last-dex))
                (if return? n [])))))

(defn after-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:d :c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (after-first-occurence n x {}))

  ([n x {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)]
        (if-let [item-first-dex (dex/first-dex-of n x)]
                (if (number? item-first-dex)
                    (into [] (subvec n (inc item-first-dex))))
                (if return? n [])))))

(defn after-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:d :c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (after-first-match n f {}))

  ([n f {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-first-dex (dex/first-dex-by n f)]
                (if (number? item-first-dex)
                    (into [] (subvec n (inc item-first-dex))))
                (if return? n [])))))

(defn after-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  ([n x]
   (after-last-occurence n x {}))

  ([n x {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)]
        (if-let [item-last-dex (dex/last-dex-of n x)]
                (if (number? item-last-dex)
                    (into [] (subvec n (inc item-last-dex))))
                (if return? n [])))))

(defn after-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  ([n f]
   (after-last-match n f {}))

  ([n f {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-last-dex (dex/last-dex-by n f)]
                (if (number? item-last-dex)
                    (into [] (subvec n (inc item-last-dex))))
                (if return? n [])))))

(defn from-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:c :d :c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (from-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (mixed/to-vector n)]
        (if-let [item-first-dex (dex/first-dex-of n x)]
                (into [] (subvec n item-first-dex))
                (if return? n [])))))

(defn from-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:c :d :c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (from-first-match n f {}))

  ([n f {:keys [return?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-first-dex (dex/first-dex-by n f)]
                (into [] (subvec n item-first-dex))
                (if return? n [])))))

(defn from-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (from-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (let [n (mixed/to-vector n)]
        (if-let [item-last-dex (dex/last-dex-of n x)]
                (into [] (subvec n item-last-dex))
                (if return? n [])))))

(defn from-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (from-last-match n f {}))

  ([n f {:keys [return?]}]
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-last-dex (dex/last-dex-by n f)]
                (into [] (subvec n item-last-dex))
                (if return? n [])))))

(defn to-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  ([n x]
   (to-first-occurence n x {}))

  ([n x {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)]
        (if-let [item-first-dex (dex/first-dex-of n x)]
                (if (number? item-first-dex)
                    (into [] (subvec n 0 (inc item-first-dex))))
                (if return? n [])))))

(defn to-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  ([n f]
   (to-first-match n f {}))

  ([n f {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-first-dex (dex/first-dex-by n f)]
                (if (number? item-first-dex)
                    (into [] (subvec n 0 (inc item-first-dex))))
                (if return? n [])))))

(defn to-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c :d :c]
  ;
  ; @return (vector)
  ([n x]
   (to-last-occurence n x {}))

  ([n x {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)]
        (if-let [item-last-dex (dex/last-dex-of n x)]
                (if (number? item-last-dex)
                    (into [] (subvec n 0 (inc item-last-dex))))
                (if return? n [])))))

(defn to-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c :d :c]
  ;
  ; @return (vector)
  ([n f]
   (to-last-match n f {}))

  ([n f {:keys [return?]}]
   ; @bug (fruits.vector.remove#1130)
   (let [n (mixed/to-vector n)
         f (mixed/to-ifn    f)]
        (if-let [item-last-dex (dex/last-dex-by n f)]
                (if (number? item-last-dex)
                    (into [] (subvec n 0 (inc item-last-dex))))
                (if return? n [])))))
