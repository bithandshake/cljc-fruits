
(ns fruits.vector.update
    (:require [fruits.mixed.api    :as mixed]
              [fruits.seqable.api  :as seqable]
              [fruits.vector.check :as check]
              [fruits.vector.dex   :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-first-item
  ; @description
  ; Updates the first item in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-first-item [:a :b :c] name)
  ; =>
  ; ["a" :b :c]
  ;
  ; @usage
  ; (update-first-item [1 2 3] + 10)
  ; =>
  ; [11 2 3]
  ;
  ; @return (vector)
  [n f & params]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (if (-> n empty?)
           (-> n)
           (apply update n 0 f params))))

(defn update-second-item
  ; @description
  ; Updates the second item in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-second-item [:a :b :c] name)
  ; =>
  ; [:a "b" :c]
  ;
  ; @usage
  ; (update-second-item [1 2 3] + 10)
  ; =>
  ; [1 12 3]
  ;
  ; @return (vector)
  [n f & params]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (if (-> n count (< 2))
           (-> n)
           (apply update n 1 f params))))

(defn update-last-item
  ; @description
  ; Updates the last item in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-last-item [:a :b :c] name)
  ; =>
  ; [:a :b "c"]
  ;
  ; @usage
  ; (update-last-item [1 2 3] + 10)
  ; =>
  ; [1 2 13]
  ;
  ; @return (vector)
  [n f & params]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (if (-> n empty?)
           (-> n)
           (apply update n (-> n count dec) f params))))

(defn update-nth-item
  ; @description
  ; Updates the nth item in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-nth-item [:a :b :c] 1 name)
  ; =>
  ; [:a "b" :c]
  ;
  ; @usage
  ; (update-nth-item [1 2 3] 1 + 10)
  ; =>
  ; [1 12 3]
  ;
  ; @return (vector)
  [n th f & params]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (apply update n th f params)
               (-> n))))

(defn update-all-item
  ; @description
  ; Updates all item in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-all-item [:a :b :c] name)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @usage
  ; (update-all-item [1 2 3] + 10)
  ; =>
  ; [11 12 13]
  ;
  ; @return (vector)
  [n f & params]
  (let [n (mixed/to-vector n)
        f (mixed/to-ifn f)]
       (letfn [(f0 [result x]
                   (conj result (apply f x params)))]
              (reduce f0 [] n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn update-first-item-by
  ; @description
  ; Updates the first match of the given 'test-f' function in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-first-item-by ["a" :b :c :d] keyword? name)
  ; =>
  ; ["a" "b" :c :d]
  ;
  ; @usage
  ; (update-first-item-by [1 2 3 4] even? + 10)
  ; =>
  ; [1 12 3 4]
  ;
  ; @return (vector)
  [n test-f f & params]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (if-let [dex (dex/first-dex-by n test-f)]
               (apply update n dex f params)
               (-> n))))

(defn update-second-item-by
  ; @description
  ; Updates the second match of the given 'test-f' function in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-second-item-by ["a" :b :c :d] keyword? name)
  ; =>
  ; ["a" :b "c" :d]
  ;
  ; @usage
  ; (update-second-item-by [1 2 3 4] even? + 10)
  ; =>
  ; [1 2 3 14]
  ;
  ; @return (vector)
  [n test-f f & params]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (if-let [dex (dex/second-dex-by n test-f)]
               (apply update n dex f params)
               (-> n))))

(defn update-last-item-by
  ; @description
  ; Updates the last match of the given 'test-f' function in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-last-item-by [:a :b :c "d"] keyword? name)
  ; =>
  ; [:a :b "c" "d"]
  ;
  ; @usage
  ; (update-last-item-by [1 2 3 4] odd? + 10)
  ; =>
  ; [1 2 13 4]
  ;
  ; @return (vector)
  [n test-f f & params]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (if-let [dex (dex/last-dex-by n test-f)]
               (apply update n dex f params)
               (-> n))))

(defn update-nth-item-by
  ; @description
  ; Updates the nth match of the given 'test-f' function in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (integer) th
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-nth-item-by ["a" :b :c :d] keyword? 1 name)
  ; =>
  ; ["a" :b "c" :d]
  ;
  ; @usage
  ; (update-nth-item-by [1 2 3 4] even? 1 + 10)
  ; =>
  ; [1 2 3 14]
  ;
  ; @return (vector)
  [n test-f th f & params]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        th     (mixed/to-integer th)
        f      (mixed/to-ifn f)]
       (if-let [dex (dex/nth-dex-by n test-f th)]
               (apply update n dex f params)
               (-> n))))

(defn update-items-by
  ; @description
  ; Updates all match of the given 'test-f' function in the given 'n' vector with the given 'f' function.
  ;
  ; @param (vector) n
  ; @param (function) test-f
  ; @param (function) f
  ; @param (list of *)(opt) params
  ;
  ; @usage
  ; (update-items-by [:a :b :c "d"] keyword? name)
  ; =>
  ; ["a" "b" "c" "d"]
  ;
  ; @usage
  ; (update-items-by [1 2 3 4] even? + 10)
  ; =>
  ; [1 12 1 14]
  ;
  ; @return (vector)
  [n test-f f & params]
  (let [n      (mixed/to-vector n)
        test-f (mixed/to-ifn test-f)
        f      (mixed/to-ifn f)]
       (letfn [(f0 [result x]
                   (if (test-f x)
                       (conj result (apply f x params))
                       (conj result x)))]
              (reduce f0 [] n))))
