
(ns fruits.mixed.type
    (:refer-clojure :exclude [number?])
    (:require [fruits.regex.api :as regex]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn number?
  ; @param (*) n
  ;
  ; @example
  ; (number? 123)
  ; =>
  ; true
  ;
  ; @example
  ; (number? 123.456)
  ; =>
  ; true
  ;
  ; @example
  ; (number? "123")
  ; =>
  ; true
  ;
  ; @example
  ; (number? "123.456")
  ; =>
  ; true
  ;
  ; @example
  ; (number? "+123.456")
  ; =>
  ; true
  ;
  ; @example
  ; (number? "-123.456")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (or (-> n number?)
      (-> n (regex/re-match? #"^[\+\-]{0,1}[\d]{1,}$"))
      (-> n (regex/re-match? #"^[\+\-]{0,1}[\d]{1,}[\.][\d]{1,}$"))))

(defn rational-number?
  ; @param (*) n
  ;
  ; @example
  ; (rational-number? 123)
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? 123.456)
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "123")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "123.456")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "+123.456")
  ; =>
  ; true
  ;
  ; @example
  ; (rational-number? "-123.456")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (number? n))

(defn whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (whole-number? 123)
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "123")
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "+123")
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "-123")
  ; =>
  ; true
  ;
  ; @example
  ; (whole-number? "123.456")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (or (-> n integer?)
      (-> n (regex/re-match? #"^[\+\-]{0,1}[\d]{1,}$"))))

(defn natural-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (natural-whole-number? 123)
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "123")
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "+123")
  ; =>
  ; true
  ;
  ; @example
  ; (natural-whole-number? "-123")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (or (-> n nat-int?)
      (-> n (regex/re-match? #"^[\+]{0,1}[\d]{1,}$"))))

(defn positive-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (positive-whole-number? 123)
  ; =>
  ; true
  ;
  ; @example
  ; (positive-whole-number? "123")
  ; =>
  ; true
  ;
  ; @example
  ; (positive-whole-number? "+123")
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
  (or (-> n pos-int?)
      (and (-> n (regex/re-mismatch? #"^[\+]{0,1}[0]{1,}$"))
           (-> n (regex/re-match?    #"^[\+]{0,1}[\d]{1,}$")))))

(defn negative-whole-number?
  ; @param (*) n
  ;
  ; @example
  ; (negative-whole-number? -123)
  ; =>
  ; true
  ;
  ; @example
  ; (negative-whole-number? "-123")
  ; =>
  ; true
  ;
  ; @example
  ; (negative-whole-number? "123")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (or (-> n neg-int?)
      (and (-> n (regex/re-mismatch? #"^[-][0]{1,}$"))
           (-> n (regex/re-match?    #"^[-][\d]{1,}$")))))
