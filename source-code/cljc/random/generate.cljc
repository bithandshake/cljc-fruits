
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
  ; A (-> 9 rand inc) függvény kimenete egy lebegőpontos érték 1 és 10 között
  ;
  ; A (generate-number n) fügvény visszatérési értéke egy 1 és 9.99 között érték szorozva
  ; 10 (n - 1) hatványával, integer típusra alakítva.
  (int (* (math/power 10 (dec digits)) (min 9.999 (-> 9 rand inc)))))
