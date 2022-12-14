
(ns string.lines
    (:require [clojure.string]
              [candy.api   :refer [return]]
              [string.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-newlines
  ; @param (*) n
  ;
  ; @usage
  ; (remove-newlines "abc\r\n")
  ;
  ; @example
  ; (remove-newlines "abc\r\n")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (clojure.string/replace (str n) \newline ""))

(defn line-count
  ; @param (*) n
  ;
  ; @usage
  ; (line-count "abc\n")
  ;
  ; @example
  ; (line-count "abc\n")
  ; =>
  ; 1
  ;
  ; @example
  ; (line-count "abc")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n]
  (get (-> n str frequencies) \newline))

(defn max-lines
  ; @param (*) n
  ; @param (integer) limit
  ; @param (map)(opt) options
  ; {:reverse? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (max-lines "abc\ndef" 1)
  ;
  ; @example
  ; (max-lines "abc\ndef\nghi" 2)
  ; =>
  ; "abc\ndef"
  ;
  ; @example
  ; (max-lines "abc\ndef\nghi" 2 {:reverse? true})
  ; =>
  ; "def\nghi"
  ;
  ; @return (string)
  ([n limit]
   (max-lines n limit {}))

  ([n limit {:keys [reverse?]}]
   (let [n     (str        n)
         lines (core/split n #"\n")
         count (count      lines)
         limit (min        limit count)
         lines (if reverse? (subvec lines (- count limit) count)
                            (subvec lines 0 limit))]
        (letfn [(f [result dex]
                   (if (= dex limit)
                       (return result)
                       (f (str result (if (not= dex 0) "\n") (nth lines dex))
                          (inc dex))))]
               (f "" 0)))))
