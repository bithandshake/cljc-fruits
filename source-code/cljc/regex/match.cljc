
(ns regex.match
    (:require [seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn re-count
  ; @description
  ; Returns the match count of the given 'x' pattern in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-count "123" #"\d")
  ;
  ; @example
  ; (re-count "123" #"\d")
  ; =>
  ; 3
  ;
  ; @example
  ; (re-count "abc" #"\d")
  ; =>
  ; 0
  ;
  ; @return (integer)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) count)))

(defn re-first
  ; @description
  ; Returns the first match of the given 'x' pattern in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-first "123" #"\d")
  ;
  ; @example
  ; (re-first "123" #"\d")
  ; =>
  ; "1"
  ;
  ; @example
  ; (re-first "abc" #"\d")
  ; =>
  ; nil
  ;
  ; @return (map, string or vector)
  [n x]
  ; The 're-seq' function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) first)))

(defn re-last
  ; @description
  ; Returns the last match of the given 'x' pattern in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-last "123" #"\d")
  ;
  ; @example
  ; (re-last "123" #"\d")
  ; =>
  ; "3"
  ;
  ; @example
  ; (re-last "abc" #"\d")
  ; =>
  ; nil
  ;
  ; @return (map, string or vector)
  [n x]
  ; The 're-seq' function returns a ...
  ; ... sequence of strings if the pattern has no capturing groups.
  ; ... sequence of vectors if the pattern has one or more capturing groups.
  ; ... sequence of maps if the pattern has one ore more named capturing groups.
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-seq x) last)))

(defn re-match
  ; @description
  ; Returns the given 'n' value (converted to string) if any match of the given 'x' pattern is found.
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-match "123" #"\d{1,}")
  ;
  ; @example
  ; (re-match "123" #"^[\d]{1,}$")
  ; =>
  ; "123"
  ;
  ; @example
  ; (re-match "abc" #"^[\d]{1,}$")
  ; =>
  ; nil
  ;
  ; @return (string)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (if (->> n (re-find x))
           (->  n))))

(defn re-match?
  ; @description
  ; Returns TRUE if any matches found of the given 'x' pattern in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-match? "123" #"^[\d]{1,}$")
  ;
  ; @example
  ; (re-match? "123" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-match? "abc" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) some?)))

