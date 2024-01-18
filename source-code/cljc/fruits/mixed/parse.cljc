
(ns fruits.mixed.parse
    (:require [fruits.reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-number
  ; @description
  ; Parses the given number string to a number.
  ;
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (parse-number 123)
  ; =>
  ; 123
  ;
  ; @usage
  ; (parse-number 123.456)
  ; =>
  ; 123.456
  ;
  ; @usage
  ; (parse-number "123.456")
  ; =>
  ; 123.456
  ;
  ; @usage
  ; (parse-number "abc123.456")
  ; =>
  ; nil
  ;
  ; @usage
  ; (parse-number "abc123.456" {:return? true})
  ; =>
  ; "abc123.456"
  ;
  ; @return (*)
  ([n]
   (parse-number n {}))

  ([n options]
   ; @bug (fruits.mixed.convert#0550)
   (if (-> n number?)
       (-> n)
       (-> (re-seq #"^[\-]?[1-9][\d]*[\.]*[\d]*" (str n)) first reader/read-edn))))
