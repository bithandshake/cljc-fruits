
(ns vector.update
    (:require [seqable.api  :as seqable]
              [vector.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-first-item
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-first-item [:a :b :c] name)
  ;
  ; @example
  ; (update-first-item [:a :b :c] name)
  ; =>
  ; ["a" :b :c]
  ;
  ; @example
  ; (update-first-item [:a :b :c] #(-> :x))
  ; =>
  ; [:x :b :c]
  ;
  ; @example
  ; (update-first-item [1 2 3] + 10)
  ; =>
  ; [11 2 3]
  ;
  ; @return (vector)
  [n f & params]
  (if (check/nonempty? n)
      (apply update n 0 f params)
      (-> n)))

(defn update-last-item
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-last-item [:a :b :c] name)
  ;
  ; @example
  ; (update-last-item [:a :b :c] name)
  ; =>
  ; [:a :b "c"]
  ;
  ; @example
  ; (update-last-item [:a :b :c] #(-> :x))
  ; =>
  ; [:a :b :x]
  ;
  ; @example
  ; (update-last-item [1 2 3] + 10)
  ; =>
  ; [1 2 13]
  ;
  ; @return (vector)
  [n f & params]
  (if (check/nonempty? n)
      (apply update n (-> n count dec) f params)
      (-> n)))

(defn update-nth-item
  ; @param (vector) n
  ; @param (integer) dex
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-nth-item [:a :b :c] 1 name)
  ;
  ; @example
  ; (update-nth-item [:a :b :c] 1 name)
  ; =>
  ; [:a "b" :c]
  ;
  ; @example
  ; (update-nth-item [:a :b :c] 1 #(-> :x))
  ; =>
  ; [:a :x :c]
  ;
  ; @example
  ; (update-nth-item [1 2 3] 1 + 10)
  ; =>
  ; [1 12 3]
  ;
  ; @return (vector)
  [n dex f & params]
  (if (and (check/nonempty? n)
           (seqable/dex-in-bounds? n dex))
      (apply update n dex f params)
      (-> n)))
