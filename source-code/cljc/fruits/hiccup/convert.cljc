
(ns fruits.hiccup.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-string
  ; @param (hiccup) n
  ; @param (string)(opt) delimiter
  ; Default: " "
  ;
  ; @usage
  ; (to-string [:div "Hello" [:strong "World!"]])
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
   (if (vector? n)
       (letfn [(f0 [result x]
                   (cond (string? x) (str result delimiter x)
                         (vector? x) (str result (to-string x))
                         :return result))]
              (reduce f0 "" n)))))
