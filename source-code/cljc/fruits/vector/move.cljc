
(ns fruits.vector.move
    (:require [fruits.seqable.api   :as seqable]
              [fruits.vector.dex    :as dex]
              [fruits.vector.insert    :as insert]
              [fruits.vector.add    :as add]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move-first-item
  ; @description
  ; Moves the first item in the given 'n' vector to a specific cursor position.
  ;
  ; @param (vector) n
  ; @param (integer) to
  ;
  ; @usage
  ; (move-first-item [:a :b :c :d :e :f :g :h] 3)
  ; =>
  ; [:b :c :d :a :e :f :g :h]
  ;
  ; @return (vector)
  [n to]
  (let [n  (mixed/to-vector n)
        to (seqable/normalize-dex n to {:adjust? true :mirror? true})]
       (if (-> n count (>= 1))
           (cond ; Keeps the item in place ...
                 (-> to (= 0))
                 (-> n)
                 ; Moves the item fwd ...
                 (-> to (> 0))
                 (vec (concat (subvec n 1 (inc to))
                              [(first n)]
                              (subvec n (inc to)))))
           (-> n))))

(defn move-second-item
  ; @description
  ; Moves the second item in the given 'n' vector to a specific cursor position.
  ;
  ; @param (vector) n
  ; @param (integer) to
  ;
  ; @usage
  ; (move-second-item [:a :b :c :d :e :f :g :h] 3)
  ; =>
  ; [:a :c :d :b :e :f :g :h]
  ;
  ; @return (vector)
  [n to]
  (let [n  (mixed/to-vector n)
        to (seqable/normalize-dex n to {:adjust? true :mirror? true})]
       (if (-> n count (>= 2))
           (cond ; Keeps the item in place ...
                 (-> to (= 1))
                 (-> n)
                 ; Moves the item bwd ...
                 (-> to (= 0))
                 (vec (concat [(second n) (first n)]
                              (subvec n 2)))
                 ; Moves the item fwd ...
                 (-> to (> 1))
                 (vec (concat [(first n)]
                              (subvec n 2 (inc to))
                              [(second n)]
                              (subvec n (inc to)))))
           (-> n))))

(defn move-last-item
  ; @description
  ; Moves the last item in the given 'n' vector to a specific cursor position.
  ;
  ; @param (vector) n
  ; @param (integer) to
  ;
  ; @usage
  ; (move-last-item [:a :b :c :d :e :f :g :h] 3)
  ; =>
  ; [:a :b :c :h :d :e :f :g]
  ;
  ; @return (vector)
  [n to]
  (let [n  (mixed/to-vector n)
        to (seqable/normalize-dex n to {:adjust? true :mirror? true})]
       (if (-> n count (>= 1))
           (cond ; Keeps the item in place ...
                 (-> n count dec (= to))
                 (-> n)
                 ; Moves the item bwd ...
                 (-> n count dec (> to))
                 (vec (concat (subvec n 0 to)
                              [(last n)]
                              (subvec n to (-> n count dec)))))
           (-> n))))

(defn move-nth-item
  ; @description
  ; Moves the nth item in the given 'n' vector to a specific cursor position.
  ;
  ; @param (vector) n
  ; @param (integer) from
  ; @param (integer) to
  ;
  ; @usage
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 2 2)
  ; =>
  ; [:a :b :c :d :e :f :g :h]
  ;
  ; @usage
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 2 5)
  ; =>
  ; [:a :b :d :e :f :c :g :h]
  ;
  ; @usage
  ; (move-nth-item [:a :b :c :d :e :f :g :h] 5 2)
  ; =>
  ; [:a :b :f :c :d :e :g :h]
  ;
  ; @return (vector)
  [n from to]
  (let [n  (mixed/to-vector n)
        to (seqable/normalize-dex n to {:adjust? true :mirror? true})]
       (if-let [from (seqable/normalize-dex n from {:adjust? false :mirror? true})]
               (cond ; Keeps the item in place ...
                     (= from to)
                     (-> n)
                     ; Moves the item fwd ...
                     (< from to)
                     (vec (concat (subvec n 0 from)
                                  (subvec n (inc from) (inc to))
                                  (subvec n from (inc from))
                                  (subvec n (inc to))))
                     ; Moves the item bwd ...
                     (> from to)
                     (vec (concat (subvec n 0 to)
                                  (subvec n from (inc from))
                                  (subvec n to from)
                                  (subvec n (inc from)))))
               (-> n))))

