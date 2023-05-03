
(ns print.table
    (:require [string.api :as string]
              [vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn table-line
  ; @param (integer) size
  ;
  ; @usage
  ; (table-line 50)
  [size]
  (as-> size % (string/multiply "-" %)
               (str "|" % "|")
               (println %)))

(defn table-row
  ; @param (vectors in vector) columns
  ; [[(integer) size
  ;   (string)(opt) content
  ;   (keyword)(opt) align
  ;    :center, :left, :right
  ;    Default: :center]]
  ;
  ; @usage
  ; (table-row [[8 "..."] [...]])
  [columns]
  (letfn [(f [[size content align]]
             (let [space (- size (-> content str count))]
                  (case align :left  (cond (>  2 space) (str     content (string/multiply " " space))
                                           (<= 2 space) (str " " content (string/multiply " " (dec space))))
                              :right (cond (>  2 space) (str (string/multiply " " space)      content)
                                           (<= 2 space) (str (string/multiply " " (dec space) content " ")))
                                     (cond (even? space) (str (string/multiply " " (/ space 2))       content (string/multiply " " (/ space 2)))
                                           (odd?  space) (str (string/multiply " " (/ (dec space) 2)) content (string/multiply " " (/ (inc space) 2)))))))]
         (as-> columns % (vector/->items % f)
                         (string/join % "|")
                         (str "|" % "|")
                         (println %))))
