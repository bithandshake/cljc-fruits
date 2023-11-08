
(ns alphabet.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn integer->lowercase
  ; @description
  ; Returns the corresponging letter (in lowercase form) for the given 'n' integer in the English alphabet.
  ;
  ; @param (integer) n
  ;
  ; @usage
  ; (integer->lowercase 3)
  ;
  ; @example
  ; (integer->lowercase 3)
  ; =>
  ; "c"
  ;
  ; @example
  ; (integer->lowercase 26)
  ; =>
  ; "z"
  ;
  ; @example
  ; (integer->lowercase 27)
  ; =>
  ; "aa"
  ;
  ; @example
  ; (integer->lowercase 702)
  ; =>
  ; "zz"
  ;
  ; @example
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
  ; @param (integer) n
  ;
  ; @usage
  ; (integer->uppercase 3)
  ;
  ; @example
  ; (integer->uppercase 3)
  ; =>
  ; "C"
  ;
  ; @example
  ; (integer->uppercase 26)
  ; =>
  ; "Z"
  ;
  ; @example
  ; (integer->uppercase 27)
  ; =>
  ; "AA"
  ;
  ; @example
  ; (integer->uppercase 702)
  ; =>
  ; "ZZ"
  ;
  ; @example
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
