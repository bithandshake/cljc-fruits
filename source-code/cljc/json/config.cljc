
(ns json.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (string)
; XXX#5914
; The 'keywordize-values' and 'unkeywordize-values' functions convert strings to
; keywords and vica versa in JSON maps.
; To avoid accidentaly convertings, the 'unkeywordize-values' function put a special
; prefix character to the beginning of the converted value.
(def KEYWORD-PREFIX "*")
