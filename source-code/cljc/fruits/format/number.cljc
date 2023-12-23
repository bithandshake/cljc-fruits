
(ns fruits.format.number
    (:require [fruits.regex.api  :as regex]
              [fruits.string.api :as string]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn sign-number
  ; @param (number or string) n
  ;
  ; @usage
  ; (sign-number 123.456)
  ;
  ; @example
  ; (sign-number 123.456)
  ; =>
  ; "+123.456"
  ;
  ; @example
  ; (sign-number -123.456)
  ; =>
  ; "-123.456"
  ;
  ; @example
  ; (sign-number 0)
  ; =>
  ; "0"
  ;
  ; @return (string)
  [n]
  (letfn [(f0 [%] (cond (-> % string/first-character (= "-")) (->      %)
                        (-> % (regex/re-match? #"^0+$"))      (->      %)
                        :else                                 (str "+" %)))]
         (mixed/update-number-part n f0)))

(defn group-number
  ; @param (number or string) n
  ; @param (string)(opt) delimiter
  ; Default: ","
  ;
  ; @usage
  ; (group-number 123456.789)
  ;
  ; @example
  ; (group-number 123456.789)
  ; =>
  ; "123,456.789"
  ;
  ; @return (string)
  ([n]
   (group-number n ","))

  ([n delimiter]
   ; group-count: How many three-digit blocks is the 'whole-number' string divisible by.
   ; offset:      After dividing the 'whole-number' string into three-digit blocks,
   ;              how many digits are left out (at the beginning of the 'whole-number' string).
   (letfn [; In case the 'offset' value is 0 (because the number of digits in the 'whole-number' string is divisible by three),
           ; it removes the unnecessary separator character from the beginning of the output in every iteration.
           (f0 [%] (let [whole-number (string/before-first-occurence % "." {:return? true})
                         decimals     (string/from-first-occurence   % "." {:return? false})
                         group-count  (quot (count whole-number) 3)
                         offset       (-    (count whole-number) (* 3 group-count))]
                        (str (string/trim (reduce (fn [result dex]
                                                      (let [x (+ offset (* 3 dex))]
                                                           (str result delimiter (subs whole-number x (+ x 3)))))
                                                  (subs whole-number 0 offset)
                                                  (range group-count)))
                             (-> decimals))))]
          (mixed/update-number-part n f0))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn leading-zeros
  ; @param (number or string) n
  ; @param (integer) length
  ;
  ; @usage
  ; (leading-zeros "123" 6)
  ;
  ; @example
  ; (leading-zeros "123" 6)
  ; =>
  ; "000123"
  ;
  ; @example
  ; (leading-zeros 123 3)
  ; =>
  ; "123"
  ;
  ; @return (string)
  [n length]
  (letfn [(f0 [%] (if (->  % count (< length))
                      (->> % (str "0") f0)
                      (->  %)))
          (f1 [%] (if (-> % string/first-character (= "-"))
                      (str "-" (-> % str (subs 1) f0))
                      (str     (-> % str f0))))]
         (mixed/update-number-part n f1)))

(defn remove-leading-zeros
  ; @param (number or string) n
  ;
  ; @usage
  ; (remove-leading-zeros "000123")
  ;
  ; @example
  ; (remove-leading-zeros "000123")
  ; =>
  ; "123"
  ;
  ; @example
  ; (remove-leading-zeros "-000123")
  ; =>
  ; "-123"
  ;
  ; @return (string)
  [n]
  (letfn [(f0 [%] (if (-> % string/first-character (= "0"))
                      (-> % (subs 1) f0)
                      (-> %)))
          (f1 [%] (if (-> % string/first-character (= "-"))
                      (str "-" (-> % str (subs 1) f0))
                      (str     (-> % str f0))))]
         (mixed/update-number-part n f1)))

(defn trailing-zeros
  ; @param (number or string) n
  ; @param (integer)(opt) length
  ;
  ; @usage
  ; (trailing-zeros "123" 6)
  ;
  ; @example
  ; (trailing-zeros "123" 6)
  ; =>
  ; "123000"
  ;
  ; @return (string)
  [n length]
  (letfn [(f0 [%] (if (-> % count (< length))
                      (-> % (str "0") f0)
                      (-> %)))
          (f1 [%] (if (-> % string/first-character (= "-"))
                      (str "-" (-> % str (subs 1) f0))
                      (str     (-> % str f0))))]
         (mixed/update-number-part n f1)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn decimals
  ; @param (number or string) n
  ; @param (integer)(opt) decimal-places
  ; Default: 2
  ;
  ; @usage
  ; (decimals "123" 3)
  ;
  ; @example
  ; (decimals "123" 3)
  ; =>
  ; "123.000"
  ;
  ; @example
  ; (decimals "123.0000" 3)
  ; =>
  ; "123.000"
  ;
  ; @example
  ; (decimals nil 3)
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n]
   (decimals n 2))

  ([n decimal-places]
   (letfn [(f0 [%] (if (-> % str empty?) (-> %)
                       (if-let [separator-position (string/first-dex-of % ".")]
                               (let [diff (-> % str count (- separator-position decimal-places 1))]
                                    (cond (> diff 0) (subs % 0 (+ separator-position decimal-places 1))
                                          (< diff 0) (str  % (string/repeat "0" (- diff)))
                                          (= diff 0) (->   %)))
                               (str % "." (string/repeat "0" decimal-places)))))]
          (mixed/update-number-part n f0))))

(defn round
  ; @param (number or string) n
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
  (letfn [(f0 [%] (if   (-> % string/last-character (= "0"))
                        (-> % (string/keep-range 0 -1))
                        (-> % (string/insert-part "." -1))))
          ; A divided negative number can be 'ratio' type that cannot be rounded by the 'Math/round' function.
          (f1 [%] (cond (>= %  1000000000) (str (-> % (/ 100000000)        Math/round f0) "B")
                        (>= %  1000000)    (str (-> % (/    100000)        Math/round f0) "M")
                        (>= %  1000)       (str (-> % (/       100)        Math/round f0) "K")
                        (<= % -1000000000) (str (-> % (/ 100000000) double Math/round f0) "B")
                        (<= % -1000000)    (str (-> % (/    100000) double Math/round f0) "M")
                        (<= % -1000)       (str (-> % (/       100) double Math/round f0) "K")
                        :return            (str (-> % Math/round))))]
         (mixed/update-number-part n f1)))
