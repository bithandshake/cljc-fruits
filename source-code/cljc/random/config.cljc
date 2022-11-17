
(ns random.config)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; BUG#5570
; A cljs.reader/read-string függvény nem szereti azokat a névteres kulcsszavakat,
; amelyekben a név első karaktere egy számjegy (pl. :namespace/0abc).
; Ezért a generált kulcsszavak nevének első karaktere egy betű kell legyen!
;
; @consant (string)
(def NAME-PREFIX "q")
