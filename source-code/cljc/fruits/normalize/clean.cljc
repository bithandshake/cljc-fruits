
(ns fruits.normalize.clean
    #?(:clj  (:require [clojure.string])
       :cljs (:require [clojure.string]
                       ["normalize-diacritics" :refer [normalizeSync]])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deaccent
  ; @description
  ; Removes the diacritical marks (accents) from the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (deaccent "aáAÁ")
  ; =>
  ; "aaAA"
  ;
  ; @return (string)
  [n]
  #?(:clj  (-> n (java.text.Normalizer/normalize java.text.Normalizer$Form/NFD)
                 (clojure.string/replace #"\p{InCombiningDiacriticalMarks}+" ""))
     :cljs (-> n (normalizeSync))))

(defn remove-special-chars
  ; @description
  ; Removes the special characters from the given 'n' string including accented characters.
  ;
  ; @param (string) n
  ; @param (string)(opt) exceptions
  ; Default: "-"
  ;
  ; @usage
  ; (remove-special-chars "abc+-.")
  ; =>
  ; "abc-"
  ;
  ; @usage
  ; (remove-special-chars "abc+-.?" "-+")
  ; =>
  ; "abc+-"
  ;
  ; @return (string)
  ([n]
   (remove-special-chars n "-"))

  ([n exceptions]
   (let [pattern (re-pattern (str "[^\\w\\s"exceptions"]"))]
        (clojure.string/replace n pattern ""))))

(defn replace-blank-chars
  ; @description
  ; Replaces the blank characters / blank character groups in the given 'n' string with single hyphens.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (replace-blank-chars "a b  \n  c")
  ; =>
  ; "a-b-c"
  ;
  ; @return (string)
  [n]
  (clojure.string/replace n #"[ |-]+" "-"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn clean-text
  ; @description
  ; Normalizes the given 'n' string by removing the diacritical marks, removing the special characters,
  ; and blank characters, and converting the result to lowercase.
  ;
  ; @param (*) n
  ; @param (string)(opt) exceptions
  ; Default: "-"
  ;
  ; @usage
  ; (clean-text "a b  c")
  ; =>
  ; "a-b-c"
  ;
  ; @usage
  ; (clean-text "aá AÁ")
  ; =>
  ; "aa-AA"
  ;
  ; @usage
  ; (clean-text "1+2")
  ; =>
  ; "12"
  ;
  ; @usage
  ; (clean-text "1+2-4" "+-")
  ; =>
  ; "1+2-4"
  ;
  ; @return (string)
  ([n]
   (clean-text n "-"))

  ([n exceptions]
   (-> n (str)
         (deaccent)
         (remove-special-chars exceptions)
         (replace-blank-chars)
         (clojure.string/lower-case))))
