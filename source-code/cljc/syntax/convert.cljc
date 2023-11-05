
(ns syntax.convert
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-snake-case
  ; @description
  ; Converts the given 'n' string from CamelCase to snake-case.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (to-snake-case "SnakeCase")
  ; =>
  ; "snake-case"
  ;
  ; @return (string)
  [n]
  (letfn [
          ; Returns TRUE if the observed character follows a hyphen.
          (f0 [result cursor] (= "-" (subs result (dec cursor) cursor)))

          ; Returns TRUE if the observed character follows a whitespace.
          (f1 [result cursor] (= " " (subs result (dec cursor) cursor)))

          ; - Updates the observed character if uppercase.
          ; - Places a hyphen before the updated character if it does not follow a whitespace or a hyphen.
          ; - The #"[A-Z]" pattern only matches with uppercase LETTERS!
          (f2 [result cursor]
              (let [char (subs result cursor (inc cursor))]
                   (if (re-find #"[A-Z]" char)
                       (cond (= cursor 0)       (str                            (string/to-lowercase char) (subs result (inc cursor)))
                             (f0 result cursor) (str (subs result 0 cursor)     (string/to-lowercase char) (subs result (inc cursor)))
                             (f1 result cursor) (str (subs result 0 cursor)     (string/to-lowercase char) (subs result (inc cursor)))
                             :else              (str (subs result 0 cursor) "-" (string/to-lowercase char) (subs result (inc cursor))))
                       (-> result))))

          ; ...
          (f3 [result cursor]
              (if (= (count result) cursor)
                  (-> result)
                  (let [new-result (f2 result cursor)]
                       (f3 new-result (+ (inc cursor) (- (count new-result) (count result)))))))]

         ; ...
         (f3 n 0)))

(defn ToCamelCase
  ; @description
  ; Converts the given 'n' string from snake-case to CamelCase.
  ;
  ; @param (string) n
  ;
  ; @example
  ; (ToCamelCase "camel-case")
  ; =>
  ; "CamelCase"
  ;
  ; @return (string)
  [n]
  (letfn [
          ; Returns TRUE if the observed character follows a hyphen.
          (f0 [result cursor] (= "-" (subs result (dec cursor) cursor)))

          ; Returns TRUE if the observed character follows a whitespace.
          (f1 [result cursor] (= " " (subs result (dec cursor) cursor)))

          ; - Updates the observed character if lowercase.
          ; - Removes the hyphen before the updated character if any.
          ; - The #"[a-z]" pattern only matches with lowercase LETTERS!
          (f2 [result cursor]
              (let [char (subs result cursor (inc cursor))]
                   (if (re-find #"[a-z]" char)
                       (cond (= cursor 0)       (str                              (string/to-uppercase char) (subs result (inc cursor)))
                             (f0 result cursor) (str (subs result 0 (dec cursor)) (string/to-uppercase char) (subs result (inc cursor)))
                             (f1 result cursor) (str (subs result 0 cursor)       (string/to-uppercase char) (subs result (inc cursor)))
                             :return result)
                       (-> result))))

          ; ...
          (f3 [result cursor]
              (if (= (count result) cursor)
                  (-> result)
                  (let [new-result (f2 result cursor)]
                       (f3 new-result (+ (inc cursor) (- (count new-result) (count result)))))))]

         ; ...
         (f3 n 0)))
