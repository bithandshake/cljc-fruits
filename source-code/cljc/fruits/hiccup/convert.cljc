
(ns fruits.hiccup.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (hiccup) n
  ; @param (string)(opt) delimiter
  ; Default: " "
  ;
  ; @example
  ; (to-string [:div "Hello" [:strong "World!"]])
  ; =>
  ; "Hello World!"
  ;
  ; @return (string)
  ([n]
   (to-string n " "))

  ([n delimiter]
   (letfn [(to-string-f [result x]
                        (cond (string? x) (str result delimiter x)
                              (vector? x) (str result (to-string x))
                              :return result))]
          (reduce to-string-f "" n))))
