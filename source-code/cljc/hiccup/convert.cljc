
(ns hiccup.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (hiccup) n
  ; @param (string)(opt) delimiter
  ;  Default: " "
  ;
  ; @example
  ;  (to-string [:div "Hello" [:strong "World!"]])
  ;  =>
  ;  "Hello World!"
  ;
  ; @return (string)
  ([n]
   (to-string n " "))

  ([n delimiter]
   (letfn [(to-string-f [o x]
                        (cond (string? x) (str o delimiter  x)
                              (vector? x) (str o (to-string x))
                              :return  o))]
          (reduce to-string-f "" n))))
