
(ns mixed.type
    (:require [regex.api :refer [re-match?]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn nonempty?
  ; @param (*) n
  ;
  ; @example
  ; (nonempty? nil)
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? "")
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? [])
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? {})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  ; Az empty? függvényt csak a seqable értékeken lehetséges alkalmazni!
  ;
  ; (A) Ha az n értéke nem seqable, akkor igaz rá, hogy nonempty (keyword, integer, ...)
  ;
  ; (B) Ha az n értéke seqable, akkor megvizsgálja, hogy üres-e (nil, map, string, vector, ...)
  (or ; (A)
      (-> n seqable? not)
      ; (B)
      (-> n empty?   not)))

(defn blank?
  ; @param (*) n
  ;
  ; @example
  ; (blank? nil)
  ; =>
  ; true
  ;
  ; @example
  ; (blank? "")
  ; =>
  ; true
  ;
  ; @example
  ; (blank? [])
  ; =>
  ; true
  ;
  ; @example
  ; (=blank? {})
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  ; Az empty? függvényt csak a seqable értékeken lehetséges alkalmazni!
  ; (A nonseqable értékek nem lehetnek üresek! Pl.: :keyword)
  (and (-> n seqable?)
       (-> n   empty?)))

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
  (or (number?   n)
      (re-match? n #"^[+-]{0,1}[\d]{1,}$")
      (re-match? n #"^[+-]{0,1}[\d]{1,}[\.][\d]{1,}$")))

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
  (or (integer?  n)
      (re-match? n #"^[+-]{0,1}[\d]{1,}$")))

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
  (or (and (integer? n)
           (<= 0     n))
      (re-match? n #"^[+]{0,1}[\d]{1,}$")))

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
  (or (and (integer?  n)
           (<    0    n))
      (and (not= 0    n)
           (re-match? n #"^[+]{0,1}[\d]{1,}$"))))

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
  (or (and (integer? n)
           (> 0      n))
      (re-match? n #"^[-][0-9]{1,}$")))
