
(ns fruits.string.walk)

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
  ; =>
  ; (println "Cursor: 0 Character: M")
  ; (println "Cursor: 1 Character: y")
  ; (println "Cursor: 2 Character:  ")
  ; (println "Cursor: 3 Character: s")
  ; (println "Cursor: 4 Character: t")
  ; (println "Cursor: 5 Character: r")
  ; (println "Cursor: 6 Character: i")
  ; (println "Cursor: 7 Character: n")
  ; (println "Cursor: 8 Character: g")
  ;
  ; @usage
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
