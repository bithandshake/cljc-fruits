
(ns fruits.string.insert
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prefix
  ; @description
  ; Prepends the given 'x' string to the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (string)(opt) separator
  ;
  ; @usage
  ; (prefix "420" "$")
  ; =>
  ; "$420"
  ;
  ; @usage
  ; (prefix 420 "$")
  ; =>
  ; "$420"
  ;
  ; @usage
  ; (prefix "" "$")
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (prefix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (->  n empty?)
            (->  n)
            (str x separator n)))))

(defn suffix
  ; @description
  ; Appends the given 'x' string to the given 'n' string.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (string)(opt) separator
  ;
  ; @usage
  ; (suffix "420" "px")
  ; =>
  ; "420px"
  ;
  ; @usage
  ; (suffix 420 "px")
  ; =>
  ; "420px"
  ;
  ; @usage
  ; (suffix "" "px")
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (suffix n x nil))

  ([n x separator]
   (let [n (str n)]
        (if (->  n empty?)
            (->  n)
            (str n separator x)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prepend
  ; @description
  ; Prepends the rest parameters to the given 'n' string.
  ;
  ; @param (string) n
  ; @param (list of string) xyz
  ;
  ; @usage
  ; (prepend "my-domain.com" "https://")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @return (string)
  [n & xyz]
  (letfn [(f0 [result x] (str result x))]
         (str (reduce f0 nil (vec xyz)) n)))

(defn append
  ; @description
  ; Appends the rest parameters to the given 'n' string.
  ;
  ; @param (string) n
  ; @param (list of string) xyz
  ;
  ; @usage
  ; (append "https://" "my-domain.com")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @return (string)
  [n & xyz]
  (letfn [(f0 [result x] (str result x))]
         (reduce f0 n (vec xyz))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn insert-part
  ; @description
  ; Inserts the given 'x' string into the given 'n' string at a specific cursor position.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (insert-part "abcd" "xx" 2)
  ; =>
  ; "abxxcd"
  ;
  ; @return (string)
  [n x cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor {:adjust? true :mirror? true})]
       (str (subs n 0 cursor) x
            (subs n   cursor))))
