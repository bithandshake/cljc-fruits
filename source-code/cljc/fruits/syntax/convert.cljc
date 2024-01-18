
(ns fruits.syntax.convert
    (:require [fruits.string.api :as string]
              [fruits.seqable.api :as seqable]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-kebab-case
  ; @description
  ; Converts the given 'n' string to kebab-case.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (to-kebab-case "MyValue")
  ; =>
  ; "my-value"
  ;
  ; @usage
  ; (to-kebab-case "my_value")
  ; =>
  ; "my-value"
  ;
  ; @return (string)
  [n]
  (letfn [; Returns TRUE if the observed character preceded by a hyphen.
          (f0 [result dex] (= "-" (subs result (dec dex) dex)))

          ; Returns TRUE if the observed character is a whitespace.
          (f1 [result dex] (= " " (subs result dex (inc dex))))

          ; Returns TRUE if the observed character is a dash.
          (f2 [result dex] (= "_" (subs result dex (inc dex))))

          ; Returns TRUE if the observer character is an uppercase LETTER.
          (f3 [result dex] (->> (subs result dex) first str (re-find #"[A-Z]")))

          ; Returns the observed character converted to lowercase.
          (f4 [result dex] (-> (subs result dex) first string/to-lowercase))

          ; Returns the part preceding the observed character.
          (f5 [result dex] (subs result 0 dex))

          ; Returns the part following the observed character.
          (f6 [result dex] (subs result (inc dex)))

          ; Updates the observed character.
          (f7 [result dex]
              (cond (f1 result dex) (cond (-> dex zero?)  (str                                     (f6 result dex))  ; Whitespace, first index.
                                          (f0 result dex) (str (f5 result dex)                     (f6 result dex))  ; Whitespace, preceded by a hyphen.
                                          :else           (str (f5 result dex) "-"                 (f6 result dex))) ; Whitespace, not preceded by a hyphen.
                    (f2 result dex) (cond (-> dex zero?)  (str                 "-"                 (f6 result dex))  ; Dash, first index.
                                          (f0 result dex) (str (f5 result dex)                     (f6 result dex))  ; Dash, preceded by a hyphen.
                                          :else           (str (f5 result dex) "-"                 (f6 result dex))) ; Dash, not preceded by a hyphen.
                    (f3 result dex) (cond (-> dex zero?)  (str                     (f4 result dex) (f6 result dex))  ; Uppercase character, first index.
                                          (f0 result dex) (str (f5 result dex)     (f4 result dex) (f6 result dex))  ; Uppercase character, preceded by a hyphen.
                                          :else           (str (f5 result dex) "-" (f4 result dex) (f6 result dex))) ; Uppercase character, not preceded by a hyphen.
                    :return result))]

         ; Iterates over the given 'n' string and updates each character (if necessary).
         (loop [result (str n) dex 0]
               (if (seqable/dex-out-of-bounds? n dex)
                   (-> result)
                   (let [updated-result (f7 result dex)
                         next-dex (+ (inc dex) (seqable/count-difference updated-result result))]
                        (recur updated-result next-dex))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-snake_case
  ; @description
  ; Converts the given 'n' string to snake_case.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (to-snake_case "MyValue")
  ; =>
  ; "my_value"
  ;
  ; @usage
  ; (to-snake_case "my-value")
  ; =>
  ; "my_value"
  ;
  ; @return (string)
  [n]
  (letfn [; Returns TRUE if the observed character preceded by a dash.
          (f0 [result dex] (= "_" (subs result (dec dex) dex)))

          ; Returns TRUE if the observed character is a whitespace.
          (f1 [result dex] (= " " (subs result dex (inc dex))))

          ; Returns TRUE if the observed character is a hyphen.
          (f2 [result dex] (= "-" (subs result dex (inc dex))))

          ; Returns TRUE if the observer character is an uppercase LETTER.
          (f3 [result dex] (->> (subs result dex) first str (re-find #"[A-Z]")))

          ; Returns the observed character converted to lowercase.
          (f4 [result dex] (-> (subs result dex) first string/to-lowercase))

          ; Returns the part preceding the observed character.
          (f5 [result dex] (subs result 0 dex))

          ; Returns the part following the observed character.
          (f6 [result dex] (subs result (inc dex)))

          ; Updates the observed character.
          (f7 [result dex]
              (cond (f1 result dex) (cond (-> dex zero?)  (str                                     (f6 result dex))  ; Whitespace, first index.
                                          (f0 result dex) (str (f5 result dex)                     (f6 result dex))  ; Whitespace, preceded by a dash.
                                          :else           (str (f5 result dex) "_"                 (f6 result dex))) ; Whitespace, not preceded by a dash.
                    (f2 result dex) (cond (-> dex zero?)  (str                 "_"                 (f6 result dex))  ; Hyphen, first index.
                                          (f0 result dex) (str (f5 result dex)                     (f6 result dex))  ; Hyphen, preceded by a dash.
                                          :else           (str (f5 result dex) "_"                 (f6 result dex))) ; Hyphen, not preceded by a dash.
                    (f3 result dex) (cond (-> dex zero?)  (str                     (f4 result dex) (f6 result dex))  ; Uppercase character, first index.
                                          (f0 result dex) (str (f5 result dex)     (f4 result dex) (f6 result dex))  ; Uppercase character, preceded by a dash.
                                          :else           (str (f5 result dex) "_" (f4 result dex) (f6 result dex))) ; Uppercase character no preceded by a dash.
                    :return result))]

         ; Iterates over the given 'n' string and updates each character (if necessary).
         (loop [result (str n) dex 0]
               (if (seqable/dex-out-of-bounds? n dex)
                   (-> result)
                   (let [updated-result (f7 result dex)
                         next-dex (+ (inc dex) (seqable/count-difference updated-result result))]
                        (recur updated-result next-dex))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-CamelCase
  ; @description
  ; Converts the given 'n' string to CamelCase.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (to-CamelCase "my-value")
  ; =>
  ; "MyValue"
  ;
  ; @return (string)
  [n]
  (letfn [; Returns TRUE if the observed character is a hyphen.
          (f0 [result dex] (= "-" (subs result dex (inc dex))))

          ; Returns TRUE if the observed character is a whitespace.
          (f1 [result dex] (= " " (subs result dex (inc dex))))

          ; Returns TRUE if the observed character is a dash.
          (f2 [result dex] (= "_" (subs result dex (inc dex))))

          ; Returns TRUE if the observer character is a lowercase LETTER.
          (f3 [result dex] (->> (subs result dex) first str (re-find #"[a-z]")))

          ; Returns TRUE if the observed character followed by a lowercase LETTER.
          (f4 [result dex] (->> (subs result dex) second str (re-find #"[a-z]")))

          ; Returns the observed character converted to uppercase.
          (f5 [result dex] (-> (subs result dex) first string/to-uppercase))

          ; Returns the part preceding the observed character.
          (f6 [result dex] (subs result 0 dex))

          ; Returns the part following the observed character.
          (f7 [result dex] (subs result (inc dex)))

          ; Returns the part following the observed character capitalized.
          (f8 [result dex] (-> (subs result (inc dex)) string/to-capitalized))

          ; - Updates the observed character if lowercase.
          ; - Removes the hyphen before the updated character if any.
          ; - The #"[a-z]" pattern matches only lowercase LETTERS!
          (f9 [result dex]
              (cond (f0 result dex) (cond (-> dex zero?)  (str                                 (f7 result dex))  ; Hyphen, first index.
                                          (f4 result dex) (str (f6 result dex)                 (f8 result dex))  ; Hyphen, followed by a lowercase LETTER.
                                          :else           (str (f6 result dex)                 (f7 result dex))) ; Hyphen, not followed by a lowercase LETTER.
                    (f1 result dex) (cond (-> dex zero?)  (str                                 (f7 result dex))  ; Whitespace, first index.
                                          (f4 result dex) (str (f6 result dex)                 (f8 result dex))  ; Whitespace, followed by a lowercase LETTER.
                                          :else           (str (f6 result dex)                 (f7 result dex))) ; Whitespace, not followed by a lowercase LETTER.
                    (f2 result dex) (cond (-> dex zero?)  (str                                 (f7 result dex))  ; Dash, first index.
                                          (f4 result dex) (str (f6 result dex)                 (f8 result dex))  ; Dash, followed by a lowercase LETTER.
                                          :else           (str (f6 result dex)                 (f7 result dex))) ; Dash, not followed by a lowercase LETTER.
                    (f3 result dex) (cond (-> dex zero?)  (str                 (f5 result dex) (f7 result dex))  ; Lowercase character, first index.
                                          :return result)
                    :return result))]

         ; Iterates over the given 'n' string and updates each character if necessary.
         (loop [result (str n) dex 0]
               (if (seqable/dex-out-of-bounds? n dex)
                   (-> result)
                   (let [updated-result (f9 result dex)
                         next-dex (+ (inc dex) (- (count updated-result) (count result)))]
                        (recur updated-result next-dex))))))