(defn re-mismatch?
  ; @description
  ; Returns TRUE if no matches found of the given 'x' pattern in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ;
  ; @usage
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ;
  ; @example
  ; (re-mismatch? "abc" #"^[\d]{1,}$")
  ; =>
  ; true
  ;
  ; @example
  ; (re-mismatch? "123" #"^[\d]{1,}$")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n x]
  (let [n (str n)
        x (re-pattern x)]
       (->> n (re-find x) nil?)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn normalize-bwd-shift
  ; @temp
  ;
  ; @description
  ; Normalizes the given 'shift' value (distance from a cursor position in a sequence).
  ;
  ; @param (seqable) n
  ; @param (integer) shift
  ; @param (integer) cursor
  ;
  ; @return (integer)
  [n shift cursor]
  (cond (-> shift integer? not) (-> 0)
        (-> shift (= -1))       (-> cursor)
        (-> shift (<  0))       (-> 0)
        (-> shift (> cursor))   (-> cursor)
        :return shift))

(defn normalize-fwd-shift
  ; @temp
  ;
  ; @description
  ; Normalizes the given 'shift' value (distance from a cursor position in a sequence).
  ;
  ; @param (seqable) n
  ; @param (integer) shift
  ; @param (integer) cursor
  ;
  ; @return (integer)
  [n shift cursor]
  (cond (-> shift integer? not)                (-> 0)
        (-> shift (= -1))                      (-> n count (- cursor))
        (-> shift (<  0))                      (-> 0)
        (-> shift (> (-> n count (- cursor)))) (-> n count (- cursor))
        :return shift))

(defn re-from
  ; @warning
  ; Do not use capturing groups in the given pattern, otherwise it generates multiple matches!
  ;
  ; @description
  ; Returns the first match of the given 'x' pattern if the match starts at the given 'cursor' position in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:max-lookbehind-length (integer)(opt)
  ;   Must be provided if the given 'x' pattern contains lookbehind assertion. Use '-1' for unlimited lookbehind length.
  ;   Default: 0}
  ;
  ; @usage
  ; (re-from "abc123" #"[/d]" 3)
  ;
  ; @example
  ; (re-from "abc123" #"[/d]" 3)
  ; =>
  ; "1"
  ;
  ; @example
  ; (re-from "abc123" #"[/d]" 4)
  ; =>
  ; "2"
  ;
  ; @example
  ; (re-from "abc123" #"[/d]" 2)
  ; =>
  ; nil
  ;
  ; @example
  ; (re-from "abc123" #"(?<=c)[\d]" 3)
  ; =>
  ; nil
  ;
  ; @example
  ; (re-from "abc123" #"(?<=c)[\d]" 3 {:max-lookbehind-length 1})
  ; =>
  ; "123"
  ;
  ; @return (string)
  ([n x cursor]
   (re-from n x cursor {}))

  ([n x cursor {:keys [max-lookbehind-length]}]
   (let [n                     (str n)
         x                     (re-pattern x)
         cursor                (seqable/normalize-cursor n cursor)
         max-lookbehind-length (normalize-bwd-shift n max-lookbehind-length cursor)]
        (loop [substring (subs n cursor) shift 0]
              (or (if-let [match (re-first substring x)]
                          (let [end-cursor (-> match count (+ cursor))]
                               (and (-> n (seqable/cursor-in-bounds? end-cursor))
                                    (-> n (subs cursor end-cursor)
                                          (= match))
                                    (-> match))))
                  (if-not (or (= shift cursor)
                              (= shift max-lookbehind-length))
                          (recur (subs n (- cursor (inc shift)))
                                 (inc shift))))))))

(defn re-to
  ; @warning
  ; Do not use capturing groups in the given pattern, otherwise it generates multiple matches!
  ;
  ; @description
  ; Returns the last match of the given 'x' pattern if the match ends at the given 'cursor' position in the given 'n' value (converted to string).
  ;
  ; @param (*) n
  ; @param (regex pattern or string) x
  ; @param (integer) cursor
  ; @param (map)(opt) options
  ; {:max-lookahead-length (integer)(opt)
  ;   Must be provided if the given 'x' pattern contains lookahead assertion. Use '-1' for unlimited lookahead length.
  ;   Default: 0}
  ;
  ; @usage
  ; (re-to "abc123" #"[a-z]" 3)
  ;
  ; @example
  ; (re-to "abc123" #"[a-z]" 3)
  ; =>
  ; "c"
  ;
  ; @example
  ; (re-to "abc123" #"[a-z]" 2)
  ; =>
  ; "b"
  ;
  ; @example
  ; (re-to "abc123" #"[a-z]" 4)
  ; =>
  ; nil
  ;
  ; @example
  ; (re-to "abc123" #"abc(?=\d)" 3)
  ; =>
  ; nil
  ;
  ; @example
  ; (re-to "abc123" #"abc(?=\d)" 3 {:max-lookahead-length 1})
  ; =>
  ; "abc"
  ;
  ; @return (string)
  ([n x cursor]
   (re-to n x cursor {}))

  ([n x cursor {:keys [max-lookahead-length]}]
   (let [n                    (str n)
         x                    (re-pattern x)
         cursor               (seqable/normalize-cursor n cursor)
         max-lookahead-length (normalize-fwd-shift n max-lookahead-length cursor)]
        (loop [substring (subs n 0 cursor) shift 0]
              (or (if-let [match (re-last substring x)]
                          (and (seqable/cursor-in-bounds? n (- cursor (count match)))
                               (=  match (subs            n (- cursor (count match)) cursor))
                               (-> match)))
                  (if-not (or (= shift (-> n count (- cursor)))
                              (= shift max-lookahead-length))
                          (recur (subs n 0 (+ cursor (inc shift)))
                                 (inc shift))))))))
