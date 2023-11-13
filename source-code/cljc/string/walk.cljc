
(ns string.walk)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @description
  ; - Iterates over the given 'n' string and applies the given 'f' function in each iteration.
  ; - The 'f' function takes its own output from the previous iteration (or the 'initial' value)
  ;   and the actual cursor value as parameters.
  ;
  ; @param (string) n
  ; @param (*) initial
  ; Default: nil
  ; @param (function) f
  ;
  ; @usage
  ; (let [my-string "My string"]
  ;      (walk my-string (fn [_ cursor] (println "Cursor:" cursor "Character:" (nth my-string cursor)))))
  ;
  ; @example
  ; (let [my-string "My string"]
  ;      (walk my-string (fn [_ cursor] (println "Cursor:" cursor "Character:" (nth my-string cursor)))))
  ; =>
  ; > "Cursor: 0 Character: M"
  ; > "Cursor: 1 Character: y"
  ; > "Cursor: 2 Character:  "
  ; > "Cursor: 3 Character: s"
  ; > "Cursor: 4 Character: t"
  ; > "Cursor: 5 Character: r"
  ; > "Cursor: 6 Character: i"
  ; > "Cursor: 7 Character: n"
  ; > "Cursor: 8 Character: g"
  ;
  ; @example
  ; (let [my-string "My string"]
  ;      (walk my-string (fn [result cursor] (str (nth my-string cursor) result))))
  ; =>
  ; "gnirts yM"
  ;
  ; @return (*)
  ([n f]
   (walk n nil f))

  ([n initial f]
   (let [n (str n)]
        (letfn [(f0 [result cursor]
                    (if (-> n count (= cursor))
                        (-> result)
                        (f0 (f result cursor) (inc cursor))))]
               (f0 initial 0)))))
