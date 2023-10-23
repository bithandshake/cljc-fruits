
(ns syntax.comment
    (:require [string.api  :as string]
              [syntax.tags :as tags]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-comments
  ; @param (string) n
  ; @param (string) open-tag
  ; @param (string)(opt) close-tag
  ;  Default: "\n"
  ;
  ; @usage
  ; (remove-comments "(defn my-function []) \n ; My comment \n"
  ;                  ";")
  ;
  ; @example
  ; (remove-comments "(defn my-function []) \n ; My comment \n"
  ;                  ";")
  ; =>
  ; "(defn my-function []) \n"
  ;
  ; @example
  ; (remove-comments "body { /* My comment */ color: blue; }"
  ;                  "/*"
  ;                  "*/")
  ; =>
  ; "body {  color: blue; }"
  ;
  ; @return (string)
  ([n open-tag]
   (remove-comments n open-tag "\n"))

  ([n open-tag close-tag]
   ; BUG#1130
   ; The shadow-cljs protects the '+' function from receiving nil as a parameter.
   ; The 'close-tag-position' function returns through an if-let condition, therefore
   ; without using the (if (number? close-pos) ...) condition, the shadow-cljs
   ; throws the following error message:
   ; "cljs.core/+, all arguments must be numbers, got [#{nil clj-nil} number] instead"
   (letfn [
           ; If both an opening and a closing tag found in the given 'n', cuts
           ; the part between the opening and closing tags, otherwise it returns nil.
           (f0 [n] (if-let [open-pos (tags/open-tag-position n open-tag)]
                           (if-let [close-pos (tags/close-tag-position n open-tag close-tag)]
                                   (if (number? close-pos)
                                       (string/cut n open-pos (+ close-pos (count close-tag)))))))

           ; Cuts out the first comment by using the function 'f0'.
           ; If the 'f0' function found anything to cut it returns the chunked
           ; result (otherwise it returns a nil), after then the 'f1' function calls itself
           ; recursively, otherwise the 'f1' function returns the result from the
           ; previous iteration.
           (f1 [n] (if-let [n (f0 n)]
                           (-> n f1)
                           (-> n)))]

          ; ...
          (f1 n))))
