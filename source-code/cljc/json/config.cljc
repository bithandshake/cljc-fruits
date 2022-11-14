
(ns json.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (string)
; XXX#5914
(def KEYWORD-PREFIX "*")
; Az unkeywordize-values függvény string típussá alakítja az átadott adatszerkezetben
; található kulcsszó típusú értékeket.
; A téves át- és visszaalakítások elkerülése érdekében biztonsági prefixummal
; látja el a string típussá alakított értékeket.
