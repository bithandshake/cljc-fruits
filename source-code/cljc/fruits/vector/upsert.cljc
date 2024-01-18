
(ns fruits.vector.upsert
    (:require [fruits.mixed.api :as mixed]
              [fruits.seqable.api :as seqable]
              [fruits.vector.replace :as replace]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn upsert-first-item
  ; @description
  ; Replaces the first item (if any) in the given 'n' vector with the given 'x' value,
  ; otherwise it inserts the given 'x' value as first item.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (upsert-first-item [:a :b :c] :x)
  ; =>
  ; [:x :b :c]
  ;
  ; @usage
  ; (upsert-first-item [] :x)
  ; =>
  ; [:x]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (if (-> n empty? not)
           (replace/replace-first-item n x)
           (conj n x))))

(defn upsert-second-item
  ; @description
  ; Replaces the second item (if any) in the given 'n' vector with the given 'x' value,
  ; otherwise it inserts the given 'x' value as second (or first) item.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (upsert-second-item [:a :b :c] :x)
  ; =>
  ; [:a :x :c]
  ;
  ; @usage
  ; (upsert-second-item [:a] :x)
  ; =>
  ; [:a :x]
  ;
  ; @usage
  ; (upsert-second-item [] :x)
  ; =>
  ; [:x]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (if (-> n count (>= 2))
           (replace/replace-second-item n x)
           (conj n x))))

(defn upsert-last-item
  ; @description
  ; Replaces the last item (if any) in the given 'n' vector with the given 'x' value,
  ; otherwise it inserts the given 'x' value as last item.
  ;
  ; @param (vector) n
  ; @param (*) x
  ;
  ; @usage
  ; (upsert-last-item [:a :b :c] :x)
  ; =>
  ; [:a :b :x]
  ;
  ; @usage
  ; (upsert-last-item [] :x)
  ; =>
  ; [:x]
  ;
  ; @return (vector)
  [n x]
  (let [n (mixed/to-vector n)]
       (if (-> n empty? not)
           (replace/replace-last-item n x)
           (conj n x))))

(defn upsert-nth-item
  ; @description
  ; Replaces the nth item (if any) in the given 'n' vector with the given 'x' value,
  ; otherwise it inserts the given 'x' value as nth item.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ; @param (*) x
  ;
  ; @usage
  ; (upsert-nth-item [:a :b :c] 0 :x)
  ; =>
  ; [:x :b :c]
  ;
  ; @usage
  ; (upsert-nth-item [:a :b :c] 5 :x)
  ; =>
  ; [:a :b :c :x]
  ;
  ; @return (vector)
  [n th x]
  (let [n (mixed/to-vector n)]
       (if-let [th (seqable/normalize-dex n th {:adjust? false :mirror? true})]
               (replace/replace-nth-item n th x)
               (conj n x))))
