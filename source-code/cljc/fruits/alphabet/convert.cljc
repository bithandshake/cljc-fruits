
(ns fruits.alphabet.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn integer->lowercase
  ; @description
  ; Returns the corresponding letter (in lowercase form) for the given 'n' integer from the English alphabet.
  ;
  ; @param (integer) n
  ;
  ; @usage
  ; (integer->lowercase 3)
  ; =>
  ; "c"
  ;
  ; @usage
  ; (integer->lowercase 26)
  ; =>
  ; "z"
  ;
  ; @usage
  ; (integer->lowercase 27)
  ; =>
  ; "aa"
  ;
  ; @usage
  ; (integer->lowercase 702)
  ; =>
  ; "zz"
  ;
  ; @usage
  ; (integer->lowercase 703)
  ; =>
  ; "aaa"
  ;
  ; @return (string)
  [n]
  (let [base-char (int \a)]
       (loop [num n result ""]
             (if (-> num zero?)
                 (-> result)
                 (let [char (char (+ base-char (mod (dec num) 26)))]
                      (recur (quot (dec num) 26) (str char result)))))))

(defn integer->uppercase
  ; @description
  ; Returns the corresponding letter (in uppercase form) for the given 'n' integer from the English alphabet.
  ;
  ; @param (integer) n
  ;
  ; @usage
  ; (integer->uppercase 3)
  ; =>
  ; "C"
  ;
  ; @usage
  ; (integer->uppercase 26)
  ; =>
  ; "Z"
  ;
  ; @usage
  ; (integer->uppercase 27)
  ; =>
  ; "AA"
  ;
  ; @usage
  ; (integer->uppercase 702)
  ; =>
  ; "ZZ"
  ;
  ; @usage
  ; (integer->uppercase 703)
  ; =>
  ; "AAA"
  ;
  ; @return (string)
  [n]
  (let [base-char (int \A)]
       (loop [num n result ""]
             (if (-> num zero?)
                 (-> result)
                 (let [char (char (+ base-char (mod (dec num) 26)))]
                      (recur (quot (dec num) 26) (str char result)))))))
