
(ns syntax.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn paren
  ; @param (string) n
  ;
  ; @example
  ; (paren "420")
  ; =>
  ; "(420)"
  ;
  ; @return (string)
  [n]
  (str (when n (str "(" n ")"))))

(defn bracket
  ; @param (string) n
  ;
  ; @example
  ; (bracket "420")
  ; =>
  ; "[420]"
  ;
  ; @return (string)
  [n]
  (str (when n (str "[" n "]"))))

(defn percent
  ; @param (string) n
  ;
  ; @example
  ; (percent "99.999")
  ; =>
  ; "99.999%"
  ;
  ; @return (string)
  [n]
  (str (when n (str n "%"))))

(defn quotes
  ; @param (string) n
  ;
  ; @example
  ; (quotes "420")
  ; =>
  ; "\"420\""
  ;
  ; @return (string)
  [n]
  (str (when n (str "\"" n "\""))))
