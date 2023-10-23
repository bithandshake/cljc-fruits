
(ns uri.check
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn url-path-template-matches?
  ; @param (string) url-path
  ; @param (string) url-path-template
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: false}
  ;
  ; @usage
  ; (url-path-template-matches? "/my-path/my-value" "/my-path/:my-param")
  ;
  ; @example
  ; (url-path-template-matches? "/my-path/my-value" "/my-path/:my-param")
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([url-path url-path-template]
   (url-path-template-matches? url-path url-path-template {}))

  ([url-path url-path-template {:keys [case-sensitive?]}]
   (letfn [(f1 [url-path url-path-template]
               (let [url-path-parts                (string/split url-path          #"/")
                     url-path-template-parts       (string/split url-path-template #"/")
                     url-path-parts-count          (count        url-path-parts)
                     url-path-template-parts-count (count        url-path-template-parts)]
                    (letfn [(f0 [dex] (cond
                                            ; If the 'dex' is higher than the item count of the
                                            ; 'url-path-template-parts' vector, the iteration is
                                            ; finished and all the parts are matching (or skipped).
                                            (= dex url-path-template-parts-count)
                                            (-> true)

                                            ; If the first character of the 'url-path-template-part'
                                            ; is ":", that means the part is a path parameter variable.
                                             ; The function skips this part and steps into the
                                            ; next iteration.
                                            (= ":" (str (get-in url-path-template-parts [dex 0])))
                                            (f0 (inc dex))

                                            ; If the 'url-path-part' and the 'url-path-template-part'
                                            ; are matching, the function step into the next iteration.
                                            (=  (get url-path-parts          dex)
                                                (get url-path-template-parts dex))
                                            (f0 (inc dex))

                                            ; If the parts aren't matching, the function returns
                                            ; with nil.
                                            :unmatching-parts nil))]

                           ; The function only does the matching process if the 'url-path-parts'
                           ; and the 'url-path-template-parts' has the same item count.
                           (if (= url-path-parts-count url-path-template-parts-count)
                               (f0 0)))))]

          ; ...
          (if case-sensitive? (f1 (str                 url-path)
                                  (str                 url-path-template))
                              (f1 (string/to-lowercase url-path)
                                  (string/to-lowercase url-path-template))))))