(defn move-nth-item-bwd
  ; @description
  ; Moves the nth item in the given 'n' vector backwards.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ; @param (integer)(opt) step
  ; Default: 1
  ;
  ; @usage
  ; (move-nth-item-bwd [:a :b :c :d] 2)
  ; =>
  ; [:a :c :b :d]
  ;
  ; @usage
  ; (move-nth-item-bwd [:a :b :c :d] 0)
  ; =>
  ; [:b :c :d :a]
  ;
  ; @return (vector)
  ([n th]
   (move-nth-item-bwd n th 1))

  ([n th step]
   (let [th   (mixed/to-integer th)
         step (mixed/to-integer step)]
        (move-nth-item n th (- th step)))))

(defn move-nth-item-fwd
  ; @description
  ; Moves the nth item in the given 'n' vector forwards.
  ;
  ; @param (vector) n
  ; @param (integer) th
  ; @param (integer)(opt) step
  ; Default: 1
  ;
  ; @usage
  ; (move-nth-item-fwd [:a :b :c :d] 2)
  ; =>
  ; [:a :b :d :c]
  ;
  ; @usage
  ; (move-nth-item-fwd [:a :b :c :d] 3)
  ; =>
  ; [:d :a :b :c]
  ;
  ; @return (vector)
  ([n th]
   (move-nth-item-fwd n th 1))

  ([n th step]
   (let [th   (mixed/to-integer th)
         step (mixed/to-integer step)]
        (move-nth-item n th (+ th step)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn move-item-to-first
  ; @description
  ; Moves the first occurence of a specific item in the given 'n' vector to the first position.
  ;
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:insert? (boolean)(opt)
  ;   Inserts the item in case the given 'n' vector doesn't contain it.
  ;   Default: false}
  ;
  ; @usage
  ; (move-item-to-first [:a :b :c :d :e :f :g :h] :b)
  ; =>
  ; [:b :a :c :d :e :f :g :h]
  ;
  ; @return (vector)
  ([n x]
   (move-item-to-first n x {}))

  ([n x {:keys [insert?]}]
   (let [n (mixed/to-vector n)]
        (if-let [dex (dex/first-dex-of n x)]
                (move-nth-item n dex 0)
                (if insert? (-> n (add/cons-item x))
                            (-> n))))))

(defn move-item-to-second
  ; @description
  ; Moves the first occurence of a specific item in the given 'n' vector to the second position.
  ;
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:insert? (boolean)(opt)
  ;   Inserts the item in case the given 'n' vector doesn't contain it.
  ;   Default: false}
  ;
  ; @usage
  ; (move-item-to-second [:a :b :c :d :e :f :g :h] :a)
  ; =>
  ; [:b :a :c :d :e :f :g :h]
  ;
  ; @return (vector)
  ([n x]
   (move-item-to-second n x {}))

  ([n x {:keys [insert?]}]
   (let [n (mixed/to-vector n)]
        (if-let [dex (dex/first-dex-of n x)]
                (move-nth-item n dex 1)
                (if insert? (-> n (insert/insert-item 1 x))
                            (-> n))))))

(defn move-item-to-last
  ; @description
  ; Moves the first occurence of a specific item in the given 'n' vector to the last position.
  ;
  ; @param (vector) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:insert? (boolean)(opt)
  ;   Inserts the item in case the given 'n' vector doesn't contain it.
  ;   Default: false}
  ;
  ; @usage
  ; (move-item-to-last [:a :b :c :d :e :f :g :h] :a)
  ; =>
  ; [:b :c :d :e :f :g :h :a]
  ;
  ; @return (vector)
  ([n x]
   (move-item-to-last n x {}))

  ([n x {:keys [insert?]}]
   (let [n (mixed/to-vector n)]
        (if-let [dex (dex/first-dex-of n x)]
                (move-nth-item n dex -1)
                (if insert? (-> n (add/conj-item x))
                            (-> n))))))

(defn move-item-to-nth
  ; @description
  ; Moves the first occurence of a specific item in the given 'n' vector to the nth position.
  ;
  ; @param (vector) n
  ; @param (*) x
  ; @param (integer) th
  ; @param (map)(opt) options
  ; {:insert? (boolean)(opt)
  ;   Inserts the item in case the given 'n' vector doesn't contain it.
  ;   Default: false}
  ;
  ; @usage
  ; (move-item-to-nth [:a :b :c :d :e :f :g :h] :a 2)
  ; =>
  ; [:b :c :a :d :e :f :g :h]
  ;
  ; (... [:a :b :c] :c 5)
  ;
  ; @return (vector)
  ([n x to]
   (move-item-to-nth n x to {}))

  ([n x to {:keys [insert?]}]
   (let [n  (mixed/to-vector n)
         to (seqable/normalize-dex n to {:adjust? true :mirror? true})]
        (if-let [dex (dex/first-dex-of n x)]
                (move-nth-item n dex to)
                (if insert? (-> n (insert/insert-item to x))
                            (-> n))))))
