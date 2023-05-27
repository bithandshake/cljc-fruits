
(ns audit.generate
    (:require [loop.api   :refer [reduce-range]]
              [math.api   :as math]
              [random.api :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn generate-pin-code
  ; @param (integer)(opt) length
  ; Default: 4
  ;
  ; @usage
  ; (generate-pin-code)
  ;
  ; @example
  ; (generate-pin-code)
  ; =>
  ; "0420"
  ;
  ; @example
  ; (generate-pin-code 6)
  ; =>
  ; "042069"
  ;
  ; @return (string)
  ([]
   (generate-pin-code 4))

  ([length]
   (-> length random/generate-number str)))

(defn generate-password
  ; @param (integer)(opt) length
  ; Default: 8
  ; Min: 4
  ;
  ; @usage
  ; (generate-password)
  ;
  ; @example
  ; (generate-password 4)
  ; =>
  ; "Yi4_"
  ;
  ; @example
  ; (generate-password 5)
  ; =>
  ; "YQi4_"
  ;
  ; @return (string)
  ([]
   (generate-password 8))

  ([length]
   (let [length (max 4 length)]
        (letfn [(set-f  [from to]    (map char (range (int from) (inc (int to)))))
                (part-f [set length] (reduce-range (fn [%1 _] (str %1 (rand-nth set))) nil length))
                (rem-f  [pos] (if (<= pos (rem length 4)) 1 0))]
               (let [lower-chars (set-f \a \z)
                     upper-chars (set-f \A \Z)
                     digits      (set-f \0 \9)
                     specials    [\.\-\_\!\?\#\*]]
                    (str (part-f upper-chars (+ (math/floor (/ length 4)) (rem-f 1)))
                         (part-f lower-chars (+ (math/floor (/ length 4)) (rem-f 2)))
                         (part-f digits      (+ (math/floor (/ length 4)) (rem-f 3)))
                         (part-f specials    (+ (math/floor (/ length 4)) (rem-f 4)))))))))
