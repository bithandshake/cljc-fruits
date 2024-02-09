
(ns fruits.css.candy
    (:refer-clojure :exclude [repeat])
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn calc
  ; @param (string) n
  ;
  ; @usage
  ; (calc "100% - 100px")
  ; =>
  ; "calc(100% - 100px)"
  ;
  ; @return (string)
  [n]
  (if n (str "calc(" n ")")))

(defn fr
  ; @param (ms) n
  ;
  ; @usage
  ; (fr 420)
  ; =>
  ; "420fr"
  ;
  ; @return (string)
  [n]
  (if n (str n "fr")))

(defn ms
  ; @param (ms) n
  ;
  ; @usage
  ; (ms 420)
  ; =>
  ; "420ms"
  ;
  ; @return (string)
  [n]
  (if n (str n "ms")))

(defn s
  ; @param (s) n
  ;
  ; @usage
  ; (s 420)
  ; =>
  ; "420s"
  ;
  ; @return (string)
  [n]
  (if n (str n "s")))

(defn percent
  ; @param (percentage) n
  ;
  ; @usage
  ; (percent 420)
  ; =>
  ; "420%"
  ;
  ; @return (string)
  [n]
  (if n (str n "%")))

(defn px
  ; @param (px) n
  ;
  ; @usage
  ; (px "420")
  ; =>
  ; "420px"
  ;
  ; @return (string)
  [n]
  (if n (str n "px")))

(defn repeat
  ; @param (integer) count
  ; @param (string) n
  ;
  ; @usage
  ; (repeat 3 "1fr")
  ; =>
  ; "repeat(3, 1fr)"
  ;
  ; @return (string)
  [count n]
  (if n (str "repeat("count", "n")")))

(defn rotate
  ; @param (deg) n
  ;
  ; @usage
  ; (rotate 420)
  ; =>
  ; "rotate(420deg)"
  ;
  ; @return (string)
  [n]
  (if n (str "rotate(" n "deg)")))

(defn rotate-x
  ; @param (deg) n
  ;
  ; @usage
  ; (rotate-x 420)
  ; =>
  ; "rotateX(420deg)"
  ;
  ; @return (string)
  [n]
  (if n (str "rotateX(" n "deg)")))

(defn rotate-y
  ; @param (deg) n
  ;
  ; @usage
  ; (rotate-y 420)
  ; =>
  ; "rotateY(420deg)"
  ;
  ; @return (string)
  [n]
  (if n (str "rotateY(" n "deg)")))

(defn rotate-z
  ; @param (deg) n
  ;
  ; @usage
  ; (rotate-z 420)
  ; =>
  ; "rotateZ(420deg)"
  ;
  ; @return (string)
  [n]
  (if n (str "rotateZ(" n "deg)")))

(defn scale
  ; @param (number or string) n
  ;
  ; @usage
  ; (scale 1.1)
  ; =>
  ; "scale(1.1)"
  ;
  ; @return (string)
  [n]
  (if n (str "scale(" n ")")))

(defn translate
  ; @param (number or string) n
  ; @param (string)(opt) suffix
  ;
  ; @usage
  ; (translate 420 "px")
  ; =>
  ; "translate(420px)"
  ;
  ; @usage
  ; (translate "420px")
  ; =>
  ; "translate(420px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (if n (str "translate(" n suffix ")")))

(defn translate-x
  ; @param (number or string) n
  ; @param (string)(opt) suffix
  ;
  ; @usage
  ; (translate-x 420 "px")
  ; =>
  ; "translateX(420px)"
  ;
  ; @usage
  ; (translate-x "420px")
  ; =>
  ; "translateX(120px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (if n (str "translateX(" n suffix ")")))

(defn translate-y
  ; @param (number or string) n
  ; @param (string)(opt) suffix
  ;
  ; @usage
  ; (translate-y 420 "px")
  ; =>
  ; "translateY(420px)"
  ;
  ; @usage
  ; (translate-y "420px")
  ; =>
  ; "translateY(420px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (if n (str "translateY(" n suffix ")")))

(defn translate-z
  ; @param (number or string) n
  ; @param (string)(opt) suffix
  ;
  ; @usage
  ; (translate-z 420 "px")
  ; =>
  ; "translateZ(420px)"
  ;
  ; @usage
  ; (translate-z "420px")
  ; =>
  ; "translateZ(420px)"
  ;
  ; @return (string)
  [n & [suffix]]
  (if n (str "translateZ(" n suffix ")")))

(defn url
  ; @param (string) n
  ;
  ; @usage
  ; (url "/my-style.ext")
  ; =>
  ; "url(/my-style.ext)"
  ;
  ; @return (string)
  [n]
  (if n (str "url(" n ")")))

(defn value
  ; @param (number or string) n
  ; @param (string) unit
  ; "%", "px", "rem", ...
  ;
  ; @usage
  ; (value 420 "px")
  ; =>
  ; "180px"
  ;
  ; @return (string)
  [n unit]
  (if n (str n unit)))

(defn var
  ; @param (keyword or string) n
  ;
  ; @usage
  ; (var "my-var")
  ; =>
  ; "var( --my-var )"
  ;
  ; @usage
  ; (var :my-var)
  ; =>
  ; "var( --my-var )"
  ;
  ; @return (string)
  [n]
  (cond (string?  n) (str "var( --" n        " )")
        (keyword? n) (str "var( --" (name n) " )")))

(defn var-key
  ; @param (keyword or string) n
  ;
  ; @usage
  ; (var-key "my-key")
  ; =>
  ; "--my-key"
  ;
  ; @usage
  ; (var-key :my-key)
  ; =>
  ; "--my-key"
  ;
  ; @return (string)
  [n]
  (cond (string?  n) (str "--"       n)
        (keyword? n) (str "--" (name n))))

(defn horizontal-padding
  ; @param (string) n
  ;
  ; @usage
  ; (horizontal-padding "420px")
  ; =>
  ; "420px 0"
  ;
  ; @return (string)
  [n]
  (if n (str n " 0")))

(defn vertical-padding
  ; @param (string) n
  ;
  ; @usage
  ; (vertical-padding "420px")
  ; =>
  ; "0 420px"
  ;
  ; @return (string)
  [n]
  (if n (str "0 " n)))

(defn horizontal-margin
  ; @param (string) n
  ;
  ; @usage
  ; (horizontal-margin "420px")
  ; =>
  ; "420px 0"
  ;
  ; @return (string)
  [n]
  (if n (str n " 0")))

(defn vertical-margin
  ; @param (string) n
  ;
  ; @usage
  ; (vertical-margin "420px")
  ; =>
  ; "0 420px"
  ;
  ; @return (string)
  [n]
  (if n (str "0 " n)))

(defn linear-gradient
  ; @param (string) direction
  ; @param (list of strings) color-stops
  ;
  ; @usage
  ; (linear-gradient "0deg" "red" "green" "blue")
  ; =>
  ; "linear-gradient(0deg, red, green, blue)"
  ;
  ; @return (string)
  [direction & color-stops]
  (if (-> color-stops empty? not)
      (let [color-stops (string/join color-stops ", ")]
           (str "linear-gradient("direction", "color-stops")"))))
