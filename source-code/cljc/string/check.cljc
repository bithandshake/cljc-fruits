
(ns string.check
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn blank?
  ; @param (*) n
  ;
  ; @usage
  ; (blank? "")
  ;
  ; @example
  ; (blank? "abc")
  ; =>
  ; false
  ;
  ; @example
  ; (blank? "")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (clojure.string/blank? n))

(defn nonblank?
  ; @param (*) n
  ;
  ; @usage
  ; (nonblank? "abc")
  ;
  ; @example
  ; (nonblank? "")
  ; =>
  ; false
  ;
  ; @example
  ; (nonblank? "abc")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n]
  (and (-> n string?)
       (-> n empty? not)))

(defn abc?
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (abc? "abc" "def")
  ;
  ; @example
  ; (abc? "abc" "def")
  ; =>
  ; true
  ;
  ; @example
  ; (abc? "abc" "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (abc? 10 12)
  ; =>
  ; true
  ;
  ; @example
  ; (abc? "" "abc")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [a b]
  (>= 0 (compare (str a)
                 (str b))))

(defn min-length?
  ; @param (*) n
  ; @param (integer) min
  ;
  ; @usage
  ; (min-length? "abc" 3)
  ;
  ; @example
  ; (min-length? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (min-length? "abc" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n min]
  (and (-> min integer?)
       (<= min (-> n str count))))

(defn max-length?
  ; @param (*) n
  ; @param (integer) max
  ;
  ; @usage
  ; (max-length? "abc" 3)
  ;
  ; @example
  ; (max-length? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (max-length? "abc" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n max]
  (and (-> max integer?)
       (>= max (-> n str count))))

(defn length?
  ; @param (*) n
  ; @param (integer) length / min
  ; @param (integer)(opt) max
  ;
  ; @example
  ; (length? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length? "abc" 2)
  ; =>
  ; false
  ;
  ; @example
  ; (length? "abc" 2 4)
  ; =>
  ; true
  ;
  ; @return (boolean)
  ([n length]
   (= length (-> n str count)))

  ([n min max]
   (let [n (str n)]
        (and (<= min (count n))
             (>= max (count n))))))

(defn contains-part?
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (contains-part? "abc" "ab")
  ;
  ; @example
  ; (contains-part? "abc" "ab")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-part? "abc" "cd")
  ; =>
  ; false
  ;
  ; @example
  ; (contains-part? "abc" "")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-part? "abc" nil)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n x]
  (clojure.string/includes? (str n)
                            (str x)))

(defn contains-lowercase-letter?
  ; @param (*) n
  ;
  ; @usage
  ; (contains-lowercase-letter? "abc")
  ;
  ; @example
  ; (contains-lowercase-letter? "abc")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-lowercase-letter? "ABC")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (not= (-> n str)
        (-> n str clojure.string/upper-case)))

(defn contains-uppercase-letter?
  ; @param (*) n
  ;
  ; @usage
  ; (contains-uppercase-letter? "ABC")
  ;
  ; @example
  ; (contains-uppercase-letter? "ABC")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-uppercase-letter? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (not= (-> n str)
        (-> n str clojure.string/lower-case)))
