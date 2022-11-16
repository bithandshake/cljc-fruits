
(ns mixed.parse
    (:require [candy.api  :refer [return]]
              [mixed.type :as type]
              [reader.api :as reader]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-rational-number
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (parse-rational-number 12)
  ; =>
  ; 12
  ;
  ; @example
  ; (parse-rational-number 12.1)
  ; =>
  ; 12.1
  ;
  ; @example
  ; (parse-rational-number "12.1")
  ; =>
  ; 12.1
  ;
  ; @example
  ; (parse-rational-number "abCd12.1")
  ; =>
  ; nil
  ;
  ; @example
  ; (parse-rational-number "abCd12.1" {:return? true})
  ; =>
  ; "abCd12.1"
  ;
  ; @return (*)
  ([n]
   (parse-rational-number n {}))

  ([n {:keys [return?]}]
   (cond (number?               n) (return               n)
         (type/rational-number? n) (reader/string->mixed n)
         return?                   (return               n))))

(defn parse-number
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (parse-number 12)
  ; =>
  ; 12
  ;
  ; @example
  ; (parse-number 12.1)
  ; =>
  ; 12.1
  ;
  ; @example
  ; (parse-number "12.1")
  ; =>
  ; 12.1
  ;
  ; @example
  ; (parse-number "abCd12.1")
  ; =>
  ; nil
  ;
  ; @example
  ; (parse-number "abCd12.1" {:return? tru})
  ; =>
  ; "abCd12.1"
  ;
  ; @return (*)
  ([n]
   (parse-number n {}))

  ([n options]
   (parse-rational-number n options)))

(defn parse-whole-number
  ; @param (*) n
  ; @param (map)(opt) options
  ; {:return? (boolean)(opt)
  ;   Default: false}
  ;
  ; @example
  ; (parse-whole-number 12)
  ; =>
  ; 12
  ;
  ; @example
  ; (parse-whole-number "12")
  ; =>
  ; 12
  ;
  ; @example
  ; (parse-whole-number "abCd12")
  ; =>
  ; nil
  ;
  ; @example
  ; (parse-whole-number "abCd12" {:return? true})
  ; =>
  ; "abCd12"
  ;
  ; @return (*)
  ([n]
   (parse-whole-number n {}))

  ([n {:keys [return?]}]
   (cond (integer?           n) (return               n)
         (type/whole-number? n) (reader/string->mixed n)
         return?                (return               n))))
