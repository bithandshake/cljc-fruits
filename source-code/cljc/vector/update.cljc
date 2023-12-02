
(ns vector.update
    (:require [seqable.api  :as seqable]
              [vector.check :as check]
              [vector.dex   :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-first-item
  ; @description
  ; Updates the first item in the given 'n' vector with the given 'f' function and passes the given parameters to the applied function.
  ;
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
  ; @description
  ; Updates the last item in the given 'n' vector with the given 'f' function and passes the given parameters to the applied function.
  ;
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
  ; @description
  ; Updates the nth item in the given 'n' vector with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (integer) th
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
  [n th f & params]
  (if (check/nonempty? n)
      (let [th (seqable/normalize-dex n th)]
           (apply update n th f params))
      (-> n)))

(defn update-all-items
  ; @description
  ; Updates all items in the given 'n' vector with the given 'f' function and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-all-items [:a :b :c] name)
  ;
  ; @example
  ; (update-all-items [:a :b :c] name)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @example
  ; (update-all-items [:a :b :c] #(-> :x))
  ; =>
  ; [:x :x :x]
  ;
  ; @example
  ; (update-all-items [1 2 3] + 10)
  ; =>
  ; [11 12 13]
  ;
  ; @return (vector)
  [n f & params]
  (if (check/nonempty? n)
      (letfn [(f0 [result x]
                  (conj result (apply f x params)))]
             (reduce f0 [] n))
      (-> n)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-first-item-by
  ; @description
  ; Updates the first match of the given 'test-f' function in the given 'n' vector with the given 'f' function
  ; and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-first-item-by ["a" :b :c :d] keyword? name)
  ;
  ; @example
  ; (update-first-item-by ["a" :b :c :d] keyword? name)
  ; =>
  ; ["a" "b" :c :d]
  ;
  ; @example
  ; (update-first-item-by ["a" :b :c :d] keyword? #(-> :x))
  ; =>
  ; ["a" :x :c :d]
  ;
  ; @example
  ; (update-first-item-by [1 2 3 4] even? + 10)
  ; =>
  ; [1 12 3 4]
  ;
  ; @return (vector)
  [n test-f f & params]
  (if (check/nonempty? n)
      (if-let [first-dex (dex/first-dex-by n test-f)]
              (apply update n first-dex f params)
              (-> n))
      (-> n)))

(defn update-last-item-by
  ; @description
  ; Updates the last match of the given 'test-f' function in the given 'n' vector with the given 'f' function
  ; and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-last-item-by [:a :b :c "d"] keyword? name)
  ;
  ; @example
  ; (update-last-item-by [:a :b :c "d"] keyword? name)
  ; =>
  ; [:a :b "c" "d"]
  ;
  ; @example
  ; (update-last-item-by [:a :b :c "d"] keyword? #(-> :x))
  ; =>
  ; [:a :b :x "d"]
  ;
  ; @example
  ; (update-last-item-by [1 2 3 4] odd? + 10)
  ; =>
  ; [1 2 13 4]
  ;
  ; @return (vector)
  [n test-f f & params]
  (if (check/nonempty? n)
      (if-let [last-dex (dex/last-dex-by n test-f)]
              (apply update n last-dex f params)
              (-> n))
      (-> n)))

(defn update-nth-item-by
  ; @description
  ; Updates the nth match of the given 'test-f' function in the given 'n' vector with the given 'f' function
  ; and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) th
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-nth-item-by ["a" :b :c :d] keyword? 1 name)
  ;
  ; @example
  ; (update-nth-item-by ["a" :b :c :d] keyword? 1 name)
  ; =>
  ; ["a" :b "c" :d]
  ;
  ; @example
  ; (update-nth-item-by ["a" :b :c :d] keyword? 1 #(-> :x))
  ; =>
  ; ["a" :b :x :d]
  ;
  ; @example
  ; (update-nth-item-by [1 2 3 4] even? 1 + 10)
  ; =>
  ; [1 2 3 14]
  ;
  ; @return (vector)
  [n test-f th f & params]
  (if (check/nonempty? n)
      (if-let [nth-dex (dex/nth-dex-by n test-f th)]
              (apply update n nth-dex f params)
              (-> n))
      (-> n)))

(defn update-items-by
  ; @description
  ; Updates all match of the given 'test-f' function in the given 'n' vector with the given 'f' function
  ; and passes the given parameters to the applied function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-items-by [:a :b :c "d"] keyword? name)
  ;
  ; @example
  ; (update-items-by [:a :b :c "d"] keyword? name)
  ; =>
  ; ["a" "b" "c" "d"]
  ;
  ; @example
  ; (update-items-by [:a :b :c "d"] keyword? #(-> :x))
  ; =>
  ; [:x :x :x "d"]
  ;
  ; @example
  ; (update-items-by [1 2 3 4] even? + 10)
  ; =>
  ; [1 12 1 14]
  ;
  ; @return (vector)
  [n test-f f & params]
  (if (check/nonempty? n)
      (letfn [(f0 [result x]
                  (if (test-f x)
                      (conj result (apply f x params))
                      (conj result x)))]
             (reduce f0 [] n))
      (-> n)))
