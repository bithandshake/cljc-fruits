
(ns string.length
    (:require [string.dex :as dex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn length
  ; @param (*) n
  ;
  ; @usage
  ; (length "One Flew Over the Cuckoo's Nest")
  ;
  ; @example
  ; (length "One Flew Over the Cuckoo's Nest")
  ; =>
  ; 31
  ;
  ; @example
  ; (length [])
  ; =>
  ; 2
  ;
  ; @return (integer)
  [n]
  (let [n (str n)]
       (if (-> n empty?)
           (-> 0)
           (-> n count))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn same-length?
  ; @description
  ; Returns TRUE if the given 'a' and 'b' values (converted to string) have a the same length.
  ;
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (same-length? "abc" "def")
  ;
  ; @example
  ; (same-length? "abc" "def")
  ; =>
  ; true
  ;
  ; @example
  ; (same-length? "abc" "defghi")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [a b]
  (= (-> a str count)
     (-> b str count)))

(defn length-min?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) has a length that is not smaller than the given 'min'.
  ;
  ; @param (*) n
  ; @param (integer) min
  ;
  ; @usage
  ; (length-min? "abc" 3)
  ;
  ; @example
  ; (length-min? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length-min? "abc" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n min]
  (let [n (str n)]
       (and (-> min integer?)
            (<= min (-> n count)))))

(defn length-max?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) has a length that is not greater than the given 'max'.
  ;
  ; @param (*) n
  ; @param (integer) max
  ;
  ; @usage
  ; (length-max? "abc" 3)
  ;
  ; @example
  ; (length-max? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length-max? "abc" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n max]
  (let [n (str n)]
       (and (-> max integer?)
            (>= max (-> n count)))))

(defn length-between?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) has a length between the given 'min' and 'max' values.
  ;
  ; @param (*) n
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ; (length-between? "abc" 3 4)
  ; =>
  ; true
  ;
  ; @example
  ; (length-between? "abc" 2 4)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n min max]
  (let [n (str n)]
       (and (<= min (count n))
            (>= max (count n)))))

(defn length?
  ; @description
  ; Returns TRUE if the given 'n' value (converted to string) has the exact same length as the given 'length' value.
  ;
  ; @param (*) n
  ; @param (integer) length
  ;
  ; @example
  ; (length? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length? "abc" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n length]
  (let [n (str n)]
       (= length (-> n count))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn max-length
  ; @param (*) n
  ; @param (integer) limit
  ; @param (*)(opt) suffix
  ;
  ; @usage
  ; (max-length "One Flew Over the Cuckoo's Nest" 5)
  ;
  ; @example
  ; (max-length "One Flew Over the Cuckoo's Nest" 10)
  ; =>
  ; "One Flew O"
  ;
  ; @example
  ; (max-length "One Flew Over the Cuckoo's Nest" 10 " ...")
  ; =>
  ; "One Flew O ..."
  ;
  ; @example
  ; (max-length nil 10)
  ; =>
  ; ""
  ;
  ; @return (string)
  [n limit & [suffix]]
  (let [n (str n)]
       (if (and (-> n empty? not)
                (-> n (dex/dex-in-bounds? limit)))
           (str (subs n 0 limit) suffix)
           (-> n))))
