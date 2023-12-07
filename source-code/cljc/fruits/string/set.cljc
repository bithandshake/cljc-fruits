
(ns fruits.string.set
    (:refer-clojure :exclude [repeat])
    (:require [clojure.string]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn repeat
  ; @param (*) n
  ; @param (integer) x
  ;
  ; @usage
  ; (repeat "a" 3)
  ;
  ; @example
  ; (repeat "a" 3)
  ; =>
  ; "aaa"
  ;
  ; @return (string)
  [n x]
  (if (nat-int? x)
      (letfn [(f0 [result]
                  (if (= (-> n      count (* x))
                         (-> result count))
                      (-> result)
                      (-> result (str n) f0)))]
             (f0 ""))))

(defn join
  ; @param (collection) n
  ; @param (*)(opt) separator
  ; @param (map)(opt) options
  ; {:join-empty? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (join ["a" "b"] ".")
  ;
  ; @example
  ; (join ["my-image" "png"] ".")
  ; =>
  ; "my-image.png"
  ;
  ; @example
  ; (join ["a" "b" ""] ".")
  ; =>
  ; "a.b."
  ;
  ; @example
  ; (join ["a" "b" ""] "." {:join-empty? false})
  ; =>
  ; "a.b"
  ;
  ; @return (string)
  ([n]
   (join n nil {}))

  ([n separator]
   (join n separator {}))

  ([n separator {:keys [join-empty?] :or {join-empty? true}}]
   (letfn [(f0 [result dex]
               (cond (seqable/dex-out-of-bounds? n dex) (-> result)
                     (or join-empty? (-> (nth n dex) str empty? not))
                     (if (and (-> (seqable/dex-last? n dex) not)
                              (-> (nth n (inc dex)) str empty? not))
                         (f0 (str result (nth n dex) separator) (inc dex))
                         (f0 (str result (nth n dex))           (inc dex)))
                     :return (f0 result (inc dex))))]
          ; ...
          (if (seqable? n)
              (f0 "" 0)))))

(defn split
  ; @param (*) n
  ; @param (clj: regex, cljs: regex or string) delimiter
  ;
  ; @usage
  ; (split "a.b.c" #"\.")
  ;
  ; @example
  ; (split "a.b.c" #"\.")
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @example
  ; (split ".b.c" #"\.")
  ; =>
  ; ["" "b" "c"]
  ;
  ; @example
  ; (split "a.b.c" #"\_")
  ; =>
  ; ["a.b.c"]
  ;
  ; @example
  ; (split "" #"\.")
  ; =>
  ; []
  ;
  ; @return (strings in vector)
  [n delimiter]
  (let [n (str n)]
       (cond (-> n empty?)        []
             (-> delimiter some?) (clojure.string/split n delimiter)
             :return              [n])))
