
(ns math.domain
    (:require [candy.api :refer [param return]]
              [math.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; Egy n szám milyen tulajdonságokkal rendelkezik egy tartományban.
; Pl. 5 tartományai: -4–0, 1–5, 6–10, 11–15, ...

(defn domain-inchoate
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ;  (domain-inchoate 9 5)
  ;  =>
  ;  2
  ;
  ; @example
  ;  (domain-inchoate 10 5)
  ;  =>
  ;  2
  ;
  ; @example
  ;  (domain-inchoate 11 5)
  ;  =>
  ;  3
  ;
  ; @example
  ;  (domain-inchoate 0 5)
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n domain]
  ; Az n értéke hányadik domain tartományban helyezkedik el
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
           (return quot)
           (inc    quot))))

(defn domain-floor
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ;  (domain-floor 9 5)
  ;  =>
  ;  6
  ;
  ; @example
  ;  (domain-floor 10 5)
  ;  =>
  ;  6
  ;
  ; @example
  ;  (domain-floor 11 5)
  ;  =>
  ;  11
  ;
  ; @example
  ;  (domain-floor 0 5)
  ;  =>
  ;  -4
  ;
  ; @return (integer)
  [n domain]
  ; Az n értékéhez tartozó tartomány kezdő értéke
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
           (inc (* (dec quot) domain))
           (inc (*      quot  domain)))))

(defn domain-ceil
  ; @param (integer) n
  ; @param (integer) domain
  ;
  ; @example
  ;  (domain-ceil 9 5)
  ;  =>
  ;  10
  ;
  ; @example
  ;  (domain-ceil 10 5)
  ;  =>
  ;  10
  ;
  ; @example
  ;  (domain-ceil 11 5)
  ;  =>
  ;  15
  ;
  ; @example
  ;  (domain-ceil 0 5)
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n domain]
  ; Az n értékéhez tartozó tartomány záró értéke
  (let [quot (quot n domain)
        rem  (rem  n domain)]
       (if (= rem 0)
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
  ;  (choose 4.20 42 "A" "B")
  ;  =>
  ;  "B"
  ;
  ; @example
  ;  (choose 42 4.20 "A" "B")
  ;  =>
  ;  "A"
  ;
  ; @return (*)
  [n limit value-if-bigger & [value-if-smaller]]
  (if (>= limit n) value-if-smaller value-if-bigger))

(defn calc
  ; A calc fuggveny kiszamolja egy A valtozo erteketol fuggo B valtozo
  ; pillanatnyi erteket.
  ; Pl. Egy elem left position erteke fuggjon a scroll-y kornyezeti valtozotol
  ;
  ; A fuggveny mukodese:
  ; Az n erteke amint eleri a domain-from erteket,
  ; akkor a fuggveny kimenete range-from ertekrol indul es amikor az n erteke
  ; elerei a domain-to erteket addigra a fuggveny kimenete eleri a range-to erteket.
  ;
  ; @param (float, int) n
  ;  Az A valtozo pillanatnyi erteke
  ; @param (vector) domain
  ;  Az A valtozo ertelmezesi tartomanya
  ;  XXX A domain-from mindig legyen kisebb, mint a domain-to!
  ;  [(integer) domain-from
  ;   (integer) domain-to
  ; @param (vector) range
  ;  A B valtozo kimeneti tartomanya
  ;  [(integer) range-from
  ;   (integer) range-to]
  ;
  ; @example
  ;  (calc 42 [10 50] [100 500])
  ;  =>
  ;  420
  ;
  ; @return (*)
  ;  A B valtozo pillanatnyi erteke (az A valtozotol fuggoen)
  [n [domain-from domain-to] [range-from range-to]]
  (let [domain-length (- domain-to domain-from)
        domain-offset (- n domain-from)
        range-length  (- range-to range-from)
        range-offset  (core/absolute range-from)
        ratio         (/ range-length domain-length)]
       (if (< n domain-from)
           (return range-from)
           (if (> n domain-to)
               (return range-to)
               (+ (param range-from)
                  (* domain-offset ratio))))))
