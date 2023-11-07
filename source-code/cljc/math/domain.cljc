
(ns math.domain
    (:require [math.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn domain-inchoate
  ; @description
  ; - Returns which domain contains the 'n' value.
  ; - E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5).
  ;         If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
  ;
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ; (domain-inchoate 0 5)
  ; =>
  ; 0
  ;
  ; @example
  ; (domain-inchoate 9 5)
  ; =>
  ; 2
  ;
  ; @example
  ; (domain-inchoate 10 5)
  ; =>
  ; 2
  ;
  ; @example
  ; (domain-inchoate 11 5)
  ; =>
  ; 3
  ;
  ; @return (integer)
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (->  rem zero?)
           (->  quot)
           (inc quot))))

(defn domain-floor
  ; @description
  ; - Returns the floor value of the domain that contains the 'n' value.
  ; - E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5),
  ;         and it returns the floor value of the first domain: 1.
  ;         If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
  ;         and it returns the floor value of the second domain: 6.
  ;
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ; (domain-floor 9 5)
  ; =>
  ; 6
  ;
  ; @example
  ; (domain-floor 10 5)
  ; =>
  ; 6
  ;
  ; @example
  ; (domain-floor 11 5)
  ; =>
  ; 11
  ;
  ; @example
  ; (domain-floor 0 5)
  ; =>
  ; -4
  ;
  ; @return (integer)
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (-> rem zero?)
           (inc (* (dec quot) domain))
           (inc (*      quot  domain)))))

(defn domain-ceil
  ; @description
  ; - Returns the ceil value of the domain that contains the 'n' value.
  ; - E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5),
  ;         and it returns the ceil value of the first domain: 5.
  ;         If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
  ;         and it returns the ceil value of the second domain: 10.
  ;
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ; (domain-ceil 9 5)
  ; =>
  ; 10
  ;
  ; @example
  ; (domain-ceil 10 5)
  ; =>
  ; 10
  ;
  ; @example
  ; (domain-ceil 11 5)
  ; =>
  ; 15
  ;
  ; @example
  ; (domain-ceil 0 5)
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n domain]
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (-> rem zero?)
           (*      quot  domain)
           (* (inc quot) domain))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn choose
  ; @param (integer) n
  ; @param (integer) limit
  ; @param (*) value-if-bigger
  ; @param (*)(opt) value-if-smaller
  ;
  ; @example
  ; (choose 4.20 42 "A" "B")
  ; =>
  ; "B"
  ;
  ; @example
  ; (choose 42 4.20 "A" "B")
  ; =>
  ; "A"
  ;
  ; @return (*)
  [n limit value-if-bigger & [value-if-smaller]]
  (if (>= limit n) value-if-smaller value-if-bigger))

(defn calc
  ; @description
  ; If 'n' is equal to or smaller than the input-min, returns the 'output-min'.
  ; If 'n' is equal to or greater than the input-max, returns the 'output-max'.
  ; If 'n' is in the input domain, takes the actual position of 'n' in the input
  ; domain and projects the taken position to the output range.
  ;
  ; @param (number) n
  ; @param (vector) input-domain
  ; [(integer) input-min
  ;  (integer) input-max
  ; @param (vector) output-range
  ; [(integer) output-min
  ;  (integer) output-max]
  ;
  ; @example
  ; (calc 42 [10 50] [100 500])
  ; =>
  ; 420
  ;
  ; @return (*)
  [n [input-min input-max :as input-domain] [output-min output-max :as output-range]]
  (let [input-min     (min (first input-domain) (second input-domain))
        input-max     (max (first input-domain) (second input-domain))
        output-min    (min (first output-range) (second output-range))
        output-max    (max (first output-range) (second output-range))
        domain-length (- input-max input-min)
        domain-offset (- n input-min)
        range-length  (- output-max output-min)
        range-offset  (core/absolute output-min)
        ratio         (/ range-length domain-length)]
       (if (< n input-min)
           (-> output-min)
           (if (> n input-max)
               (-> output-max)
               (+ output-min (* domain-offset ratio))))))
