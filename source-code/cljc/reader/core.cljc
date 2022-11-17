
(ns reader.core
    (:require #?(:cljs [cljs.reader :as reader])
              #?(:clj  [clojure.edn :as edn])
              [candy.api  :refer [return]]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn read-str
  ; @param (string) n
  ;
  ; @example
  ; (read-str "{:a \"b\"}")
  ; =>
  ; {:a "b"}
  ;
  ; @return (*)
  [n]
  #?(:cljs (try (reader/read-string n) (catch :default  e (println e)))
     :clj  (try (edn/read-string    n) (catch Exception e (println e)))))

(defn mixed->string
  ; @param (*) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn string->mixed
  ; @param (n) string
  ;
  ; @example
  ; (string->mixed "")
  ; =>
  ; nil
  ;
  ; @example
  ; (string->mixed ":foo")
  ; =>
  ; :foo
  ;
  ; @example
  ; (string->mixed "{:foo :bar}")
  ; =>
  ; {:foo :bar}
  ;
  ; @example
  ; (string->mixed "[:foo]")
  ; =>
  ; [:foo]
  ;
  ; @return (nil, keyword, map, number, string or vector)
  [n]
  (if (string/nonblank? n)
      (let [x (read-str n)]
           (if (some #(% x) [keyword? map? vector? number?])
               (return x)
               ; Előfordulhat, hogy a read-str függvény egy Error objektummal tér vissza
               (return n)))))

(defn string->map
  ; @param (string) n
  ;
  ; @example
  ; (string->map "foo")
  ; =>
  ; {:0 "foo"}
  ;
  ; @example
  ; (string->map nil)
  ; =>
  ; {}
  ;
  ; @example
  ; (string->map "{:foo :bar}")
  ; =>
  ; {:foo :bar}
  ;
  ; @return (map)
  [n]
  (if-let [x (string->mixed n)]
          (cond (map? x) x
                (nil? n) {}
                :return  {:0 (str n)})
          (return {})))
