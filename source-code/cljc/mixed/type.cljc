
(ns mixed.type
    (:require [regex.api :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn rational-number?
  ; @param (*) n
  ;
  ; @example
  ; (rational-number? 12)
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? 12.1)
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "12")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "12.1")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "+12.1")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "-12.1")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (or (-> n number?)
      (-> n (re-match? #"^[+-]{0,1}[\d]{1,}$"))
      (-> n (re-match? #"^[+-]{0,1}[\d]{1,}[\.][\d]{1,}$"))))

(defn whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (whole-number? 12)
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "12")
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "+12")
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "-12")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (or (-> n integer?)
      (-> n (re-match? #"^[+-]{0,1}[\d]{1,}$"))))

(defn natural-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (natural-whole-number? 12)
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "12")
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "+12")
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "-12")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (or (-> n nat-int?)
      (-> n (re-match? #"^[+]{0,1}[\d]{1,}$"))))

(defn positive-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (positive-whole-number? 12)
  ; =>
  ; true
  ;
  ; @example
  ; (positive-whole-number? "12")
  ; =>
  ; true
  ;
  ; @example
  ; (positive-whole-number? "+12")
  ; =>
  ; true
  ;
  ; @example
  ; (positive-whole-number? "0")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (or (and (-> n integer?)
           (-> n pos?))
      (and (-> n zero? not)
           (-> n (re-match? #"^[+]{0,1}[\d]{1,}$")))))

(defn negative-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (negative-whole-number? -12)
  ; =>
  ; true
  ;
  ; @example
  ; (negative-whole-number? "12")
  ; =>
  ; false
  ;
  ; @example
  ; (negative-whole-number? "-12")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (or (and (-> n integer?)
           (-> n neg?))
      (-> n (re-match? #"^[-][0-9]{1,}$"))))
