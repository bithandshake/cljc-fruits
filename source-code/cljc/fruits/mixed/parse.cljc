
(ns fruits.mixed.parse
    (:require [fruits.regex.api  :as regex]
              [fruits.reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-number
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (parse-number 123)
  ; =>
  ; 123
  ;
  ; @example
  ; (parse-number 123.456)
  ; =>
  ; 123.456
  ;
  ; @example
  ; (parse-number "123.456")
  ; =>
  ; 123.456
  ;
  ; @example
  ; (parse-number "abc123.456")
  ; =>
  ; nil
  ;
  ; @example
  ; (parse-number "abc123.456" {:return? true})
  ; =>
  ; "abc123.456"
  ;
  ; @return (*)
  ([n]
   (parse-number n {}))

  ([n options]
   (if (-> n number?)
       (-> n)
       (-> n (regex/re-first #"^\-*\d+\.*\d*$")
             (reader/read-edn)))))
