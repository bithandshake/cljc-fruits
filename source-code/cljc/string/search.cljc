
(ns string.search)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn count-occurences
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (count-occurences "abc" "a")
  ;
  ; @example
  ; (count-occurences "abca" "a")
  ; =>
  ; 2
  ;
  ; @example
  ; (count-occurences "abca" "ab")
  ; =>
  ; 1
  ;
  ; @example
  ; (count-occurences "aaaa" "aa")
  ; =>
  ; 3
  ;
  ; @example
  ; (count-occurences "aaaa" "aa" {:separate-matches? true})
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n x]
   (count-occurences n x {}))

  ([n x {:keys [separate-matches?]}]
   (let [n (str n)
         x (str x)]
        (letfn [(f [result cursor]
                   (cond ; ...
                         (< (-> n count)
                            (-> x count (+ cursor)))
                         (-> result)
                         ; ...
                         (= x (subs n cursor (+ cursor (count x))))
                         (f (inc result)
                            (if separate-matches? (+ cursor (count x))
                                                  (inc cursor)))
                         ; ...
                         :else 
                         (f result (inc cursor))))]
               ; ...
               (f 0 0)))))

(defn min-occurence?
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) min
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (min-occurence? "abc" "a" 1)
  ;
  ; @example
  ; (min-occurence? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (min-occurence? "abc abc" "a" 3)
  ; =>
  ; false
  ;
  ; @example
  ; (min-occurence? "aaaa" "aa" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (min-occurence? "aaaa" "aa" 3 {:separate-matches? true})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x min]
   (min-occurence? n x min {}))

  ([n x min options]
   (<= min (count-occurences n x options))))

(defn max-occurence?
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) max
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (max-occurence? "abc" "a" 1)
  ;
  ; @example
  ; (max-occurence? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @example
  ; (max-occurence? "abc abc abc" "a" 2)
  ; =>
  ; false
  ;
  ; @example
  ; (max-occurence? "aaaa" "aa" 2)
  ; =>
  ; false
  ;
  ; @example
  ; (max-occurence? "aaaa" "aa" 2 {:separate-matches? true})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n x max]
   (max-occurence? n x max {}))

  ([n x max options]
   (>= max (count-occurences n x options))))
