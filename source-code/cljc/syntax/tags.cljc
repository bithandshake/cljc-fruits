
(ns syntax.tags
    (:require [string.api   :as string]
              [syntax.check :as check]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn tag-position
  ; @description
  ; - Returns the position of the first occurence of 'tag' string in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (string) tag
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
  ;
  ; @example
  ; (tag-position "<div>My content</div>" "<div>")
  ; =>
  ; 0
  ;
  ; @example
  ; (tag-position "<div><div></div></div>" "<div>")
  ; =>
  ; 0
  ;
  ; @example
  ; (tag-position "</div> <div></div>" "<div>")
  ; =>
  ; 7
  ;
  ; @return (integer)
  ([n tag]
   (tag-position n tag {}))

  ([n tag {:keys [comment-close-tag comment-open-tag ignore-comments? offset]
           :or   {comment-close-tag "\n" comment-open-tag ";" offset 0}
           :as   options}]
   (if (string/cursor-in-bounds? n offset)
       (let [observed-part (string/part n offset)]
            (if-let [observed-tag-pos (string/first-dex-of observed-part tag)]
                    (let [observed-tag-pos (+ offset observed-tag-pos)]
                         (if ignore-comments? (if (check/position-commented? n observed-tag-pos comment-open-tag comment-close-tag)
                                                  (tag-position n tag (assoc options :offset (+ observed-tag-pos (count tag))))
                                                  (-> observed-tag-pos))
                                              (-> observed-tag-pos))))))))

(defn tag-count
  ; @description
  ; - Returns the found occurence count of the 'tag' string in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ;
  ; @param (string) n
  ; @param (string) tag
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
  ;
  ; @example
  ; (tag-count "<div><div></div></div>" "<div>")
  ; "** ***"
  ; =>
  ; 2
  ;
  ; @return (integer)
  ([n tag]
   (tag-count n tag {}))

  ([n tag {:keys [offset] :or {offset 0} :as options}]
   (letfn [(f [cursor found-tag-count]
              (if (string/cursor-in-bounds? n cursor)
                  (if-let [first-tag-pos (tag-position n tag (assoc options :offset cursor))]
                          (f (+ first-tag-pos (count tag)) (inc found-tag-count))
                          (-> found-tag-count))
                  (-> found-tag-count)))]
          ; ...
          (f offset 0))))

(defn tags-balanced?
  ; @description
  ; - Returns TRUE if the given 'open-tag' and 'close-tag' pairs are balanced in their quantity in the given 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ;
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (string) close-tag
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
  ;
  ; @example
  ; (tags-balanced? "<div><div></div>" "<div>" "</div>")
  ; =>
  ; false
  ;
  ; @example
  ; (tags-balanced? "<div><div></div></div>" "<div>" "</div>")
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n open-tag close-tag]
   (tags-balanced? n open-tag close-tag {}))

  ([n open-tag close-tag options]
   (= (tag-count n open-tag  options)
      (tag-count n close-tag options))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-tag-position
  ; @description
  ; - Returns the position of the first 'open-tag' string in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n open-tag]
   (open-tag-position n open-tag {}))

  ([n open-tag options]
   (tag-position n open-tag options)))

(defn close-tag-position
  ; @description
  ; - Returns the position of the close pair of the first occurence of the 'open-tag' string in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (string) close-tag
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ; 12 <- DEPRECTATED BEHAVIOUR
  ; 0
  ;
  ; @return (integer)
  ([n open-tag close-tag]
   (close-tag-position n open-tag close-tag {}))

  ([n open-tag close-tag {:keys [offset] :or {offset 0} :as options}]
   (letfn [(f [cursor]
              (if (string/cursor-in-bounds? n cursor)
                  (if-let [observed-close-pos (tag-position n close-tag (assoc options :offset cursor))]
                          (let [observed-part (string/part n 0 (+ observed-close-pos (count close-tag)))]
                               (if (tags-balanced? observed-part open-tag close-tag options)
                                   (-> observed-close-pos)
                                   (f (+ observed-close-pos (count close-tag))))))))]
          ; ...
          (f offset))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-brace-position
  ; @description
  ; - Returns the position of the first opening brace character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (open-brace-position n {}))

  ([n options]
   (open-tag-position n "{" options)))

(defn close-brace-position
  ; @description
  ; - Returns the position of the closing brace that corresponds to the first opening brace character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and does not depend on the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (close-brace-position n {}))

  ([n options]
   (close-tag-position n "{" "}" options)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-bracket-position
  ; @description
  ; - Returns the position of the first opening bracket character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (open-bracket-position n {}))

  ([n options]
   (open-tag-position n "[" options)))

(defn close-bracket-position
  ; @description
  ; - Returns the position of the closing bracket that corresponds to the first opening bracket character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and does not depend on the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (close-bracket-position n {}))

  ([n options]
   (close-tag-position n "[" "]" options)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn open-paren-position
  ; @description
  ; - Returns the position of the first opening parenthesis character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and it is independent from the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (open-paren-position n {}))

  ([n options]
   ; The 'bithandshake/clj-docs-generator' library would throw an error of unbalanced parens
   ; without this little fella' -> :)
   (open-tag-position n "(" options)))

(defn close-paren-position
  ; @description
  ; - Returns the position of the closing parenthesis that corresponds to the first opening parenthesis character in the 'n' string.
  ; - If the 'offset' parameter is passed, the search starts from the offset position.
  ; - The returned position is an absolute value and does not depend on the offset.
  ;
  ; @param (string) n
  ; @param (map)(opt) options
  ; {:comment-close-tag (string)(opt)
  ;   Default: "\n"
  ;  :comment-open-tag (string)(opt)
  ;   Default ";"
  ;  :ignore-comments? (boolean)(opt)
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   Default: false
  ;  :offset (integer)(opt)
  ;   Default: 0}
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
  ([n]
   (close-paren-position n {}))

  ([n options]
   (close-tag-position n "(" ")" options)))
