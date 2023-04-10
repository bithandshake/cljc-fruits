
(ns syntax.tags
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-tag-position
  ; @description
  ; Returns the first position of the 'open-tag' in the string 'n'.
  ;
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
  [n open-tag]
  (string/first-dex-of n open-tag))

(defn close-tag-position
  ; @description
  ; Returns the close pair position of the first occurence of the 'open-tag'
  ; in the string 'n'.
  ;
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
  [n open-tag close-tag]
  (if-let [offset (string/first-dex-of n open-tag)]
          (letfn [
                  ; ...
                  (f0 [observed-part open-tag-found]
                      (+ open-tag-found (string/count-occurences observed-part open-tag)))

                  ; ...
                  (f1 [observed-part close-tag-found]
                      (+ close-tag-found (string/count-occurences observed-part close-tag)))

                  ; ...
                  (f2 [from] (if-let [close-tag-pos (string/first-dex-of (string/part n from) close-tag)]
                                     (+ from close-tag-pos (count close-tag))))

                  ; ...
                  (f3 [from open-tag-found close-tag-found]

                      ; DEBUG
                      ; (println "offset:" offset)
                      ; (println "from:"   from)

                      (if-let [to (f2 from)]
                              (let [observed-part   (string/part n from to)
                                    open-tag-found  (f0 observed-part open-tag-found)
                                    close-tag-found (f1 observed-part close-tag-found)]

                                   ; DEBUG
                                   ; (println "to:"              to)
                                   ; (println "observed-part:"   (str "\""observed-part"\""))
                                   ; (println "open-tag-found:"  open-tag-found)
                                   ; (println "close-tag-found:" close-tag-found)

                                   (if (<= open-tag-found close-tag-found)
                                       (- to (count close-tag))
                                       (f3 to open-tag-found close-tag-found)))))]

                 ; ...
                 (f3 offset 0 0))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-brace-position
  ; @description
  ; Returns the first position of the opening brace character in the string 'n'.
  ;
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
  [n]
  (open-tag-position n "{"))

(defn close-brace-position
  ; @description
  ; Returns the close pair position of the first opening brace character
  ; in the string 'n'.
  ;
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
  [n]
  (close-tag-position n "{" "}"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-bracket-position
  ; @description
  ; Returns the first position of the opening bracket character in the string 'n'.
  ;
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
  [n]
  (open-tag-position n "["))

(defn close-bracket-position
  ; @description
  ; Returns the close pair position of the first opening bracket character
  ; in the string 'n'.
  ;
  ; @description
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
  [n]
  (close-tag-position n "[" "]"))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-paren-position
  ; @description
  ; Returns the first position of the opening parenthesis character in the string 'n'.
  ;
  ; @description
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
  [n]
  ; The 'clj-docs-generator' throws an error if the parenthesis pairs in the code
  ; aren't balanced. That's why I have to put a closing parenthesis here :)
  (open-tag-position n "("))

(defn close-paren-position
  ; @description
  ; Returns the close pair position of the first opening parenthesis character
  ; in the string 'n'.
  ;
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
  [n]
  (close-tag-position n "(" ")"))
