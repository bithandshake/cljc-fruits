
(ns logical.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn =?
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ;  (=? "A" "B" "equal" "not equal")
  ;  =>
  ;  "not equal"
  ;
  ; @return (*)
  ;  Ha a egyenlő b, akkor c különben d
  ([a b c]
   (when (= a b) c))

  ([a b c d]
   (if (= a b) c d)))

(defn not=?
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ;  (not=? "A" "B" "not equal" "equal")
  ;  =>
  ;  "not equal"
  ;
  ; @return (*)
  ;  Ha a nem egyenlő b, akkor c különben d
  ([a b c]
   (when-not (= a b) c))

  ([a b c d]
   (if-not (= a b) c d)))

(defn if-or
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ;  (if-or true false "C" "D")
  ;  =>
  ;  "C"
  ;
  ; @return (*)
  ;  Ha a vagy b igaz, akkor c különben d
  ([a b c]
   (when (or a b) c))

  ([a b c d]
   (if (or a b) c d)))


(defn if-and
  ; @param (*) a
  ; @param (*) b
  ; @param (*) c
  ; @param (*) d
  ;
  ; @example
  ;  (if-and true false "C" "D")
  ;  =>
  ;  "D"
  ;
  ; @return (*)
  ;  Ha a és b igaz, akkor c különben d
  ([a b c]
   (when (and a b) c))

  ([a b c d]
   (if (and a b) c d)))

(defn nor
  ; @param (list of *) abc
  ;
  ; @example
  ;  (nor true false false)
  ;  =>
  ;  false
  ;
  ; @example
  ;  (nor false false false)
  ;  =>
  ;  true
  ;
  ; @example
  ;  (nor false nil)
  ;  =>
  ;  true
  ;
  ; @return (boolean)
  [& abc]
  ; WARNING! Az összes paraméter kiértelése minden esetben megtörténik!
  ; Pl. (nor (my-f   ...)
  ;           (your-f ...))
  ; Az (your-f ...) függvény abban az esetben is lefut, ha az (my-f ...) függvény kimenete igaz!
  (not-any? boolean abc))

(defn or=
  ; @param (*) a
  ; @param (list of *) bcd
  ;
  ; @example
  ;  (or= :a :b :c)
  ;  =>
  ;  false
  ;
  ; @example
  ;  (or= :a :b :a)
  ;  =>
  ;  true
  ;
  ; @example
  ;  (or= :a :a)
  ;  =>
  ;  true
  ;
  ; @return (boolean)
  [a & bcd]
  (boolean (some #(= a %) bcd)))

(defn swap
  ; @param (*) x
  ; @param (*) a
  ; @param (*) b
  ;
  ; @example
  ;  (swap "A" "A" "B")
  ;  =>
  ;  "B"
  ;
  ; @example
  ;  (swap "B" "A" "B")
  ;  =>
  ;  "A"
  ;
  ; @example
  ;  (swap "C" "A" "B")
  ;  =>
  ;  "C"
  ;
  ; @return (*)
  [x a b]
  (cond (= x a) b
        (= x b) a
        :return x))
