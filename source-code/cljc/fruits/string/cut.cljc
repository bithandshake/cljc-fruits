
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
  ; Keeps a specific range from the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ;
  ; @usage
  ; (keep-range "abc" 0 2)
  ;
  ; @example
  ; (keep-range "abcdef" 2 4)
  ; =>
  ; "cd"
  ;
  ; @example
  ; (keep-range "abcdef" 4 2)
  ; =>
  ; "cd"
  ;
  ; @example
  ; (keep-range 12345 2 4)
  ; =>
  ; "34"
  ;
  ; @example
  ; (keep-range [:a :b] 0 6)
  ; =>
  ; "[:a, :"
  ;
  ; @return (string)
  ([n start]
   (keep-range n start nil))

  ([n start end]
   (let [n     (-> n str)
         start (-> n (seqable/normalize-cursor (-> start)))
         end   (-> n (seqable/normalize-cursor (-> end (or (count n)))))]
        (subs n (min start end)
                (max start end)))))

(defn cut-range
  ; @description
  ; Removes a specific range from the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (integer) start
  ; @param (integer)(opt) end
  ; @param (map)(opt) options
  ; {:keep-inline-position? (boolean)(opt)
  ;   Ensures that the following part has the same inline position before and after the cut.
  ;   Default: false}
  ;
  ; @usage
  ; (cut-range "abcdef" 2 4)
  ;
  ; @example
  ; (cut-range "abcdef" 2 4)
  ; =>
  ; "abef"
  ;
  ; @example
  ; (cut-range "abcdef" 4 2)
  ; =>
  ; "abef"
  ;
  ; @example
  ; (cut-range 12345 2 4)
  ; =>
  ; "125"
  ;
  ; @example
  ; (cut-range [:a :b] 0 3)
  ; =>
  ; " :b]"
  ;
  ; @example
  ; (cut-range "abc\n      def" 3 10 {:keep-inline-position? false})
  ; =>
  ; "abcdef"
  ;
  ; @example
  ; (cut-range "abc\n      def" 3 10 {:keep-inline-position? true})
  ; =>
  ; "abc   def"
  ;
  ; @return (string)
  ([n start]
   (cut-range n start nil))

  ([n start end]
   (cut-range n start end {}))

  ([n start end {:keys [keep-inline-position?]}]
   (let [n     (str n)
         start (seqable/normalize-cursor n (-> start))
         end   (seqable/normalize-cursor n (-> end (or (count n))))]
        (if-not keep-inline-position? (str (subs n 0 (min start end))
                                           (subs n   (max start end)))
                                      (inline/fix-inline-position (str (subs n 0 (min start end))
                                                                       (subs n   (max start end)))
                                                                  (min start end)
                                                                  (inline/inline-position n (max start end)))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn before-first-occurence
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-first-occurence "abcabc" "b")
  ;
  ; @example
  ; (before-first-occurence "abcabc" "b")
  ; =>
  ; "a"
  ;
  ; @example
  ; (before-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (before-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (before-last-occurence "abcabc" "b")
  ;
  ; @example
  ; (before-last-occurence "abcabc" "b")
  ; =>
  ; "abca"
  ;
  ; @example
  ; (before-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (before-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (before-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-first-occurence "abcabc" "b")
  ;
  ; @example
  ; (after-first-occurence "abcabc" "b")
  ; =>
  ; "cabc"
  ;
  ; @example
  ; (after-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (after-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (after-last-occurence "abcabc" "b")
  ;
  ; @example
  ; (after-last-occurence "abcabc" "b")
  ; =>
  ; "c"
  ;
  ; @example
  ; (after-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (after-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (after-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-first-occurence "abcabc" "b")
  ;
  ; @example
  ; (from-first-occurence "abcabc" "b")
  ; =>
  ; "bcabc"
  ;
  ; @example
  ; (from-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (from-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (from-last-occurence "abcabc" "b")
  ;
  ; @example
  ; (from-last-occurence "abcabc" "b")
  ; =>
  ; "bc"
  ;
  ; @example
  ; (from-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (from-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (from-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-first-occurence "abcabc" "b")
  ;
  ; @example
  ; (to-first-occurence "abcabc" "b")
  ; =>
  ; "ab"
  ;
  ; @example
  ; (to-first-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-first-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (to-first-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of no occurence is found.
  ;   Default: false}
  ;
  ; @usage
  ; (to-last-occurence "abcabc" "b")
  ;
  ; @example
  ; (to-last-occurence "abcabc"  "b")
  ; =>
  ; "abcab"
  ;
  ; @example
  ; (to-last-occurence "abcabc" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (to-last-occurence "abcabc" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (to-last-occurence nil "b")
  ; =>
  ; nil
  ;
  ; @example
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
  ; @param (*) n
  ; @param (*) x
  ; @param (*) y
  ; @param (map)(opt) options
  ; {:case-sensitive? (boolean)(opt)
  ;   Default: true
  ;  :return? (boolean)(opt)
  ;   If TRUE, returns the given 'n' value in case of the occurences are not found.
  ;   Default: false}
  ;
  ; @usage
  ; (between-occurences "abcabc" "b" "a")
  ;
  ; @example
  ; (between-occurences "abcabc" "b" "a")
  ; =>
  ; "c"
  ;
  ; @example
  ; (between-occurences "abcabc" "b" "d")
  ; =>
  ; nil
  ;
  ; @example
  ; (between-occurences "abcabc" "b" "d" {:return? true})
  ; =>
  ; "abcabc"
  ;
  ; @example
  ; (between-occurences nil "b" "a")
  ; =>
  ; nil
  ;
  ; @example
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
