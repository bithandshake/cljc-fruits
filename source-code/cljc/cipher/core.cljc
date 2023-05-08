
(ns cipher.core
    (:require [format.api]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn encrypt
  ; @param (*) n
  ; @param (*) key
  ;
  ; @description
  ; https://en.wikipedia.org/wiki/Cipher
  ;
  ; @usage
  ; (encrypt "Hello world!" "password")
  ;
  ; @example
  ; (encrypt "Hello world!" "password")
  ; =>
  ; "¸ÆßßæéÓâÍ×"
  ;
  ; @return (string)
  [n key]
  ; XXX#4101
  ; The ASCII (7bit) character table contains 128 characters, therefore if the
  ; shifted character is not in the range (0 - 127) subtracting 128 puts it back
  ; to the range and of course the decrypt method does the same but reverse (adding 128).
  ; 112 + 28 > 140 (out of range)
  ;            140 - 128 > 12
  (let [key-length (count key)
        key-codes  (vec (map int key))
        n-codes    (vec (map int n))]
       (letfn [(f [result n-cursor n-code]
                  (let [key-cursor   (- n-cursor (* key-length (quot n-cursor key-length)))
                        key-code     (nth key-codes key-cursor)
                        shifted-code (+ n-code key-code)
                        shifted-code (if (> shifted-code 127) (- shifted-code 128) shifted-code)]
                       (str result (char shifted-code))))]
              (reduce-kv f "" n-codes))))

(defn decrypt
  ; @param (*) n
  ; @param (*) key
  ;
  ; @description
  ; https://en.wikipedia.org/wiki/Cipher
  ;
  ; @usage
  ; (decrypt "¸ÆßßæéÓâÍ×" "password")
  ;
  ; @example
  ; (decrypt "¸ÆßßæéÓâÍ×" "password")
  ; =>
  ; "Hello world!"
  ;
  ; @return (string)
  [n key]
  ; XXX#4101
  (let [key-length (count key)
        key-codes  (vec (map int key))
        n-codes    (vec (map int n))]
       (letfn [(f [result n-cursor n-code]
                  (let [key-cursor   (- n-cursor (* key-length (quot n-cursor key-length)))
                        key-code     (nth key-codes key-cursor)
                        shifted-code (- n-code key-code)
                        shifted-code (if (< shifted-code 0) (+ 128 shifted-code) shifted-code)]
                       (str result (char shifted-code))))]
              (reduce-kv f "" n-codes))))
