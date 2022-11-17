
(ns normalize.clean
    (:require #?(:cljs ["normalize-diacritics" :refer [normalize normalizeSync]])
              [clojure.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn deaccent
  ; @param (string) n
  ;
  ; @example
  ; (deaccent "aá AÁ")
  ; =>
  ; "aa AA"
  ;
  ; @return (string)
  [n]
  "Remove accent from string"
  #?(:clj  (let [normalized (java.text.Normalizer/normalize n java.text.Normalizer$Form/NFD)]
                (string/replace normalized #"\p{InCombiningDiacriticalMarks}+" ""))
     :cljs (normalizeSync n)))

(defn soft-cut-special-chars
  ; @param (string) n
  ;
  ; @example
  ; (soft-cut-special-chars "aá+-.")
  ; =>
  ; "aá"
  ;
  ; @return (string)
  [n]
  "Cut special chars, but keep accent characters"
  (string/replace n #"[^a-zA-Z0-9\u00C0-\u017F\- ]" ""))

(defn cut-special-chars
  ; @param (string) n
  ; @param (string)(opt) exceptions
  ; Default: "-"
  ;
  ; @example
  ; (cut-special-chars "abc+-.")
  ; =>
  ; "abc-"
  ;
  ; @example
  ; (cut-special-chars "abc+-.?" "+")
  ; =>
  ; "abc+-"
  ;
  ; @return (string)
  ([n]
   (cut-special-chars n "-"))

  ([n exceptions]
   "Cut special chars including accent characters"
   (let [pattern (re-pattern (str "[^\\w\\s"exceptions"]"))]
        (string/replace n pattern ""))))

(defn replace-white-chars
  ; @param (string) n
  ;
  ; @example
  ; (replace-white-chars "a b  c")
  ; =>
  ; "a-b-c"
  ;
  ; @return (string)
  [n]
  "Replace ' ' to '-'. Keep one '-' if more than one are next to each other."
  (string/replace n #"[ |-]{1,}" "-"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn clean-text
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
  ; (clean-text "1+2" "+")
  ; =>
  ; "1+2"
  ;
  ; @return (string)
  ([n]
   (clean-text n "-"))

  ([n exceptions]
   (-> n (str)
         (deaccent)
         (cut-special-chars exceptions)
         (replace-white-chars)
         (string/lower-case))))
