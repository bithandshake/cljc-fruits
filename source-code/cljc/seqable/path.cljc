
(ns seqable.path)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn dynamic-path
  ; @description
  ; Converts the given 'path' dynamic path into a static path of the given 'n' value.
  ;
  ; @param (seqable) n
  ; @param (vector) path
  ;
  ; @usage
  ; (dynamic-path [{:a [{:b "B"}]}] [seqable/last-dex :a seqable/last-dex :b])
  ;
  ; @example
  ; (dynamic-path [{:a [{:b "B"}]}] [seqable/last-dex :a seqable/last-dex :b])
  ; =>
  ; [0 :a 0 b]
  ;
  ; @return (vector)
  [n path]
  (letfn [(f0 [result k]
              (if (fn? k)
                  (conj result (-> n (get-in result) k))
                  (conj result (-> k))))]
         (reduce f0 [] path)))
