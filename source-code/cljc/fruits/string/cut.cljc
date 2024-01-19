
(ns fruits.string.cut
    (:require [clojure.string]
              [fruits.math.api      :as math]
              [fruits.seqable.api   :as seqable]
              [fruits.string.inline :as inline]
              [fruits.string.set    :as set]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn keep-range
  ; @description
  ; Keeps a specific range of the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ;
  ; @usage
  ; (keep-range "abcdef" 2 4)
  ; =>
  ; "cd"
  ;
  ; @usage
  ; (keep-range "abcdef" 4 2)
  ; =>
  ; "cd"
  ;
  ; @usage
  ; (keep-range 12345 2 4)
  ; =>
  ; "34"
  ;
  ; @usage
  ; (keep-range [:a :b] 0 6)
  ; =>
  ; "[:a, :"
  ;
  ; @return (string)
  ([n start]
   (let [n   (str n)
         end (count n)]
        (keep-range n start end)))

  ([n start end]
   (let [n     (-> n str)
         start (-> n (seqable/normalize-cursor start {:adjust? true :mirror? true}))
         end   (-> n (seqable/normalize-cursor end   {:adjust? true :mirror? true}))]
        (subs n (min start end)
                (max start end)))))

(defn cut-range
  ; @description
  ; Removes a specific range from the given 'n' string.
  ;
  ; @param (string) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ; @param (map)(opt) options
  ; {:keep-inline-position? (boolean)(opt)
  ;   Ensures that the following part has the same inline position before and after the cut.
  ;   Default: false}
  ;
  ; @usage
  ; (cut-range "abcdef" 2 4)
  ; =>
  ; "abef"
  ;
  ; @usage
  ; (cut-range "abcdef" 4 2)
  ; =>
  ; "abef"
  ;
  ; @usage
  ; (cut-range 12345 2 4)
  ; =>
  ; "125"
  ;
  ; @usage
  ; (cut-range [:a :b] 0 3)
  ; =>
  ; " :b]"
  ;
  ; @usage
  ; (cut-range "abc\n      def" 3 10 {:keep-inline-position? false})
  ; =>
  ; "abcdef"
  ;
  ; @usage
  ; (cut-range "abc\n      def" 3 10 {:keep-inline-position? true})
  ; =>
  ; "abc   def"
  ;
  ; @return (string)
  ([n start]
   (let [n   (str n)
         end (count n)]
        (cut-range n start end)))

  ([n start end]
   (cut-range n start end {}))

  ([n start end {:keys [keep-inline-position?]}]
   (let [n     (str n)
         start (seqable/normalize-cursor n start {:adjust? true :mirror? true})
         end   (seqable/normalize-cursor n end   {:adjust? true :mirror? true})]
        (if-not keep-inline-position? (str (subs n 0 (min start end))
                                           (subs n   (max start end)))
                                      (inline/fix-inline-position (str (subs n 0 (min start end))
                                                                       (subs n   (max start end)))
                                                                  (min start end)
                                                                  (inline/inline-position n (max start end)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @description
  ; Returns the part of the given 'n' string that ends where the first occurence of the given 'x' string starts (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-occurence "abcabc" "b")
  ; =>
  ; "a"
  ;
  ; @usage
  ; (before-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (before-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-first-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-first-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (subs n 0 dex)
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn before-last-occurence
  ; @description
  ; Returns the part of the given 'n' string that ends where the last occurence of the given 'x' string starts (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-occurence "abcabc" "b")
  ; =>
  ; "abca"
  ;
  ; @usage
  ; (before-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (before-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (before-last-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (before-last-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (subs n 0 dex)
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn after-first-occurence
  ; @description
  ; Returns the part of the given 'n' string that starts where the first occurence of the given 'x' string ends (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-occurence "abcabc" "b")
  ; =>
  ; "cabc"
  ;
  ; @usage
  ; (after-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (after-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-first-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-first-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (subs n (+ dex (count x)))
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn after-last-occurence
  ; @description
  ; Returns the part of the given 'n' string that starts where the last occurence of the given 'x' string ends (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-occurence "abcabc" "b")
  ; =>
  ; "c"
  ;
  ; @usage
  ; (after-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (after-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (after-last-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (after-last-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (subs n (+ dex (count x)))
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn from-first-occurence
  ; @description
  ; Returns the part of the given 'n' string that starts where the first occurence of the given 'x' string starts (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-occurence "abcabc" "b")
  ; =>
  ; "bcabc"
  ;
  ; @usage
  ; (from-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (from-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-first-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-first-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (subs n dex)
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn from-last-occurence
  ; @description
  ; Returns the part of the given 'n' string that starts where the last occurence of the given 'x' string starts (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-occurence "abcabc" "b")
  ; =>
  ; "bc"
  ;
  ; @usage
  ; (from-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (from-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (from-last-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (from-last-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (subs n dex)
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn to-first-occurence
  ; @description
  ; Returns the part of the given 'n' string that ends where the first occurence of the given 'x' string ends (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-occurence "abcabc" "b")
  ; =>
  ; "ab"
  ;
  ; @usage
  ; (to-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (to-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-first-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-first-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/index-of o x)]
                       (subs n 0 (+ dex (count x)))
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn to-last-occurence
  ; @description
  ; Returns the part of the given 'n' string that ends where the last occurence of the given 'x' string ends (if any).
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-occurence "abcabc"  "b")
  ; =>
  ; "abcab"
  ;
  ; @usage
  ; (to-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (to-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @usage
  ; (to-last-occurence nil "b" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x]
   (to-last-occurence n x {}))

  ([n x {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x]
               (if-let [dex (clojure.string/last-index-of o x)]
                       (subs n 0 (+ dex (count x)))
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case))))))

(defn between-occurences
  ; @description
  ; Returns the part of the given 'n' string that is surrounded by the given 'x' and 'y' strings.
  ;
  ; @param (string) n
  ; @param (string) x
  ; @param (string) y
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of the occurences are not found.
  ;   Default: false}
  ;
  ; @usage
  ; (between-occurences "abcabc" "b" "a")
  ; =>
  ; "c"
  ;
  ; @usage
  ; (between-occurences "abcabc" "b" "d")
  ; =>
  ; nil
  ;
  ; @usage
  ; (between-occurences "abcabc" "b" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @usage
  ; (between-occurences nil "b" "a")
  ; =>
  ; nil
  ;
  ; @usage
  ; (between-occurences nil "b" "a" {:return? true})
  ; =>
  ; ""
  ;
  ; @return (string)
  ([n x y]
   (between-occurences n x y {}))

  ([n x y {:keys [case-sensitive? return?] :or {case-sensitive? true}}]
   (letfn [(f0 [n o x y]
               (if-let [dex (clojure.string/index-of o x)]
                       (let [after-x (subs n (+ dex (count x)))]
                            (if-let [dex (clojure.string/index-of after-x y)]
                                    (subs after-x 0 dex)
                                    (if return? n)))
                       (if return? n)))]
          (if case-sensitive? (f0 (-> n str)
                                  (-> n str)
                                  (-> x str)
                                  (-> y str))
                              (f0 (-> n str)
                                  (-> n str clojure.string/lower-case)
                                  (-> x str clojure.string/lower-case)
                                  (-> y str clojure.string/lower-case))))))
