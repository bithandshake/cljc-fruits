
(ns regex.match)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-match?
  ; @description
  ; Returns TRUE if any matches found.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-match? "123" #"^[\d]{1,}$")
  ;
  ; @example
  ; (re-match? "123" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-match? "abc" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) some?)))

(defn re-mismatch?
  ; @description
  ; Returns TRUE if no matches found.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ;
  ; @example
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-mismatch? "123" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) nil?)))
