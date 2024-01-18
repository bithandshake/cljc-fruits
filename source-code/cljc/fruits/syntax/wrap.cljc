
(ns fruits.syntax.wrap)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn paren
  ; @description
  ; Wraps the given 'n' value with a parenthesis pair.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (paren "420")
  ; =>
  ; "(420)"
  ;
  ; @return (string)
  [n]
  (str (when n (str "("n")"))))

(defn brace
  ; @description
  ; Wraps the given 'n' value with a brace pair.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (brace ":x 420")
  ; =>
  ; "{:x 420}"
  ;
  ; @return (string)
  [n]
  (str (when n (str "{"n"}"))))

(defn bracket
  ; @description
  ; Wraps the given 'n' value with a bracket pair.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (bracket "420")
  ; =>
  ; "[420]"
  ;
  ; @return (string)
  [n]
  (str (when n (str "["n"]"))))

(defn percent
  ; @description
  ; Appends a percentage symbol to the given 'n' value.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (percent "99.999")
  ; =>
  ; "99.999%"
  ;
  ; @return (string)
  [n]
  (str (when n (str n"%"))))

(defn quotes
  ; @description
  ; Wraps the given 'n' value with a quote pair.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (quotes "420")
  ; =>
  ; "\"420\""
  ;
  ; @return (string)
  [n]
  (str (when n (str "\""n"\""))))
