
(ns fruits.string.order)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn abc?
  ; @description
  ; Returns TRUE if the given 'a' and 'b' values (converted to string) are passed in alphabetical order.
  ;
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
  (let [a (str a)
        b (str b)]
       (>= 0 (compare a b))))

(defn abc
  ; @description
  ; Takes the value 'a' (converted to string) and value 'b' (converted to string)
  ; and returns the one that is less than in alphabetical order.
  ;
  ; @param (*) a
  ; @param (*) b
  ;
  ; @usage
  ; (abc "abc" "def")
  ;
  ; @example
  ; (abc "abc" "def")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc "def" "abc")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc "abc" "abc")
  ; =>
  ; "abc"
  ;
  ; @example
  ; (abc 10 12)
  ; =>
  ; "10"
  ;
  ; @example
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
