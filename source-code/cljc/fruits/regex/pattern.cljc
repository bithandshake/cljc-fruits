
(ns fruits.regex.pattern)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join-pattern
  ; @param (list of strings) xyz
  ;
  ; @example
  ; (join-pattern "(?<=abc)" "def")
  ; =>
  ; #"(?<=abc)def"
  ;
  ; @return (regex pattern)
  [& xyz]
  (-> (apply str xyz) re-pattern))
