
(ns fruits.string.trim
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim
  ; @description
  ; Trims both ends of the given 'n' string by removing the leading and trailing whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (trim "  abc  ")
  ; =>
  ; "abc"
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn horizontal-trim
  ; @description
  ; Trims both ends of the given 'n' string by removing the leading and trailing horizontal whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (horizontal-trim "  abc  ")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (horizontal-trim "\n  abc  ")
  ; =>
  ; "\n  abc"
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (loop [cursor 0 left-indent 0 right-indent 0]
             (cond (= cursor (count n))               (subs n left-indent (- (count n) right-indent))
                   (-> n (nth cursor) str (not= " ")) (recur (inc cursor) (->  left-indent) (-> 0))
                   (<= cursor left-indent)            (recur (inc cursor) (inc left-indent) (-> right-indent))
                   :else                              (recur (inc cursor) (->  left-indent) (inc right-indent))))))

(defn vertical-trim
  ; @description
  ; Trims both ends of the given 'n' string by removing the leading and trailing vertical whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (vertical-trim "\n  abc  \n")
  ; =>
  ; "  abc  "
  ;
  ; @return (string)
  [n]
  (let [n (str n)]
       (loop [cursor 0 left-indent 0 right-indent 0]
             (cond (= cursor (count n))                (subs n left-indent (- (count n) right-indent))
                   (-> n (nth cursor) str (not= "\n")) (recur (inc cursor) (->  left-indent) (-> 0))
                   (<= cursor left-indent)             (recur (inc cursor) (inc left-indent) (-> right-indent))
                   :else                               (recur (inc cursor) (->  left-indent) (inc right-indent))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-start
  ; @description
  ; Trims the beginning of the given 'n' string by removing the leading whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (trim-start "  abc  ")
  ; =>
  ; "abc  "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/triml))

(defn trim-end
  ; @description
  ; Trims the end of the given 'n' string by removing the trailing whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (trim-end "  abc  ")
  ; =>
  ; "abc  "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trimr))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn trim-newlines
  ; @description
  ; Trims the trailing newlines of the given 'n' string by removing the all newlines from the end.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (trim-newlines "abc \r\n")
  ; =>
  ; "abc "
  ;
  ; @usage
  ; (trim-newlines "\nabc \r\n")
  ; =>
  ; "\nabc "
  ;
  ; @return (string)
  [n]
  (-> n str clojure.string/trim-newline))

(defn trim-gaps
  ; @description
  ; Trims the whitespace gaps in the given 'n' string by removing the duplicated whitespaces.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (trim-gaps "  a  b  c  ")
  ; =>
  ; " a b c "
  ;
  ; @return (string)
  [n]
  ; "\h" matches horizontal whitespace, which includes spaces and tabs but excludes newline.
  (-> n str (clojure.string/replace #"[\h]+" " ")))
