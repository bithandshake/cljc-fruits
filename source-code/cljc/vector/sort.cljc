
(ns vector.sort
    (:require [candy.api  :refer [return]]
              [vector.dex :as dex]
              [vector.nth :as nth]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reverse-items
  ; @param (vector) n
  ;
  ; @usage
  ; (reverse-items [:a :b :c])
  ;
  ; @example
  ; (reverse-items [:a :b :c])
  ; =>
  ; [:c :b :a]
  ;
  ; @return (vector)
  [n]
  (-> n reverse vec))

(defn abc-items
  ; @param (vector) n
  ;
  ; @usage
  ; (abc-items [:a :d :c :b])
  ;
  ; @example
  ; (abc-items [:b "b" :a "a" nil])
  ; =>
  ; [nil "a" "b" :a :b]
  ;
  ; @return (vector)
  [n]
  (letfn [(sort-item-f [o x] (cond (string?  x) (update o :string-items     conj x)
                                   (keyword? x) (update o :keyword-items    conj x)
                                   :return      (update o :unsortable-items conj x)))
          (sort-items-f [n] (reduce sort-item-f {} n))]
         (let [{:keys [string-items keyword-items unsortable-items]} (sort-items-f n)]
              ; (sort) cannot compare string to keyword!
              (vec (concat unsortable-items (sort string-items)
                                            (sort keyword-items))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sort-items
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ;
  ; @usage
  ; (sort-items ["a" "c" "b"] string/abc?)
  ;
  ; @example
  ; (sort-items ["a" "c" "b"] string/abc?)
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @return (vector)
  ([n]
   (-> n sort vec))

  ([n comparator-f]
   (vec (sort comparator-f n))))

(defn items-sorted?
  ; @param (vector) n
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (items-sorted? ["a" "c" "b"] string/abc?)
  ;
  ; @example
  ; (items-sorted? ["a" "c" "b"] string/abc?)
  ; =>
  ; false
  ;
  ; @example
  ; (items-sorted? ["a" "b" "c"] string/abc?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n comparator-f]
  (= n (sort-items n comparator-f)))

(defn sort-items-by
  ; @param (vector) n
  ; @param (function)(opt) comparator-f
  ; @param (function) value-f
  ;
  ; @usage
  ; (sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
  ;
  ; @example
  ; (sort-items-by [{:a 3} {:a 2} {:a 1}] :a)
  ; =>
  ; [{:a 1} {:a 2} {:a 3}]
  ;
  ; @example
  ; (sort-items-by [[1 2] [2 2] [2 3]] > first)
  ; =>
  ; [[2 2] [2 3] [1 2]]
  ;
  ; @return (vector)
  ([n value-f]
   (vec (sort-by value-f n)))

  ([n comparator-f value-f]
   (vec (sort-by value-f comparator-f n))))

(defn sort-items-by-dexes
  ; @param (vector) n
  ; @param (integers in vector) dexes
  ;
  ; @usage
  ; (sort-items-by-dexes [:a :b :c] [2 0 1])
  ; =>
  ; [:c :a :b]
  ;
  ; @usage
  ; (sort-items-by-dexes [:a :b :c] [2 0])
  ; =>
  ; [:c :a]
  ;
  ; @return (vector)
  [n dexes]
  (when (and (vector? n)
             (vector? dexes))
        (letfn [(f [result dex]
                   (if-let [item (nth/nth-item n dex)]
                           (conj   result item)
                           (return result)))]
               (reduce f [] dexes))))

(defn sorted-dexes
  ; @param (vector) a
  ; @param (vector) b
  ;
  ; @usage
  ; (sorted-dexes [:a :b :c] [:c :a :b])
  ; =>
  ; [2 0 1]
  ;
  ; @usage
  ; (sorted-dexes [:a :b :c] [:c :a])
  ; =>
  ; [2 0]
  ;
  ; @return (integers in vector)
  [a b]
  (when (and (vector? a)
             (vector? b))
        (letfn [(f [dexes x]
                   (if-let [ordered-dex (dex/item-first-dex a x)]
                           (conj   dexes ordered-dex)
                           (return dexes)))]
               (reduce f [] b))))

(defn compared-items-sorted?
  ; @param (vector) a
  ; @param (vector) b
  ; @param (function) comparator-f
  ;
  ; @usage
  ; (compared-items-sorted? [0 1 3] [0 1 2] <)
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 2] <)
  ; =>
  ; false
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 4] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? [0 1 3] [0 1 3] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? [] [] <)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? ["a" "b" "c"] ["d" "a"] string/abc?)
  ; =>
  ; true
  ;
  ; @example
  ; (compared-items-sorted? ["a" "b" "c"] ["a" "b"] string/abc?)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b comparator-f]
  ; A compared-items-ordered? függvény összehasonlítja a és b vektor azonos indexű elemeit. ...
  ; ... Az első alkalommal, amikor két összehasonlított elem nem egyezik, visszatér
  ;    a (comparator-f x y) függvény kimenetével.
  ; ... Ha a és b vektor elemei és az elemek száma megegyeznek, a visszatérési érték true!
  ; ... Ha a vektor elemeinek száma és b vektor elemeinek száma nem egyenlő,
  ;    akkor az elemek összehasonlítása az alacsonyabb elemszámig történik!
  ;    (Ha az összehasonlított elemek megegyeznek, akkor a kisebb elemszámú vektor számít
  ;     a sorrendben hamarabbinak!)
  (let [max-count (min (count a) (count b))]
       (letfn [(f [dex]
                  (let [x (get a dex)
                        y (get b dex)]
                       (if (= x y)
                           ; If the compared items are equal ...
                           (if (= (inc dex) max-count)
                               ; If NO more items to compare (and NO difference found)...
                               (<= (count a) (count b))
                               ; If more items to compare ...
                               (f (inc dex)))
                           ; If the compared items are NOT equal ...
                           (comparator-f x y))))]
              ; Ha a max-count értéke 0, akkor mind a két vektor üres.
              (case max-count 0 (return true)
                                (f 0)))))
