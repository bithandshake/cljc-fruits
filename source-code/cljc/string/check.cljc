
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

(defn length-min?
  ; @param (*) n
  ; @param (integer) min
  ;
  ; @usage
  ; (length-min? "abc" 3)
  ;
  ; @example
  ; (length-min? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length-min? "abc" 4)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n min]
  (and (-> min integer?)
       (<= min (-> n str count))))

(defn length-max?
  ; @param (*) n
  ; @param (integer) max
  ;
  ; @usage
  ; (length-max? "abc" 3)
  ;
  ; @example
  ; (length-max? "abc" 3)
  ; =>
  ; true
  ;
  ; @example
  ; (length-max? "abc" 2)
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n max]
  (and (-> max integer?)
       (>= max (-> n str count))))

(defn length-between?
  ; @param (*) n
  ; @param (integer) min
  ; @param (integer) max
  ;
  ; @example
  ; (length-between? "abc" 3 4)
  ; =>
  ; true
  ;
  ; @example
  ; (length-between? "abc" 2 4)
  ; =>
  ; true
  ;
  ; @return (boolean)
  [n min max]
  (let [n (str n)]
       (and (<= min (count n))
            (>= max (count n)))))

(defn length?
  ; @param (*) n
  ; @param (integer) length
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
  ; @return (boolean)
  [n length]
  (= length (-> n str count)))

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

(defn contains-digit?
  ; @param (*) n
  ;
  ; @usage
  ; (contains-digit? "abc1")
  ;
  ; @example
  ; (contains-digit? "abc1")
  ; =>
  ; true
  ;
  ; @example
  ; (contains-digit? "abc")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (some? (re-find #"\d" (str n))))

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

(defn if-contains-part
  ; @param (*) n
  ; @param (*) x
  ;
  ; @usage
  ; (if-contains-part "abc" "ab")
  ;
  ; @example
  ; (if-contains-part "abc" "ab")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (if-contains-part "abc" "cd")
  ; =>
  ; nil
  ;
  ; @example
  ; (if-contains-part "abc" "")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (if-contains-part "abc" nil)
  ; =>
  ; "abc"
  ;
  ; @example
  ; (if-contains-part [:a] "[:")
  ; =>
  ; "[:a]"
  ;
  ; @return (string)
  [n x]
  (if (clojure.string/includes? (str n)
                                (str x))
      (str n)))
