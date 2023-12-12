
(ns fruits.vector.cut
    (:require [fruits.seqable.api :as seqable]
              [fruits.vector.dex  :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-range
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ;
  ; @usage
  ; (keep-range [:a :b :c] 1 3)
  ;
  ; @usage
  ; (keep-range [:a :b :c] 1)
  ;
  ; @example
  ; (keep-range [:a :b :c :d :e :f] 1 3)
  ; =>
  ; [:b :c]
  ;
  ; @example
  ; (keep-range [:a :b :c :d :e :f] 2)
  ; =>
  ; [:c :d :e :f]
  ;
  ; @return (vector)
  ([n from]
   (let [to (count n)]
        (keep-range n from to)))

  ([n from to]
   (if (vector? n)
       (let [from (seqable/normalize-cursor n from)
             to   (seqable/normalize-cursor n to)
             from (min from to)
             to   (max from to)]
            (subvec n from to)))))

(defn cut-range
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer)(opt) to
  ;
  ; @usage
  ; (cut-range [:a :b :c] 1 3)
  ;
  ; @usage
  ; (cut-range [:a :b :c] 1)
  ;
  ; @example
  ; (cut-range [:a :b :c :d :e :f] 1 3)
  ; =>
  ; [:a :d :e :f]
  ;
  ; @example
  ; (cut-range [:a :b :c :d :e :f] 2)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  [n from to]
  (if (vector? n)
      (let [from (seqable/normalize-cursor n (-> from))
            to   (seqable/normalize-cursor n (-> to (or (count n))))
            from (min from to)
            to   (max from to)]
           (vec (concat (subvec n 0 from)
                        (subvec n to))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-first-item
  ; @param (vector) n
  ;
  ; @usage
  ; (keep-first-item [:a :b :c])
  ;
  ; @example
  ; (keep-first-item [:a :b :c])
  ; =>
  ; [:a]
  ;
  ; @return (vector)
  [n]
  (if (vector? n)
      (if-let [first-item (-> n first)]
              [first-item] [])))

(defn keep-first-items
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (keep-first-items [:a :b :c :d :e] 2)
  ;
  ; @example
  ; (keep-first-items [:a :b :c :d :e] 3)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  [n length]
  (if (vector? n)
      (let [length (seqable/normalize-cursor n length)]
           (subvec n 0 length))))

(defn keep-last-item
  ; @param (vector) n
  ;
  ; @usage
  ; (keep-last-item [:a :b :c])
  ;
  ; @example
  ; (keep-last-item [:a :b :c])
  ; =>
  ; [:c]
  ;
  ; @return (vector)
  [n]
  (if (vector? n)
      (if-let [last-item (-> n last)]
              [last-item] [])))

(defn keep-last-items
  ; @param (vector) n
  ; @param (integer) length
  ;
  ; @usage
  ; (keep-last-items [:a :b :c :d :e] 2)
  ;
  ; @example
  ; (keep-last-items [:a :b :c :d :e] 2)
  ; =>
  ; [:d :e]
  ;
  ; @return (vector)
  [n length]
  (if (vector? n)
      (let [length (seqable/normalize-cursor n length)]
           (subvec n (-> n count (- length))))))

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
  ;
  ; @example
  ; (before-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  ([n x]
   (before-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (if-let [item-first-dex (dex/first-dex-of n x)]
           (subvec n 0 item-first-dex)
           (if return? n []))))

(defn before-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (before-first-match [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b]
  ;
  ; @return (vector)
  ([n f]
   (before-first-match n f {}))

  ([n f {:keys [return?]}]
   (if-let [item-first-dex (dex/first-dex-by n f)]
           (subvec n 0 item-first-dex)
           (if return? n []))))

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
  ;
  ; @example
  ; (before-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  ([n x]
   (before-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (if-let [item-last-dex (dex/last-dex-of n x)]
           (subvec n 0 item-last-dex)
           (if return? n []))))

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
  ;
  ; @example
  ; (before-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c :d]
  ;
  ; @return (vector)
  ([n f]
   (before-last-match n f {}))

  ([n f {:keys [return?]}]
   (if-let [item-last-dex (dex/last-dex-by n f)]
           (subvec n 0 item-last-dex)
           (if return? n []))))

(defn after-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (after-first-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (after-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:d :c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (after-first-occurence n x {}))

  ([n x {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-first-dex (dex/first-dex-of n x)]
           (if (number? item-first-dex)
               (subvec n (inc item-first-dex)))
           (if return? n []))))

(defn after-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (after-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (after-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:d :c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (after-first-match n f {}))

  ([n f {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-first-dex (dex/first-dex-by n f)]
           (if (number? item-first-dex)
               (subvec n (inc item-first-dex)))
           (if return? n []))))

(defn after-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (after-last-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (after-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  ([n x]
   (after-last-occurence n x {}))

  ([n x {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-last-dex (dex/last-dex-of n x)]
           (if (number? item-last-dex)
               (subvec n (inc item-last-dex)))
           (if return? n []))))

(defn after-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (after-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (after-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:b :a]
  ;
  ; @return (vector)
  ([n f]
   (after-last-match n f {}))

  ([n f {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-last-dex (dex/last-dex-by n f)]
           (if (number? item-last-dex)
               (subvec n (inc item-last-dex)))
           (if return? n []))))

(defn from-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (from-first-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (from-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:c :d :c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (from-first-occurence n x {}))

  ([n x {:keys [return?]}]
   (if-let [item-first-dex (dex/first-dex-of n x)]
           (subvec n item-first-dex)
           (if return? n []))))

(defn from-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (from-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (from-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:c :d :c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (from-first-match n f {}))

  ([n f {:keys [return?]}]
   (if-let [item-first-dex (dex/first-dex-by n f)]
           (subvec n item-first-dex)
           (if return? n []))))

(defn from-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (from-last-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (from-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  ([n x]
   (from-last-occurence n x {}))

  ([n x {:keys [return?]}]
   (if-let [item-last-dex (dex/last-dex-of n x)]
           (subvec n item-last-dex)
           (if return? n []))))

(defn from-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (from-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (from-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  ([n f]
   (from-last-match n f {}))

  ([n f {:keys [return?]}]
   (if-let [item-last-dex (dex/last-dex-by n f)]
           (subvec n item-last-dex)
           (if return? n []))))

(defn to-first-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (to-first-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (to-first-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  ([n x]
   (to-first-occurence n x {}))

  ([n x {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-first-dex (dex/first-dex-of n x)]
           (if (number? item-first-dex)
               (subvec n 0 (inc item-first-dex)))
           (if return? n []))))

(defn to-first-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (to-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (to-first-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c]
  ;
  ; @return (vector)
  ([n f]
   (to-first-match n f {}))

  ([n f {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-first-dex (dex/first-dex-by n f)]
           (if (number? item-first-dex)
               (subvec n 0 (inc item-first-dex)))
           (if return? n []))))

(defn to-last-occurence
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no occurence is found.
  ;   Default: false}
  ;
  ; @example
  ; (to-last-occurence [:a :b :c :d :c :b :a] :c)
  ;
  ; @example
  ; (to-last-occurence [:a :b :c :d :c :b :a] :c)
  ; =>
  ; [:a :b :c :d :c]
  ;
  ; @return (vector)
  ([n x]
   (to-last-occurence n x {}))

  ([n x {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-last-dex (dex/last-dex-of n x)]
           (if (number? item-last-dex)
               (subvec n 0 (inc item-last-dex)))
           (if return? n []))))

(defn to-last-match
  ; @param (vector) n
  ; @param (function) f
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' vector in case of no match is found.
  ;   Default: false}
  ;
  ; @example
  ; (to-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ;
  ; @example
  ; (to-last-match [:a :b :c :d :c :b :a] #(= % :c))
  ; =>
  ; [:a :b :c :d :c]
  ;
  ; @return (vector)
  ([n f]
   (to-last-match n f {}))

  ([n f {:keys [return?]}]
   ; BUG#1130 (source-code/cljc/fruits/vector/remove.cljc)
   (if-let [item-last-dex (dex/last-dex-by n f)]
           (if (number? item-last-dex)
               (subvec n 0 (inc item-last-dex)))
           (if return? n []))))
