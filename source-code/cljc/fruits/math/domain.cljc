
(ns fruits.math.domain
    (:require [fruits.math.polarity :as polarity]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn domain-inchoate
  ; @description
  ; - Returns which domain contains the 'n' value.
  ; - E.g., If 'n' is 3 and the 'domain' is 5, that means 'n' falls into the first domain of 5 (1 - 5).
  ;         If 'n' is 8 and the 'domain' is 5, that means 'n' falls into the second domain of 5 (6 - 10).
  ;
  ; @param (number) n
  ; @param (number) domain
  ;
  ; @usage
  ; (domain-inchoate 0 5)
  ; =>
  ; 0
  ;
  ; @usage
  ; (domain-inchoate 9 5)
  ; =>
  ; 2
  ;
  ; @usage
  ; (domain-inchoate 10 5)
  ; =>
  ; 2
  ;
  ; @usage
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
  ; @param (number) n
  ; @param (number) domain
  ;
  ; @usage
  ; (domain-floor 9 5)
  ; =>
  ; 6
  ;
  ; @usage
  ; (domain-floor 10 5)
  ; =>
  ; 6
  ;
  ; @usage
  ; (domain-floor 11 5)
  ; =>
  ; 11
  ;
  ; @usage
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
  ; @param (number) n
  ; @param (number) domain
  ;
  ; @usage
  ; (domain-ceil 9 5)
  ; =>
  ; 10
  ;
  ; @usage
  ; (domain-ceil 10 5)
  ; =>
  ; 10
  ;
  ; @usage
  ; (domain-ceil 11 5)
  ; =>
  ; 15
  ;
  ; @usage
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

(defn domain-project
  ; @description
  ; If the given 'n' value ...
  ; A) is equal to or smaller than the given 'input-min' value ...
  ;    ... returns the given 'output-min' value.
  ; B) is equal to or greater than the given 'input-max' value ...
  ;    ... returns the given 'output-max' value.
  ; C) is in the input domain ...
  ;    takes the actual position of 'n' in the input domain and projects it to the output range.
  ;
  ; @param (number) n
  ; @param (vector) input-domain
  ; [(integer) input-min
  ;  (integer) input-max
  ; @param (vector) output-range
  ; [(integer) output-min
  ;  (integer) output-max]
  ;
  ; @usage
  ; (project 42 [10 50] [100 500])
  ; =>
  ; 420
  ;
  ; @usage
  ; (project 10 [10 50] [100 500])
  ; =>
  ; 100
  ;
  ; @usage
  ; (project 5 [10 50] [100 500])
  ; =>
  ; 100
  ;
  ; @usage
  ; (project 50 [10 50] [100 500])
  ; =>
  ; 500
  ;
  ; @usage
  ; (project 55 [10 50] [100 500])
  ; =>
  ; 500
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
        range-offset  (polarity/absolute output-min)
        ratio         (/ range-length domain-length)]
       (if (< n input-min)
           (-> output-min)
           (if (> n input-max)
               (-> output-max)
               (+ output-min (* domain-offset ratio))))))
