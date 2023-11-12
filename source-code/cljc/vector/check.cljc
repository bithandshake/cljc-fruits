
(ns vector.check
    (:refer-clojure :exclude [empty?]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty?
  ; @param (*) n
  ;
  ; @usage
  ; (empty? [])
  ;
  ; @example
  ; (empty? [])
  ; =>
  ; true
  ;
  ; @example
  ; (empty? [:a])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n clojure.core/empty?)))

(defn nonempty?
  ; @param (*) n
  ;
  ; @usage
  ; (nonempty? [:a])
  ;
  ; @example
  ; (nonempty? [:a])
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? [])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n clojure.core/empty? not)))
