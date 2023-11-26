
(ns format.number
    (:require [regex.api  :refer [re-match?]]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sign-number
  ; @param (number or string) n
  ;
  ; @usage
  ; (sign-number 4200.5)
  ;
  ; @example
  ; (sign-number 4200.5)
  ; =>
  ; "+4200.5"
  ;
  ; @example
  ; (sign-number -4200.5)
  ; =>
  ; "-4200.5"
  ;
  ; @example
  ; (sign-number 0)
  ; =>
  ; "0"
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (cond (-> n string/first-character (= "-")) (->      n)
             (-> n (re-match? #"^0+$"))            (->      n)
             :else                                 (str "+" n))))

(defn group-number
  ; @param (number or string) n
  ; @param (string)(opt) delimiter
  ; Default: ","
  ;
  ; @usage
  ; (group-number 4200.5)
  ;
  ; @example
  ; (group-number 4200.5)
  ; =>
  ; "4,200.5"
  ;
  ; @return (string)
  ([n]
   (group-number n ","))

  ([n delimiter]
   ; groupable:   The first block of 'n' string that only contains digits.
   ; group-count: How many three-character blocks is the 'groupable' string divisible by.
   ; offset:      After dividing the 'groupable' string into three-character blocks,
   ;              how many characters are left out (at the beginning of the 'groupable' string).
   (let [n           (str n)
         groupable   (re-find #"\d+" n)
         group-count (quot (count groupable) 3)
         offset      (-    (count groupable) (* 3 group-count))]
        ; In case the 'offset' value is 0 (because the number of characters in the 'groupable' string is divisible by three),
        ; it is necessary to remove the unnecessary separator character from the beginning of the loop's output.
        (str (string/trim (reduce (fn [result dex]
                                      (let [x (+ offset (* 3 dex))]
                                           (str result delimiter (subs groupable x (+ x 3)))))
                                  (subs groupable 0 offset)
                                  (range group-count)))
             ; It appends the non-grouped part of the original 'n' string (the part after the 'groupable' string).
             (subs n (count groupable))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn leading-zeros
  ; @param (number or string) n
  ; @param (integer) length
  ;
  ; @usage
  ; (leading-zeros "420" 5)
  ;
  ; @example
  ; (leading-zeros 7 3)
  ; =>
  ; "007"
  ;
  ; @example
  ; (leading-zeros 420 3)
  ; =>
  ; "420"
  ;
  ; @return (string)
  [n length]
  ; TODO
  ; It doesn't handle the "-" and "+" signs!
  (let [n (str n)]
       (letfn [(f0 [n]
                   (if (->  n count (< length))
                       (->> n (str "0") f0)
                       (->  n)))]
              (f0 n))))

(defn remove-leading-zeros
  ; @param (number or string) n
  ;
  ; @usage
  ; (remove-leading-zeros "042")
  ;
  ; @example
  ; (remove-leading-zeros 42)
  ; =>
  ; "42"
  ;
  ; @example
  ; (remove-leading-zeros "0042")
  ; =>
  ; "42"
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (letfn [(f0 [n]
                   (if-not (-> n string/first-character (= "0"))
                           (-> n)
                           (-> n (subs 1) f0)))]
              (f0 n))))

(defn trailing-zeros
  ; @param (integer or string) n
  ; @param (integer)(opt) length
  ;
  ; @usage
  ; (trailing-zeros "420" 5)
  ;
  ; @example
  ; (trailing-zeros 7 3)
  ; =>
  ; "700"
  ;
  ; @return (string)
  [n length]
  (let [n (str n)]
       (letfn [(f0 [n]
                   (if (-> n count (< length))
                       (-> n (str "0") f0)
                       (-> n)))]
              (f0 n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn decimals
  ; @param (number or string) n
  ; @param (integer)(opt) decimal-places
  ; Default: 2
  ;
  ; @usage
  ; (decimals "420" 2)
  ;
  ; @example
  ; (decimals "1" 2)
  ; =>
  ; "1.00"
  ;
  ; @example
  ; (decimals "11.0000" 3)
  ; =>
  ; "11.000"
  ;
  ; @example
  ; (decimals nil 2)
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n]
   (decimals n 2))

  ([n decimal-places]
   (let [n (str n)]
        (if ; If the 'n' string is empty ...
            (-> n count (< 1)) (-> n)
            ; If the 'n' string is NOT empty ...
            (if-let [separator-position (string/first-dex-of n ".")]
                    ; If the 'n' string contains a "." character ...
                    (let [diff (-> n count (- separator-position decimal-places 1))]
                         (cond ; If the 'n' string is too long ...
                               (> diff 0)
                               (subs n 0 (+ separator-position decimal-places 1))
                               ; If the 'n' string is too short ...
                               (< diff 0)
                               (str n (string/repeat "0" (- diff)))
                               ; If the 'n' string is ...
                               (= diff 0)
                               (-> n)))
                    ; If the 'n' string doesn't contain a "." character ...
                    (str n "." (string/repeat "0" decimal-places)))))))

(defn round
  ; @param (number) n
  ;
  ; @usage
  ; (round 1234)
  ;
  ; @example
  ; (round 1740)
  ; =>
  ; "1.7K"
  ;
  ; @example
  ; (round 2023)
  ; =>
  ; "2K"
  ;
  ; @example
  ; (round 1000420)
  ; =>
  ; "1M"
  ;
  ; @example
  ; (round 1000420069)
  ; =>
  ; "1B"
  ;
  ; @return (string)
  [n]
  (letfn [(f0 [%]
              (if (-> % string/last-character (= "0"))
                  (-> % (string/keep-range 0 -1))
                  (-> % (string/insert-part "." -1))))]
         (cond (>= n 1000000000) (str (f0 (Math/round (/ n 100000000))) "B")
               (>= n 1000000)    (str (f0 (Math/round (/ n    100000))) "M")
               (>= n 1000)       (str (f0 (Math/round (/ n       100))) "K")
               :return           (str (Math/round n)))))
