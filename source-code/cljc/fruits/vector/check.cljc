
(ns fruits.vector.check
    (:refer-clojure :exclude [empty?]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty?
  ; @description
  ; Returns TRUE if the given 'n' value is an empty vector.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (empty? [])
  ; =>
  ; true
  ;
  ; @usage
  ; (empty? [:a])
  ; =>
  ; false
  ;
  ; @usage
  ; (empty? {})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n clojure.core/empty?)))

(defn not-empty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty vector.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-empty? [:a])
  ; =>
  ; true
  ;
  ; @usage
  ; (not-empty? [])
  ; =>
  ; false
  ;
  ; @usage
  ; (not-empty? {:a "A"})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n vector?)
       (-> n clojure.core/empty? not)))
