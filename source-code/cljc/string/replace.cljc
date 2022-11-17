
(ns string.replace
    (:require [clojure.string]
              [candy.api    :refer [return]]
              [string.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-part
  ; @param (*) n
  ; @param (regex or string) x
  ; @param (*) y
  ;
  ; @usage
  ; (replace-part "abc" "b" "x")
  ;
  ; @example
  ; (replace-part "abc" "b" "x")
  ; =>
  ; "axc"
  ;
  ; @example
  ; (replace-part "abc" #"[b]{0,}" "x")
  ; =>
  ; "axc"
  ;
  ; @example
  ; (replace-part "abc" "b" nil)
  ; =>
  ; "ac"
  ;
  ; @return (string)
  [n x y]
  (clojure.string/replace (str n) x
                          (str y)))

(defn use-replacements
  ; @param (*) n
  ; @param (vector) replacements
  ;
  ; @usage
  ; (use-replacements "Hi, my name is %" ["John"])
  ;
  ; @example
  ; (use-replacements "Hi, my name is %" ["John"])
  ; =>
  ; "Hi, my name is John"
  ;
  ; @example
  ; (use-replacements "My favorite colors are: %1, %2 and %3" ["red" "green" "blue"])
  ; =>
  ; "My favorite colors are: red, green and blue"
  ;
  ; @example
  ; (use-replacements "%1 / %2 items downloaded" [nil 3])
  ; =>
  ; ""
  ;
  ; @return (string)
  [n replacements]
  ; XXX#4509
  ;
  ; A behelyettesíthetőséget jelző karakter abban az esetben van számmal jelölve,
  ; (pl. %1, %2, ...) ha a szöveg több behelyettesítést fogad.
  ;
  ; Hasonlóan az anoním függvények paramétereinek elnevezéséhez, ahol az EGY
  ; paramétert fogadó függvények egyetlen paraméterének neve egy számmal NEM
  ; megkülönböztett % karakter és a TÖBB paramétert fogadó függvények paramétereinek
  ; nevei pedig számokkal megkülönböztetett %1, %2, ... elnevezések!
  ;
  ; Abban az esetben, ha valamelyik behelyettesítő kifejezés értéke üres (nil, "")
  ; a függvény visszatérési értéke egy üres string ("")!
  ; Emiatt nem szükséges máshol kezelni, hogy ne jelenjenek meg a hiányos feliratok,
  ; mert ez a use-replacements függvényben kezelve van!
  (let [n (str n)]
       (when (vector? replacements)
                     ; ...
             (letfn [(f? [] (= 1 (count replacements)))
                     ; ...
                     (f1 [n marker replacement]
                         ; A replacement értéke number és string típus is lehet!
                         (if-not (-> replacement str empty?)
                                 (clojure.string/replace n marker replacement)))
                     ; ...
                     (f2 [n dex replacement]
                         (let [marker (str "%" (inc dex))]
                              (f1 n marker replacement)))]
                    ; ...
                    (if (f?) (f1 n "%" (first replacements))
                             (reduce-kv f2 n replacements))))))

(defn use-replacement
  ; @param (*) n
  ; @param (*) replacement
  ;
  ; @usage
  ; (use-replacement "Hi, my name is %" "John")
  ;
  ; @example
  ; (use-replacement "Hi, my name is %" "John")
  ; =>
  ; "Hi, my name is John"
  ;
  ; @return (string)
  [n replacement]
  (clojure.string/replace (str n) "%"
                          (str replacement)))

(defn use-nil
  ; @param (*) n
  ;
  ; @usage
  ; (use-nil "")
  ;
  ; @example
  ; (use-nil "")
  ; =>
  ; nil
  ;
  ; @example
  ; (use-nil "abc")
  ; =>
  ; "abc"
  ;
  ; @return (boolean)
  [n]
  (if (empty? n)
      (return nil)
      (return n)))

(defn use-placeholder
  ; @param (string) n
  ; @param (string) placeholder
  ;
  ; @usage
  ; (use-placeholder "My content" "My placeholder")
  ;
  ; @example
  ; (use-placeholder "My content" "My placeholder")
  ; =>
  ; "My content"
  ;
  ; @example
  ; (use-placeholder "" "My placeholder")
  ; =>
  ; "My placeholder"
  ;
  ; @return (string)
  [n placeholder]
  (if (check/nonblank? n)
      (return          n)
      (return          placeholder)))
