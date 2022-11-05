
(ns syntax.tags
    (:require [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn tag-close-dex
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (string) close-tag
  ;
  ; @example
  ;  (tag-close-dex "<div>My content</div>" "<div>" "</div>")
  ;  =>
  ;  15
  ;
  ; @example
  ;  (tag-close-dex "<div><div></div></div>" "<div>" "</div>")
  ;  =>
  ;  16
  ;
  ; @example
  ;  (tag-close-dex "</div> <div></div>" "<div>" "</div>")
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n open-tag close-tag]
  (if (string/contains-part? n close-tag)
      (letfn [(f [cursor]
                 (let [a               (string/part             n  0 cursor)
                       b               (string/part             n    cursor)
                       open-tag-count  (string/count-occurences a  open-tag)
                       close-tag-count (string/count-occurences a close-tag)]
                      ;(println)
                      ;(println (str "\"" a "\""))
                      ;(println (str "\"" b "\""))
                      ;(println "cursor:"           cursor)
                      ;(println "open-tag-count:"   open-tag-count)
                      ;(println "close-tag-count:" close-tag-count)
                      (if (and (>  close-tag-count 0)
                               (>= close-tag-count open-tag-count))
                          (do ;(println "result:" (string/last-dex-of a close-tag))
                              (string/last-dex-of a close-tag))
                          (if-let [close-tag-dex (string/first-dex-of b close-tag)]
                                  (do ;(println "close-tag-dex:" close-tag-dex)
                                      (f (+ close-tag-dex (count close-tag) cursor)))))))]
             (f 0))))

(defn brace-close-dex
  ; @param (n)
  ;
  ; @example
  ;  (brace-close-dex "{:a 0}")
  ;  =>
  ;  5
  ;
  ; @example
  ;  (brace-close-dex "([] {:a {:b 0}}")
  ;  =>
  ;  14
  ;
  ; @example
  ;  (brace-close-dex "} {}")
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n]
  (tag-close-dex n "{" "}"))

(defn bracket-close-dex
  ; @param (n)
  ;
  ; @example
  ;  (bracket-close-dex "[1 2]")
  ;  =>
  ;  4
  ;
  ; @example
  ;  (bracket-close-dex "({} [[0 1]])")
  ;  =>
  ;  10
  ;
  ; @example
  ;  (bracket-close-dex "] []")
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n]
  (tag-close-dex n "[" "]"))

(defn paren-close-dex
  ; @param (n)
  ;
  ; @example
  ;  (paren-close-dex "(+ 1 2)")
  ;  =>
  ;  6
  ;
  ; @example
  ;  (paren-close-dex "[{} (+ 1 (inc 2) (inc 3))]")
  ;  =>
  ;  24
  ;
  ; @example
  ;  (paren-close-dex ") ()")
  ;  =>
  ;  0
  ;
  ; @return (integer)
  [n]
  (tag-close-dex n "(" ")"))
