
(ns fruits.string.set
    (:refer-clojure :exclude [repeat])
    (:require [clojure.string]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn repeat
  ; @description
  ; Returns the given 'x' string repeated as many times as the given 'x' value.
  ;
  ; @param (string) n
  ; @param (integer) x
  ;
  ; @usage
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
  ; @description
  ; Joins the items of the given 'n' collection into a string.
  ;
  ; @param (seqable) n
  ; @param (string)(opt) separator
  ; @param (map)(opt) options
  ; {:join-empty? (boolean)(opt)
  ;   Default: true}
  ;
  ; @usage
  ; (join ["my-image" "png"] ".")
  ; =>
  ; "my-image.png"
  ;
  ; @usage
  ; (join ["a" "b" ""] ".")
  ; =>
  ; "a.b."
  ;
  ; @usage
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
  ; @description
  ; Splits the given 'n' string into a vector of chunks by the given delimiter value.
  ;
  ; @param (string) n
  ; @param (clj: regex, cljs: regex or string) delimiter
  ;
  ; @usage
  ; (split "a.b.c" #"\.")
  ; =>
  ; ["a" "b" "c"]
  ;
  ; @usage
  ; (split ".b.c" #"\.")
  ; =>
  ; ["" "b" "c"]
  ;
  ; @usage
  ; (split "a.b.c" #"\_")
  ; =>
  ; ["a.b.c"]
  ;
  ; @usage
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
