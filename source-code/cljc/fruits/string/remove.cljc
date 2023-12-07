
(ns fruits.string.remove
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn remove-part
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (remove-part "abc" "b")
  ;
  ; @example
  ; (remove-part "abc" "b")
  ; =>
  ; "ac"
  ;
  ; @example
  ; (remove-part "abc abc" "b")
  ; =>
  ; "ac ac"
  ;
  ; @example
  ; (remove-part "abc abc 123" #"\d")
  ; =>
  ; "abc abc "
  ;
  ; @example
  ; (remove-part "///" "//")
  ; =>
  ; "/"
  ;
  ; @return (string)
  [n x]
  (let [n (str n)]
       (clojure.string/replace n x "")))

(defn remove-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ;
  ; @example
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "never")
  ; =>
  ; "With insomnia, you're really awake; but you're never really asleep."
  ;
  ; @example
  ; (remove-first-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                         "abc")
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @return (string)
  ([n x]
   (remove-first-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (str (subs n 0 dex)
                            (subs n (+ dex (count x))))
                       (-> n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn remove-last-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ;
  ; @example
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "never")
  ; =>
  ; "With insomnia, you're never really awake; but you're really asleep."
  ;
  ; @example
  ; (remove-last-occurence "With insomnia, you're never really awake; but you're never really asleep."
  ;                        "abc")
  ; =>
  ; "With insomnia, you're never really awake; but you're never really asleep."
  ;
  ; @return (string)
  ([n x]
   (remove-last-occurence n x {}))

  ([n x {:keys [case-sensitive?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (str (subs n 0 dex)
                            (subs n (+ dex (count x))))
                       (-> n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))
