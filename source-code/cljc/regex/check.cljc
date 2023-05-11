
(ns regex.check)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-match?
  ; @description
  ; Returns true if any matches found.
  ;
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @usage
  ; (re-match? "123" #"\d{1,}")
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
  [n pattern]
  (let [n (str n)]
       (some? (re-find pattern n))))

(defn re-mismatch?
  ; @description
  ; Returns true if no matches found.
  ;
  ; @usage
  ; (re-mismatch? "123" #"\d{1,}")
  ;
  ; @param (*) n
  ; @param (regex pattern) pattern
  ;
  ; @example
  ; (re-mismatch? "123" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @example
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n pattern]
  (let [n (str n)]
       (nil? (re-find pattern n))))
