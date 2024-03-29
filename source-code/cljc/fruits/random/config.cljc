
(ns fruits.random.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @bug (#5570)
; The 'cljs.reader/read-string' function does not accept namespaced keywords where
; the first character of the name is a digit (e.g., :namespace/0abc).
; Therefore, the first character of the generated keyword names must be a letter!
;
; @consant (string)
(def NAME-PREFIX "q")
