
(ns fruits.string.search)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn occurence-count
  ; @description
  ; Returns the occurence count of the given 'x' string in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (occurence-count "abca" "a")
  ; =>
  ; 2
  ;
  ; @usage
  ; (occurence-count "abca" "ab")
  ; =>
  ; 1
  ;
  ; @usage
  ; (occurence-count "aaaa" "aa")
  ; =>
  ; 3
  ;
  ; @usage
  ; (occurence-count "aaaa" "aa" {:separate-matches? true})
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n x]
   (occurence-count n x {}))

  ([n x {:keys [separate-matches?]}]
   (let [n (str n)
         x (str x)]
        (letfn [(f0 [result cursor]
                    (cond ; ...
                          (< (-> n count)
                             (-> x count (+ cursor)))
                          (-> result)
                          ; ...
                          (= x (subs n cursor (+ cursor (count x))))
                          (f0 (inc result)
                             (if separate-matches? (+ cursor (count x))
                                                   (inc cursor)))
                          ; ...
                          :else
                          (f0 result (inc cursor))))]
               ; ...
               (f0 0 0)))))

(defn occurence-min?
  ; @description
  ; Returns TRUE, if the given 'x' string has at least many occurence in the given 'n' string as the given 'min' value.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) min
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (occurence-min? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @usage
  ; (occurence-min? "abc abc" "a" 3)
  ; =>
  ; false
  ;
  ; @usage
  ; (occurence-min? "aaaa" "aa" 3)
  ; =>
  ; true
  ;
  ; @usage
  ; (occurence-min? "aaaa" "aa" 3 {:separate-matches? true})
  ; =>
  ; false
  ;
  ; @return (boolean)
  ([n x min]
   (occurence-min? n x min {}))

  ([n x min options]
   (<= min (occurence-count n x options))))

(defn occurence-max?
  ; @description
  ; Returns TRUE, if the given 'x' string has at most many occurence in the given 'n' string as the given 'max' value.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) max
  ; @param (map)(opt) options
  ; {:separate-matches? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (occurence-max? "abc abc" "a" 2)
  ; =>
  ; true
  ;
  ; @usage
  ; (occurence-max? "abc abc abc" "a" 2)
  ; =>
  ; false
  ;
  ; @usage
  ; (occurence-max? "aaaa" "aa" 2)
  ; =>
  ; false
  ;
  ; @usage
  ; (occurence-max? "aaaa" "aa" 2 {:separate-matches? true})
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n x max]
   (occurence-max? n x max {}))

  ([n x max options]
   (>= max (occurence-count n x options))))
