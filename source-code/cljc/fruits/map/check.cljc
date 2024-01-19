
(ns fruits.map.check
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
  ; =>
  ; false
  ;
  ; @usage
  ; (empty? {:a "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (empty? [:a])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n clojure.core/empty?)))

(defn not-empty?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonempty map.
  ;
  ; @param (map) n
  ;
  ; @usage
  ; (not-empty? {})
  ; =>
  ; false
  ;
  ; @usage
  ; (not-empty? {:a "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (not-empty? [:a])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n clojure.core/empty? not)))

(defn namespaced?
  ; @description
  ; Returns TRUE if the given 'n' value is a namespaced map.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (namespaced? {:a/b "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (namespaced? {:a "A"})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n namespace some?)))

(defn not-namespaced?
  ; @description
  ; Returns TRUE if the given 'n' value is a nonnamespaced map.
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (not-namespaced? {:a "A"})
  ; =>
  ; true
  ;
  ; @usage
  ; (not-namespaced? {:a/b "A"})
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n map?)
       (-> n namespace nil?)))
