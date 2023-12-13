
(ns fruits.string.insert
    (:require [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prefix
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (prefix "420" "$")
  ;
  ; @example
  ; (prefix "420" "$")
  ; =>
  ; "$420"
  ;
  ; @example
  ; (prefix 420 "$")
  ; =>
  ; "$420"
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (*)(opt) separator
  ;
  ; @usage
  ; (suffix "420" "px")
  ;
  ; @example
  ; (suffix "420" "px")
  ; =>
  ; "420px"
  ;
  ; @example
  ; (suffix 420 "px")
  ; =>
  ; "420px"
  ;
  ; @example
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
  ; @param (*) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (prepend "my-domain.com" "https://")
  ;
  ; @example
  ; (prepend "my-domain.com" "https://")
  ; =>
  ; "https://my-domain.com"
  ;
  ; @return (string)
  [n & xyz]
  (letfn [(f0 [result x] (str result x))]
         (str (reduce f0 nil (vec xyz)) n)))

(defn append
  ; @param (*) n
  ; @param (list of *) xyz
  ;
  ; @usage
  ; (append "https://" "my-domain.com")
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (integer) cursor
  ;
  ; @usage
  ; (insert-part "abcd" "xx" 2)
  ;
  ; @example
  ; (insert-part "abcd" "xx" 2)
  ; =>
  ; "abxxcd"
  ;
  ; @return (string)
  [n x cursor]
  (let [n      (str n)
        cursor (seqable/normalize-cursor n cursor)]
       (str (subs n 0 cursor) x
            (subs n   cursor))))
