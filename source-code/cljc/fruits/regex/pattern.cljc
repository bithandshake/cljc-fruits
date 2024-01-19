
(ns fruits.regex.pattern)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn join-pattern
  ; @param (list of regex patterns and/or strings) xyz
  ;
  ; @usage
  ; (join-pattern "(?<=abc)" "def")
  ; =>
  ; #"(?<=abc)def"
  ;
  ; @return (regex pattern)
  [& xyz]
  (-> (apply str xyz) re-pattern))
