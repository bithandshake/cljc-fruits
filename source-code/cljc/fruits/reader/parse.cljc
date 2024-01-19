
(ns fruits.reader.parse
    #?(:clj  (:require [clojure.edn           :as edn]
                       [fruits.reader.prepare :as prepare])
       :cljs (:require [cljs.reader           :as reader]
                       [fruits.reader.prepare :as prepare])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-edn
  ; @note
  ; http://edn-format.org
  ;
  ; @description
  ; Parses the given 'n' EDN string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (parse-edn "")
  ; =>
  ; nil
  ;
  ; @usage
  ; (parse-edn ":foo")
  ; =>
  ; :foo
  ;
  ; @usage
  ; (parse-edn "{:foo :bar}")
  ; =>
  ; {:foo :bar}
  ;
  ; @usage
  ; (parse-edn "[:foo]")
  ; =>
  ; [:foo]
  ;
  ; @return (nil, keyword, map, number, seqable (e.g., string, vector, etc.))
  [n]
  (letfn [(parse-edn-f [%] #?(:cljs (try (-> % str reader/read-string) (catch :default  e (println e)))
                              :clj  (try (-> % str edn/read-string)    (catch Exception e (println e)))))]
         (let [output (-> n prepare/prepare-edn parse-edn-f)]
              (if (some #(% output) [boolean? keyword? map? number? seqable?]) ; <- Avoids returning error objects
                  (-> output)))))

(defn parse-json
  ; @description
  ; Parses the given 'n' JSON string.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (parse-json "{\"name\":\"value\"}")
  ; =>
  ; {"name" "value"}
  ;
  ; @usage
  ; (parse-json "{\"name\":[\"value\"]}")
  ; =>
  ; {"name" ["value"]}
  ;
  ; @return (map)
  [n]
  (-> n prepare/prepare-json parse-edn))
