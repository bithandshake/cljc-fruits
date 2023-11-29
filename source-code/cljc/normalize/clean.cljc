
(ns normalize.clean
    (:require #?(:cljs ["normalize-diacritics" :refer [normalizeSync]])
              [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deaccent
  ; @description
  ; Removes diacritical marks (accents) from the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (deaccent "aáAÁ")
  ;
  ; @example
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
  ; Removes special characters from the given 'n' string including accented characters.
  ;
  ; @param (string) n
  ; @param (string)(opt) exceptions
  ; Default: "-"
  ;
  ; @usage
  ; (remove-special-chars "abc+-.")
  ;
  ; @example
  ; (remove-special-chars "abc+-.")
  ; =>
  ; "abc-"
  ;
  ; @example
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
  ; Replaces blank characters / blank character groups in the given 'n' string with single hyphens.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (replace-blank-chars "a b  \n  c")
  ;
  ; @example
  ; (replace-blank-chars "a b  \n  c")
  ; =>
  ; "a-b-c"
  ;
  ; @return (string)
  [n]
  (clojure.string/replace n #"[ |-]{1,}" "-"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn clean-text
  ; @description
  ; Normalizes the given 'n' string by removing diacritical marks, removing special characters,
  ; and blank characters, and converts the string to lowercase.
  ;
  ; @param (*) n
  ; @param (string)(opt) exceptions
  ; Default: "-"
  ;
  ; @usage
  ; (clean-text "a b  c")
  ;
  ; @example
  ; (clean-text "a b  c")
  ; =>
  ; "a-b-c"
  ;
  ; @example
  ; (clean-text "aá AÁ")
  ; =>
  ; "aa-AA"
  ;
  ; @example
  ; (clean-text "1+2")
  ; =>
  ; "12"
  ;
  ; @example
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
