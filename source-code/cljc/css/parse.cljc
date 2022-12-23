
(ns css.parse
    (:require [candy.api  :refer [return]]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse
  ; @param (map) n
  ;
  ; @example
  ; (unparse {:opacity 1 :width "100%"})
  ; =>
  ; "opacity: 1; width: 100%;"
  ;
  ; @return (string)
  [n]
  (letfn [(f [style k v] (str style (name k) ": " (if (keyword? v) (name v) v) "; "))]
         (string/trim (reduce-kv f "" n))))

(defn parse
  ; @param (string) n
  ;
  ; @example
  ; (parse "opacity: 1; width: 100%;")
  ; =>
  ; {:opacity 1 :width "100%"}
  ;
  ; @return (map)
  [n]
  ; WARNING! NOT TESTED! DO NOT USE!
  ; TODO! TEST!
  ;
  ; (f0 "opacity: 1") => ["opacity" "1"]
  ; (f0 "opacity:1")  => ["opacity" "1"]
  (letfn [(f0 [x] (if-let [k (string/before-first-occurence x ":" {:return? false})]
                          (if-let [v (string/before-first-occurence x ":" {:return? false})]
                                  (let [k (string/trim k) v (string/trim v)]
                                       (and (string/nonblank? k)
                                            (string/nonblank? v)
                                            [k v])))))
          ; To avoid infinite loops when the f0 function cannot
          ; resolve the passed x fraction, the f1 function quits
          ; parsing the n string, and returns with the incomplete result.
          (f1 [style n] (if-let [x (string/before-first-occurence n ";" {:return? false})]
                                (if-let [[k v] (f0 x)]
                                        (f1 (assoc style (keyword k) v)
                                            (string/after-first-occurence n ";" {:return? false}))
                                        (return style))
                                (if-let [[k v] (f0 n)]
                                        (assoc  style (keyword k) v)
                                        (return style))))]
         (f1 {} n)))
