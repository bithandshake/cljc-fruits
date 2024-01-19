
(ns fruits.regex.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pattern?
  ; @description
  ; Returns TRUE if the given 'n' value is a regex pattern.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (pattern? #"[a-z]")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (= (-> n     type)
     (-> #"\d" type)))
