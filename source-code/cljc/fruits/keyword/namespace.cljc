
(ns fruits.keyword.namespace
    (:require [fruits.random.api :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn add-namespace
  ; @description
  ; Adds the given namespace value to the given 'n' keyword.
  ;
  ; @param (keyword) n
  ; @param (keyword)(opt) namespace
  ;
  ; @usage
  ; (add-namespace :b :a)
  ; =>
  ; :a/b
  ;
  ; @usage
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
  ; @description
  ; Returns the namespace value of the given 'n' keyword.
  ;
  ; @param (keyword) n
  ;
  ; @usage
  ; (get-namespace :a)
  ; =>
  ; nil
  ;
  ; @usage
  ; (get-namespace :a/b)
  ; =>
  ; :a
  ;
  ; @return (keyword or nil)
  [n]
  (if (keyword? n)
      (if-let [namespace (namespace n)]
              (keyword namespace))))

(defn remove-namespace
  ; @description
  ; Removes the namespace value from the given 'n' keyword.
  ;
  ; @param (keyword) n
  ;
  ; @usage
  ; (remove-namespace :a/b)
  ; =>
  ; :b
  ;
  ; @return (keyword)
  [n]
  (-> n name keyword))
