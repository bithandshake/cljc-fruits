
(ns syntax.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn paren
  ; @description
  ; Wraps the given 'n' string with a parenthesis pair.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (paren "420")
  ; =>
  ; "(420)"
  ;
  ; @return (string)
  [n]
  (str (when n (str "("n")"))))

(defn brace
  ; @description
  ; Wraps the given 'n' string with a brace pair.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (brace ":x 420")
  ; =>
  ; "{:x 420}"
  ;
  ; @return (string)
  [n]
  (str (when n (str "{"n"}"))))

(defn bracket
  ; @description
  ; Wraps the given 'n' string with a bracket pair.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (bracket "420")
  ; =>
  ; "[420]"
  ;
  ; @return (string)
  [n]
  (str (when n (str "["n"]"))))

(defn percent
  ; @description
  ; Appends a percentage symbol to the given 'n' string.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (percent "99.999")
  ; =>
  ; "99.999%"
  ;
  ; @return (string)
  [n]
  (str (when n (str n"%"))))

(defn quotes
  ; @description
  ; Wraps the given 'n' string with a quote pair.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (quotes "420")
  ; =>
  ; "\"420\""
  ;
  ; @return (string)
  [n]
  (str (when n (str "\""n"\""))))
