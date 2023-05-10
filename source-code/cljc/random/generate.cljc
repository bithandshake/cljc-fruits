
(ns random.generate
    (:require [math.api      :as math]
              [random.config :as config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn generate-boolean
  ; @usage
  ; (generate-boolean)
  ;
  ; @example
  ; (generate-boolean)
  ; =>
  ; true
  ;
  ; @param (boolean)
  []
  (-> 2 rand-int zero?))

(defn generate-uuid
  ; @usage
  ; (generate-uuid)
  ;
  ; @example
  ; (generate-uuid)
  ; =>
  ; "ko4983l3-i8790-j93l3-lk8385u591o2"
  ;
  ; @return (string)
  []
  ; BUG#5570
  #?(:cljs (str config/NAME-PREFIX (random-uuid))
     :clj  (str config/NAME-PREFIX (java.util.UUID/randomUUID))))

(defn generate-string
  ; @usage
  ; (generate-string)
  ;
  ; @example
  ; (generate-string)
  ; =>
  ; "ko4983l3-i8790-j93l3-lk8385u591o2"
  ;
  ; @return (string)
  []
  (generate-uuid))

(defn generate-keyword
  ; @param (string)(opt) namespace
  ;
  ; @usage
  ; (generate-keyword)
  ;
  ; @example
  ; (generate-keyword)
  ; =>
  ; :ko4983l3-i8790-j93l3-lk8385u591o2
  ;
  ; @example
  ; (generate-keyword :my-namespace)
  ; =>
  ; :my-namespace/ko4983l3-i8790-j93l3-lk8385u591o2
  ;
  ; @return (keyword)
  ([]
   (keyword (generate-uuid)))

  ([namespace]
   (keyword (str namespace "/" (generate-uuid)))))

(defn generate-namespaced-keyword
  ; @usage
  ; (generate-namespaced-keyword)
  ;
  ; @example
  ; (generate-namespaced-keyword)
  ; =>
  ; :ko4983l3-i8790-j93l3-lk8385u591o2/ab5069i3-z8700-l89z6-op4450p510p4
  ;
  ; @return (namespaced keyword)
  []
  ; BUG#5570
  (keyword (str (generate-uuid) "/" (str config/NAME-PREFIX (generate-uuid)))))

(defn generate-react-key
  ; @usage
  ; (generate-react-key)
  ;
  ; @example
  ; (generate-react-key)
  ; =>
  ; "ko4983l3-i8790-j93l3-lk8385u591o2"
  ;
  ; @return (string)
  []
  (generate-uuid))

(defn generate-number
  ; @param (integer) digits
  ;
  ; @usage
  ; (generate-number 5)
  ;
  ; @example
  ; (generate-number 3)
  ; =>
  ; 420
  ;
  ; @return (integer)
  [digits]
  ; Warning! Decimal points (.) and decimal commas (,) in the text! Don't mix up them!
  ;
  ; Step 1: Generating a random float number from 0.000' to 9.000' | (-> 9 rand)
  ; Step 2: Increasing the result by one (1.000' to 10.000')       | (-> 9 rand inc)
  ; Step 3: Multiplying the result by 10 to the power of (n - 1)   | (* (math/power 10 (dec digits)) ...)
  ; Step 4: Set a limit for the result to its maximum minus 1      | (min (...) (dec (math/power 10 digits)))
  ; Step 5: Converting the result to integer type.                 | (int ...)
  ;
  ; E.g. The expected digit count is 5
  ;      10 to the power of (5 - 1) is 10,000
  ;      Step 1: 0.000' to 9.000'
  ;      Step 2: 1.000' to 10.000'
  ;      Step 3: 10,000 to 100,000
  ;      Step 4: 10,000 to 99,999
  (int (min (* (math/power 10 (dec digits)) (-> 9 rand inc))
            (dec (math/power 10 digits)))))
