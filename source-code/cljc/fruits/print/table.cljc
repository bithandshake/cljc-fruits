
(ns fruits.print.table
    (:require [fruits.string.api :as string]
              [fruits.vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn table-line
  ; @description
  ; Prints a horizontal line on the console.
  ;
  ; @param (integer) size
  ;
  ; @usage
  ; (table-line 16)
  ; =>
  ; (println "|----------------|")
  [size]
  (as-> size % (string/repeat "-" %)
               (str "|" % "|")
               (println %)))

(defn table-row
  ; @description
  ; Prints a table row displaying the given data to the console.
  ;
  ; @param (vectors in vector) columns
  ; [[(integer) size
  ;   (string)(opt) content
  ;   (keyword)(opt) align
  ;    :center, :left, :right
  ;    Default: :center]]
  ;
  ; @usage
  ; (table-row [[16 "abc" :right] [8]])
  ; =>
  ; (println "|            abc |        |")
  [columns]
  (letfn [(f0 [[size content align]]
              (let [space (- size (-> content str count))]
                   (case align :left  (cond (>  2 space)  (str     content (string/repeat " " space))
                                            (<= 2 space)  (str " " content (string/repeat " " (dec space))))
                               :right (cond (>  2 space)  (str (string/repeat " " space)       content)
                                            (<= 2 space)  (str (string/repeat " " (dec space)) content " "))
                                      (cond (even? space) (str (string/repeat " " (/ space 2))       content (string/repeat " " (/ space 2)))
                                            (odd?  space) (str (string/repeat " " (/ (dec space) 2)) content (string/repeat " " (/ (inc space) 2)))))))]
         (as-> columns % (vector/->items % f0)
                         (string/join % "|")
                         (str "|" % "|")
                         (println %))))
