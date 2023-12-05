
(ns reader.read
    #?(:clj  (:require [clojure.edn    :as edn]
                       [reader.prepare :as prepare]
                       [string.api     :as string])
       :cljs (:require [cljs.reader    :as reader]
                       [reader.prepare :as prepare]
                       [string.api     :as string])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn read-edn
  ; @description
  ; Reads one object from the given 'n' string.
  ; http://edn-format.org
  ;
  ; @param (string) n
  ;
  ; @example
  ; (read-edn "")
  ; =>
  ; nil
  ;
  ; @example
  ; (read-edn ":foo")
  ; =>
  ; :foo
  ;
  ; @example
  ; (read-edn "{:foo :bar}")
  ; =>
  ; {:foo :bar}
  ;
  ; @example
  ; (read-edn "[:foo]")
  ; =>
  ; [:foo]
  ;
  ; @return (nil, keyword, map, number, seqable (e.g., string, vector, etc.))
  [n]
  (letfn [(read-edn-f [%] #?(:cljs (try (-> % str reader/read-string) (catch :default  e (println e)))
                             :clj  (try (-> % str edn/read-string)    (catch Exception e (println e)))))]
         (let [output (-> n prepare/prepare-edn read-edn-f)]
              (if (some #(% output) [boolean? keyword? map? number? seqable?]) ; <- Avoids returning error objects
                  (-> output)))))

(defn read-json
  ; @param (string) n
  ;
  ; @usage
  ; (read-json "{\"name\":\"value\"}")
  ;
  ; @example
  ; (read-json "{\"name\":\"value\"}")
  ; =>
  ; {"name" "value"}
  ;
  ; @example
  ; (read-json "{\"name\":[\"value\"]}")
  ; =>
  ; {"name" ["value"]}
  ;
  ; @return (map)
  [n]
  (-> n prepare/prepare-json read-edn))
