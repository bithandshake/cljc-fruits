
(ns fruits.string.order)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abc?
  ; @description
  ; Returns TRUE if the given 'a' and 'b' strings are passed in alphabetical order.
  ;
  ; @param (string) a
  ; @param (string) b
  ;
  ; @usage
  ; (abc? "abc" "def")
  ; =>
  ; true
  ;
  ; @usage
  ; (abc? "abc" "abc")
  ; =>
  ; true
  ;
  ; @usage
  ; (abc? 10 12)
  ; =>
  ; true
  ;
  ; @usage
  ; (abc? "" "abc")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [a b]
  (let [a (str a)
        b (str b)]
       (>= 0 (compare a b))))

(defn abc
  ; @description
  ; Takes the 'a' string and 'b' string and returns the one that is less than in alphabetical order.
  ;
  ; @param (string) a
  ; @param (string) b
  ;
  ; @usage
  ; (abc "abc" "def")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (abc "def" "abc")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (abc "abc" "abc")
  ; =>
  ; "abc"
  ;
  ; @usage
  ; (abc 10 12)
  ; =>
  ; "10"
  ;
  ; @usage
  ; (abc "" "abc")
  ; =>
  ; ""
  ;
  ; @return (string)
  [a b]
  (let [a (str a)
        b (str b)]
       (if (abc? a b)
           (-> a)
           (-> b))))
