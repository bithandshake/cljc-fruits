
(ns css.candy
    (:require [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn calc
  ; @param (string) n
  ;
  ; @example
  ;  (calc "100% - 100px")
  ;  =>
  ;  "calc(100% - 100px)"
  ;
  ; @return (string)
  [n]
  (str "calc(" n ")"))

(defn ms
  ; @param (ms) n
  ;
  ; @example
  ;  (ms 500)
  ;  =>
  ;  "500ms"
  ;
  ; @return (string)
  [n]
  (str n "ms"))

(defn s
  ; @param (s) n
  ;
  ; @example
  ;  (s 3)
  ;  =>
  ;  "3s"
  ;
  ; @return (string)
  [n]
  (str n "s"))

(defn percent
  ; @param (percent) n
  ;
  ; @example
  ;  (percent 100)
  ;  =>
  ;  "100%"
  ;
  ; @return (string)
  [n]
  (str n "%"))

(defn px
  ; @param (string) n
  ;
  ; @example
  ;  (px "100")
  ;  =>
  ;  "100px"
  ;
  ; @return (string)
  [n]
  (str n "px"))

(defn rotate
  ; @param (string) n
  ;
  ; @example
  ;  (rotate "120")
  ;  =>
  ;  "rotate(120deg)"
  ;
  ; @return (string)
  [n]
  (str "rotate(" n "deg)"))

(defn rotate-x
  ; @param (string) n
  ;
  ; @example
  ;  (rotate-x "120")
  ;  =>
  ;  "rotateX(120deg)"
  ;
  ; @return (string)
  [n]
  (str "rotateX(" n "deg)"))

(defn rotate-y
  ; @param (string) n
  ;
  ; @example
  ;  (rotate-y "120")
  ;  =>
  ;  "rotateY(120deg)"
  ;
  ; @return (string)
  [n]
  (str "rotateY(" n "deg)"))

(defn rotate-z
  ; @param (string) n
  ;
  ; @example
  ;  (rotate-z "120")
  ;  =>
  ;  "rotateZ(120deg)"
  ;
  ; @return (string)
  [n]
  (str "rotateZ(" n "deg)"))

(defn scale
  ; @param (string) n
  ;
  ; @example
  ;  (scale "1.1")
  ;  =>
  ;  "scale(1.1)"
  ;
  ; @return (string)
  [n]
  (str "scale(" n ")"))

(defn translate
  ; @param (string) n
  ; @param (string)(opt) suffix
  ;
  ; @example
  ;  (translate "120" "px")
  ;  =>
  ;  "translate(120px)"
  ;
  ; @example
  ;  (translate "120px")
  ;  =>
  ;  "translate(120px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (str "translate(" n suffix ")"))

(defn translate-x
  ; @param (string) n
  ; @param (string)(opt) suffix
  ;
  ; @example
  ;  (translate-x "120" "px")
  ;  =>
  ;  "translateX(120px)"
  ;
  ; @example
  ;  (translate-x "120px")
  ;  =>
  ;  "translateX(120px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (str "translateX(" n suffix ")"))

(defn translate-y
  ; @param (string) n
  ; @param (string)(opt) suffix
  ;
  ; @example
  ;  (translate-y "120" "px")
  ;  =>
  ;  "translateY(120px)"
  ;
  ; @example
  ;  (translate-y "120px")
  ;  =>
  ;  "translateY(120px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (str "translateY(" n suffix ")"))

(defn translate-z
  ; @param (string) n
  ; @param (string)(opt) suffix
  ;
  ; @example
  ;  (translate-z "120" "px")
  ;  =>
  ;  "translateZ(120px)"
  ;
  ; @example
  ;  (translate-z "120px")
  ;  =>
  ;  "translateZ(120px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (str "translateZ(" n suffix ")"))

(defn url
  ; @param (string) n
  ;
  ; @example
  ;  (url "/my-file.ext")
  ;  =>
  ;  "url(/my-file.ext)"
  ;
  ; @return (string)
  [n]
  (str "url(" n ")"))

(defn value
  ; @param (string or integer) n
  ; @param (string) unit
  ;  "%", "px", "rem", ...
  ;
  ; @example
  ;  (value 180 "%")
  ;  =>
  ;  "180%"
  ;
  ; @return (string)
  [n unit]
  (str n unit))

(defn var
  ; @param (string) n
  ;
  ; @example
  ;  (var "my-var")
  ;  =>
  ;  "var( --my-var )"
  ;
  ; @return (string)
  [n]
  (str "var( --" n " )"))

(defn horizontal-padding
  ; @param (string) n
  ;
  ; @example
  ;  (horizontal-padding "12px")
  ;  =>
  ;  "12px 0"
  ;
  ; @return (string)
  [n]
  (str n " 0"))

(defn vertical-padding
  ; @param (string) n
  ;
  ; @example
  ;  (vertical-padding "12px")
  ;  =>
  ;  "0 12px"
  ;
  ; @return (string)
  [n]
  (str "0 " n))

(defn horizontal-margin
  ; @param (string) n
  ;
  ; @example
  ;  (horizontal-margin "12px")
  ;  =>
  ;  "12px 0"
  ;
  ; @return (string)
  [n]
  (str n " 0"))

(defn vertical-margin
  ; @param (string) n
  ;
  ; @example
  ;  (vertical-margin "12px")
  ;  =>
  ;  "0 12px"
  ;
  ; @return (string)
  [n]
  (str "0 " n))

(defn linear-gradient
  ; @param (string) direction
  ; @param (list of strings) color-stops
  ;
  ; @example
  ;  (linear-gradient "0deg" "red" "green" "blue")
  ;  =>
  ;  "linear-gradient(0deg, red, green, blue)"
  ;
  ; @return (string)
  [direction & color-stops]
  (let [color-stops (string/join color-stops ", ")]
       (str "linear-gradient("direction", "color-stops")")))
