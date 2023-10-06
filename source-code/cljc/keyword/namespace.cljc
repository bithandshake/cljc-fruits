
(ns keyword.namespace
    (:require [random.api :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add-namespace
  ; @param (keyword) n
  ; @param (keyword)(opt) namespace
  ;
  ; @usage
  ; (add-namespace :b :a)
  ;
  ; @example
  ; (add-namespace :b :a)
  ; =>
  ; :a/b
  ;
  ; @example
  ; (add-namespace :a)
  ; =>
  ; :ko4983l3-i8790-j93l3-lk8385u591o2/a
  ;
  ; @return (keyword)
  ([n]
   (keyword (random/generate-uuid) (name n)))

  ([n namespace]
   (keyword (name namespace) (name n))))

(defn get-namespace
  ; @param (keyword) n
  ;
  ; @usage
  ; (get-namespace :a/b)
  ;
  ; @example
  ; (get-namespace :a)
  ; =>
  ; nil
  ;
  ; @example
  ; (get-namespace :a/b)
  ; =>
  ; :a
  ;
  ; @return (keyword or nil)
  [n]
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (keyword namespace))))
