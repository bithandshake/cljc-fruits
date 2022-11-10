
(ns format.core
    (:require [mid-fruits.candy  :refer [param return]]
              [mid-fruits.mixed  :as mixed]
              [mid-fruits.string :as string]
              [mid-fruits.vector :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn group-number
  ; @warning
  ;  Nagy mennyiségben és gyakori frissítéssel megjelenített számok – group-number függvénnyel való
  ;  csoportosítával – megjelenítésekor a függvény további optimalizására is szükség lehet.
  ;
  ;  A számjegyek csoportosításánál használt elválasztó a white-space karakter (" "),
  ;  a csoportok mérete pedig 3 karakterben van rögzítve. Ezen értékek paraméterként
  ;  nem átadhatók, ezzel is csökkentve a függvény számításikapacitás-igényét.
  ;
  ; @param (number or string) n
  ;
  ; @example
  ;  (group-number 4200.5)
  ;  =>
  ;  "4 200.5"
  ;
  ; @return (string)
  [n]
  (let [; base:        az n string első (kizárólag) számjegyekből álló blokkja
        ; group-count: a base string hány darab három karakteres blokkra osztható
        ; offset:      a base string három karakteres blokkokra osztása után hány karakter marad ki (a base string elején)
        base        (re-find #"\d+" n)
        group-count (quot (count base) 3)
        offset      (-    (count base) (* 3 group-count))]
       (str ; Abban az esetben, ha az offset értéke 0, mert a base karaktereinek száma hárommal osztható,
            ; szükséges a ciklus kimeneti értékének elejéről a felesleges elválasztó karaktert eltávolítani!
            (string/trim (reduce (fn [result dex]
                                     (let [x (+ offset (* 3 dex))]
                                          (str result " " (subs base x (+ x 3)))))
                                 (subs base 0 offset)
                                 (range group-count)))
            ; A csoportosítás kimenetéhez szükséges hozzáfűzni az n string nem csoportosított részét
            ; (a base string után következő részt)
            (subs n (count base)))))

(defn leading-zeros
  ; @param (integer or string) n
  ; @param (integer) length
  ;
  ; @example
  ;  (leading-zeros 7 3)
  ;  =>
  ;  "007"
  ;
  ; @return (string)
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str "0" x))
            (return x))))

(defn trailing-zeros
  ; @param (integer or string) n
  ; @param (integer)(opt) length
  ;
  ; @example
  ;  (trailing-zeros 7 3)
  ;  =>
  ;  "700"
  ;
  ; @return (string)
  [n length]
  (loop [x (str n)]
        (if (< (count x) length)
            (recur (str x "0"))
            (return x))))

(defn decimals
  ; @param (integer or string) n
  ; @param (integer)(opt) length
  ;  Default: 2
  ;
  ; @example
  ;  (decimals nil 2)
  ;  =>
  ;  ""
  ;
  ; @example
  ;  (decimals "1" 2)
  ;  =>
  ;  "1.00"
  ;
  ; @example
  ;  (decimals "11.0000" 3)
  ;  =>
  ;  "11.000"
  ;
  ; @return (string)
  ([n]
   (decimals n 2))

  ([n length]
   (let [x     (str   n)
         count (count x)]
        (if ; If x is an empty string ...
            (< count 1) x
            ; If x is NOT an empty string ...
            (if-let [separator-index (string/first-dex-of x ".")]
                    ; If x is contains "." ...
                    (let [diff (- count separator-index length 1)]
                         (cond ; If x is too long ...
                               (> diff 0)
                               (subs x 0 (+ separator-index (inc length)))
                               ; If x is too short ...
                               (< diff 0)
                               (str x (trailing-zeros nil (- 0 diff)))
                               ; If x is ...
                               (= diff 0) x))
                    ; If x is NOT contains "." ...
                    (str x "." (trailing-zeros nil length)))))))

(defn round
  ; @param (number) n
  ;
  ; @example
  ;  (round 1740)
  ;  =>
  ;  "2K"
  ;
  ; @example
  ;  (round 1000420)
  ;  =>
  ;  "1M"
  ;
  ; @return (string)
  [n]
  (cond (>= n 1000000) (str (Math/round (/ n 1000000)) "M")
        (>= n 1000)    (str (Math/round (/ n 1000))    "K")
        :return        (str (Math/round n))))

(defn inc-version
  ; @param (string) n
  ;
  ; @example
  ;  (inc-version "1.2.19")
  ;  =>
  ;  "1.2.20"
  ;
  ; @return (string)
  [n]
  (letfn [(f [n x]
             (if (:increased? x)
                 (if (-> x :separators vector/nonempty?)
                     (f (string/insert-part n "." (-> x :separators last))
                        (update x :separators vector/pop-last-item))
                     (return n))
                 (if-let [separator (string/first-dex-of   n ".")]
                         (f (string/remove-first-occurence n ".")
                            (update x :separators conj separator))
                         (f (leading-zeros (mixed/update-whole-number n inc) (count n))
                            (assoc x :increased? true)))))]
         (f n {:separators []})))
