
(ns map.check
    (:refer-clojure :exclude [empty?]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (empty? {})
  ;
  ; @example
  ; (empty? {})
  ; =>
  ; false
  ;
  ; @example
  ; (empty? {:a "A"})
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
  (and (-> n map?)
       (-> n clojure.core/empty?)))

(defn nonempty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (nonempty? {})
  ;
  ; @example
  ; (nonempty? {})
  ; =>
  ; false
  ;
  ; @example
  ; (nonempty? {:a "A"})
  ; =>
  ; true
  ;
  ; @example
  ; (nonempty? [:a])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n clojure.core/empty? not)))
