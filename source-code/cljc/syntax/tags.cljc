
(ns syntax.tags
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-tag-position
  ; @param (string) n
  ; @param (string) open-tag
  ;
  ; @example
  ; (open-tag-position "<div>My content</div>" "<div>")
  ; =>
  ; 0
  ;
  ; @example
  ; (open-tag-position "<div><div></div></div>" "<div>")
  ; =>
  ; 0
  ;
  ; @example
  ; (open-tag-position "</div> <div></div>" "<div>")
  ; =>
  ; 7
  ;
  ; @return (integer)
  ; Returns with the position of the first occurence of the given open-tag in
  ; the string n.
  [n open-tag]
  (string/first-dex-of n open-tag))

(defn close-tag-position
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (string) close-tag
  ;
  ; @example
  ; (close-tag-position "<div>My content</div>" "<div>" "</div>")
  ; =>
  ; 15
  ;
  ; @example
  ; (close-tag-position "<div><div></div></div>" "<div>" "</div>")
  ; =>
  ; 16
  ;
  ; @example
  ; (close-tag-position "</div> <div></div>" "<div>" "</div>")
  ; =>
  ; 12
  ;
  ; @return (integer)
  ; Returns with the position of the close-pair of the first occurence of the
  ; given open-tag in the string n.
  [n open-tag close-tag]
  (if (and (string/contains-part? n  open-tag)
           (string/contains-part? n close-tag))
      (letfn [(f [cursor]
                 (let [a               (string/part             n  0 cursor)
                       b               (string/part             n    cursor)
                        open-tag-count (string/count-occurences a  open-tag)
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
                          (if-let [close-tag-position (string/first-dex-of b close-tag)]
                                  (do ;(println "close-tag-position:" close-tag-position)
                                      (f (+ close-tag-position (count close-tag) cursor)))))))]
             (f (string/first-dex-of n open-tag)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-brace-position
  ; @param (n)
  ;
  ; @example
  ; (open-brace-position "{:a 0}")
  ; =>
  ; 0
  ;
  ; @example
  ; (open-brace-position "([] {:a {:b 0}})")
  ; =>
  ; 4
  ;
  ; @example
  ; (open-brace-position "} {}")
  ; =>
  ; 2
  ;
  ; @return (integer)
  ; Returns with the position of the first occurence of the character "{"
  ; in the string n.
  [n]
  (open-tag-position n "{"))

(defn close-brace-position
  ; @param (n)
  ;
  ; @example
  ; (close-brace-position "{:a 0}")
  ; =>
  ; 5
  ;
  ; @example
  ; (close-brace-position "([] {:a {:b 0}})")
  ; =>
  ; 14
  ;
  ; @example
  ; (close-brace-position "} {}")
  ; =>
  ; 3
  ;
  ; @return (integer)
  ; Returns with the position of the close-pair of the first occurence of the
  ; character "{" in the string n.
  [n]
  (close-tag-position n "{" "}"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-bracket-position
  ; @param (n)
  ;
  ; @example
  ; (open-bracket-position "[1 2]")
  ; =>
  ; 0
  ;
  ; @example
  ; (open-bracket-position "({} [[0 1]])")
  ; =>
  ; 4
  ;
  ; @example
  ; (open-bracket-position "] []")
  ; =>
  ; 2
  ;
  ; @return (integer)
  ; Returns with the position of the first occurence of the character "["
  ; in the string n.
  [n]
  (open-tag-position n "["))

(defn close-bracket-position
  ; @param (n)
  ;
  ; @example
  ; (close-bracket-position "[1 2]")
  ; =>
  ; 4
  ;
  ; @example
  ; (close-bracket-position "({} [[0 1]])")
  ; =>
  ; 10
  ;
  ; @example
  ; (close-bracket-position "] []")
  ; =>
  ; 3
  ;
  ; @return (integer)
  ; Returns with the position of the close-pair of the first occurence of the
  ; character "[" in the string n.
  [n]
  (close-tag-position n "[" "]"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-paren-position
  ; @param (n)
  ;
  ; @example
  ; (open-paren-position "(+ 1 2)")
  ; =>
  ; 0
  ;
  ; @example
  ; (open-paren-position "[{} (+ 1 (inc 2) (inc 3))]")
  ; =>
  ; 4
  ;
  ; @return (integer)
  ; Returns with the position of the first occurence of the character parenthesis
  ; open character in the string n.
  [n]
  ; A docs-api dokumentáció készítő nem tudja beolvasni ezt a függvényt, ha
  ; páratlan zárójel(ek) vannak benne, ezért szükésges idetenni egy szmájlit :)
  (open-tag-position n "("))

(defn close-paren-position
  ; @param (n)
  ;
  ; @example
  ; (close-paren-position "(+ 1 2)")
  ; =>
  ; 6
  ;
  ; @example
  ; (close-paren-position "[{} (+ 1 (inc 2) (inc 3))]")
  ; =>
  ; 24
  ;
  ; @return (integer)
  ; Returns with the position of the close-pair of the first occurence of the
  ; character parenthesis open character in the string n.
  [n]
  (close-tag-position n "(" ")"))
