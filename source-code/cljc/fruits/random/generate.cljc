
(ns fruits.random.generate
    (:require [fruits.math.api      :as math]
              [fruits.random.config :as config]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn generate-boolean
  ; @description
  ; Returns a randomly generated boolean value.
  ;
  ; @usage
  ; (generate-boolean)
  ; =>
  ; true
  ;
  ; @param (boolean)
  []
  (-> 2 rand-int zero?))

(defn generate-uuid
  ; @description
  ; Returns a randomly generated UUID string.
  ;
  ; @usage
  ; (generate-uuid)
  ; =>
  ; "ko4983l3-i8790-j93l3-lk8385u591o2"
  ;
  ; @return (string)
  []
  ; @bug (fruits.random.config#5570)
  #?(:cljs (str config/NAME-PREFIX (random-uuid))
     :clj  (str config/NAME-PREFIX (java.util.UUID/randomUUID))))

(defn generate-string
  ; @description
  ; Returns a randomly generated UUID string.
  ;
  ; @usage
  ; (generate-string)
  ; =>
  ; "ko4983l3-i8790-j93l3-lk8385u591o2"
  ;
  ; @return (string)
  []
  (generate-uuid))

(defn generate-keyword
  ; @description
  ; Returns a randomly generated UUID keyword.
  ;
  ; @param (string)(opt) namespace
  ;
  ; @usage
  ; (generate-keyword)
  ; =>
  ; :ko4983l3-i8790-j93l3-lk8385u591o2
  ;
  ; @usage
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
  ; @description
  ; Returns a randomly generated namespaced UUID keyword.
  ;
  ; @usage
  ; (generate-namespaced-keyword)
  ; =>
  ; :ko4983l3-i8790-j93l3-lk8385u591o2/ab5069i3-z8700-l89z6-op4450p510p4
  ;
  ; @return (namespaced keyword)
  []
  ; @bug (fruits.random.config#5570)
  (keyword (str (generate-uuid) "/" (str config/NAME-PREFIX (generate-uuid)))))

(defn generate-integer
  ; @description
  ; Returns a randomly generated integer.
  ;
  ; @param (integer)(opt) digits
  ; Default: 4
  ;
  ; @usage
  ; (generate-integer 3)
  ; =>
  ; 420
  ;
  ; @return (integer)
  ([]
   (generate-integer 4))

  ([digits]
   ; Warning! Decimal points (.) and decimal commas (,) are different in the following text! Do not mix them up!
   ;
   ; Step 1: Generating a random float number from 0.000' to 9.000' | (-> 9 rand)
   ; Step 2: Increasing the result by one (1.000' to 10.000')       | (-> 9 rand inc)
   ; Step 3: Multiplying the result by 10 to the power of (n - 1)   | (* (math/power 10 (dec digits)) ...)
   ; Step 4: Setting a limit for the result to its maximum minus 1  | (min (...) (dec (math/power 10 digits)))
   ; Step 5: Converting the result to integer type.                 | (int ...)
   ;
   ; E.g., The expected digit count is 5
   ;       10 to the power of (5 - 1) is 10,000
   ;       Step 1: 0.000' to 9.000'
   ;       Step 2: 1.000' to 10.000'
   ;       Step 3: 10,000 to 100,000
   ;       Step 4: 10,000 to 99,999
   (if (integer? digits)
       (int (min (*   (math/power 10 (-> digits dec)) (-> 9 rand inc))
                 (dec (math/power 10 (-> digits))))))))
