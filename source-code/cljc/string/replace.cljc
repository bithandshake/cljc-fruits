
(ns string.replace
    (:require [clojure.string]
              [candy.api    :refer [return]]
              [string.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn replace-part
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (*) y
  ; @param (map)(opt) options
  ;  {:recursive? (boolean)(opt)
  ;    Default: false}
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
  ; @example
  ; (replace-part "///" "//" "/")
  ; =>
  ; "//"
  ;
  ; @example
  ; (replace-part "///" "//" "/" {:recursive? true})
  ; =>
  ; "/"
  ;
  ; @return (string)
  ([n x y]
   (replace-part n x y {}))

  ([n x y {:keys [recursive?]}]
   (letfn [(f [n] (clojure.string/replace (str n) x
                                          (str y)))
           (r [n] (if (= n (f n))
                      (return n)
                      (r (f n))))]
          (if recursive? (r n)
                         (f n)))))

(defn use-replacements
  ; @param (*) n
  ; @param (vector) replacements
  ; @param (map)(opt) options
  ;  {:ignore? (boolean)(opt)
  ;    The function returns nil if any of the replacements is nil or empty.
  ;    Default: true}
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
  ([n replacements]
   (use-replacements n replacements {}))

  ([n replacements {:keys [ignore?] :or {ignore? true}}]
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
                          ; The replacement's value could be any type!
                          (if (or (-> replacement str empty? not)
                                  (not ignore?))
                              (clojure.string/replace n marker replacement)))
                      ; ...
                      (f2 [n dex replacement]
                          (let [marker (str "%" (inc dex))]
                               (f1 n marker replacement)))]
                     ; ...
                     (if (f?) (f1 n "%" (first replacements))
                              (reduce-kv f2 n replacements)))))))

(defn use-replacement
  ; @param (*) n
  ; @param (*) replacement
  ; @param (map)(opt) options
  ;  {:ignore? (boolean)(opt)
  ;    The function returns nil if the replacement is nil or empty.
  ;    Default: true}
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
  ([n replacement]
   (use-replacement n replacement {}))

  ([n replacement {:keys [ignore?] :or {ignore? true}}]
   ; The replacement's value could be any type!
   (if (or (-> replacement str empty? not)
           (not ignore?))
       (clojure.string/replace (str n) "%"
                               (str replacement)))))

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
