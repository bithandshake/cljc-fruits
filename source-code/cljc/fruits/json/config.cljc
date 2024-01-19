
(ns fruits.json.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @constant (string)
;
; @note (#5914)
; - The 'keywordize-value' and 'unkeywordize-value' functions convert strings to
;   keywords and vica versa, in JSON maps.
; - To avoid accidental convertings, the 'unkeywordize-value' function put a special
;   prefix character to the beginning of every converted value. This helps the reverse
;   converting functions determine whether a string value was originally a keyword that
;   converted to a string or it was a string originally.
; - Without this prefix character, keywords that converted to a string would look like this:
;   ":i-was-a-keyword-before-i-get-converted-to-a-string"
; - If a string starts with the character ":" that doesn't mean it was a keyword.
;   For usage, here is a password that starts with ":" and it was never a keyword:
;   ":?-pw123__"
; - So that's why we should mark all keywords that converted to a string with a prefix character.
(def KEYWORD-PREFIX "*")
