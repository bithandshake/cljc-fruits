
(ns reader.core
    (:require #?(:cljs [cljs.reader :as reader])
              #?(:clj  [clojure.edn :as edn])
              [noop.api   :refer [return]]
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
  ; @param (string) n
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
               ; In case of the 'read-str' function returns an Error object it's
               ; important to return that object from this function as well.
               (return n)))))

(defn json->map
  ; @param (string) n
  ;
  ; @usage
  ; (json->map "{\"name\":\"value\"}")
  ;
  ; @example
  ; (json->map "{\"name\":\"value\"}")
  ; =>
  ; {"name" "value"}
  ;
  ; @example
  ; (json->map "{\"name\":[\"value\"]}")
  ; =>
  ; {"name" ["value"]}
  ;
  ; @return (map)
  [n]
  ; https://ask.clojure.org/index.php/2366/accept-and-ignore-colon-between-key-and-value-in-map-literals
  ; In JSON syntax there is a colon between names and values, but the Clojure reader
  ; cannot deal with that colon, therefore all delimiter colons has to be removed
  ; before reading a JSON data.
  (letfn [(remove-delimiter-colons-f [%] (string/replace-part % #"(?<=\"[a-zA-Z0-9\-\_]+\")\:" " "))]
         (-> n remove-delimiter-colons-f string->mixed)))

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
