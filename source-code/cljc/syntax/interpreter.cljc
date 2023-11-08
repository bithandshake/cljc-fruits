
(ns syntax.interpreter
    (:require [string.api :as string]
              [vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn tag-matrix
  ; @description
  ; Returns a map that contains all the opening and closing positions of the given tags in the given 'n' string.
  ;
  ; @param (string) n
  ; @param (map) tags
  ; {:my-tag-name (strings in vector)
  ;   [(string) open-tag
  ;    (string) close-tag]
  ;  ...}
  ; @param (map)(opt) options
  ; {:ignore-escaped? (boolean)(opt)
  ;   Default: false
  ;
  ; @usage
  ; (tag-matrix "(defn my-function [])" {:paren ["(" ")"]})
  ;
  ; @example
  ; (tag-matrix "(defn my-function [])" {:paren ["(" ")"]})
  ; =>
  ; {:paren {:opens [0] :closes [20]}}
  ;
  ; @example
  ; (tag-matrix "(defn my-function [])" {:paren ["(" ")"] :bracket ["[" "]"]})
  ; =>
  ; {:paren   {:opens [0]  :closes [20]}
  ;  :bracket {:opens [18] :closes [19]}}
  ;
  ; @return (map)
  ([n tags]
   (tag-matrix n tags {}))

  ([n tags {:keys [ignore-escaped?]}]
   (letfn [; Returns TRUE if the given 'tag' string starts at the given 'cursor' value in the 'n' string.
           (f0 [cursor tag] (= tag (subs n cursor (-> tag count (+ cursor)))))

           ; If the open or the close tag starts at the actual cursor it updates the state and returns it,
           ; otherwise it returns NIL.
           (f1 [state cursor [tag-name [open-tag close-tag]]]
               (cond (f0 cursor open-tag)  (update-in state [tag-name :opens]  vector/conj-item cursor)
                     (f0 cursor close-tag) (update-in state [tag-name :closes] vector/conj-item cursor)))

           ; If the 'f1' function found something and returned an updated state it returns the updated state,
           ; otherwise returns the given state.
           (f2 [state cursor] (or (some #(f1 state cursor %) tags) state))]

          ; Iterates over the given 'n' string and applies the 'f2' function at every cursor position.
          (string/walk n {} f2))))

(defn interpreter
  ; @param (string) n
  ; @param (map) options
  ; {:ignore-comments? (boolean)(opt)
  ;   To use this option, provide the ':comment' value in the ':tags' property!
  ;   Default: false
  ;  :ignore-quotes? (boolean)(opt)
  ;   To use this option, provide the ':quote' value in the ':tags' property!
  ;   Default: false
  ;  :ignore-escaped? (boolean)(opt)
  ;   Default: false
  ;  :tags (map)
  ;   {:my-tag-name (strings in vector)
  ;     [(string) open-tag
  ;      (string) close-tag]}}
  ;   Default: {:bracket ["[" "]"]
  ;             :comment [";" "\n"]
  ;             :paren   ["(" ")"]
  ;             :quote   ["\"" "\""]}}
  ;
  ; @usage
  ; "abc"
  [n {:keys [tags] :or {tags {:bracket ["[" "]"] :comment [";" "\n"] :paren ["(" ")"] :quote ["\"" "\""]}}}]
  (tag-matrix n tags {}))
