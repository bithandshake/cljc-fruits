
(ns reader.prepare
    (:require [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prepare-edn
  ; @param (string) n
  ;
  ; @usage
  ; (prepare-edn "{:my-object #object[...]}")
  ;
  ; @example
  ; (prepare-edn "{:my-object #object[...]}")
  ; =>
  ; "{:my-object :object-removed-by-reader}"
  ;
  ; @return (map)
  [n]
  (letfn [; Removes the first '#object[...]' part of the given string.
          ; Returns NIL in case of no part has been removed!
          (remove-object-f [%] (if-let [open-pos (string/first-dex-of % "#object[")]
                                       (if-let [close-pos (string/first-dex-of % "]" open-pos)]
                                               (str (string/keep-range % 0 open-pos)
                                                    ":object-removed-by-reader"
                                                    (string/keep-range % (inc close-pos))))))

          ; Calls the 'remove-object-f' function recursivelly if an object has been removed (= the function returned the updated string).
          ; Returns the given string if the 'remove-object-f' returned NIL (= no part has been removed).
          (remove-objects-f [%] (if-let [% (remove-object-f %)]
                                        (-> % remove-objects-f)
                                        (-> %)))]

         ; The 'cljs.reader/read-string' function would throw the following error if the EDN contains an unknown object as tag,
         ; therefore this function removes objects from the given EDN.
         ; #error {:message No reader function for tag object., :data {:type :reader-exception, :ex-kind :reader-error}}
         (-> n remove-objects-f)))

(defn prepare-json
  ; @param (string) n
  ;
  ; @usage
  ; (prepare-json "{\"name\":\"value\"}")
  ;
  ; @example
  ; (prepare-json "{\"name\":\"value\"}")
  ; =>
  ; "{\"name\" \"value\"}"
  ;
  ; @example
  ; (prepare-json "{\"name\":[\"value\"]}")
  ; =>
  ; "{\"name\" [\"value\"]}"
  ;
  ; @return (map)
  [n]
  ; https://ask.clojure.org/index.php/2366/accept-and-ignore-colon-between-key-and-value-in-map-literals
  ; In JSON syntax there is a colon between names and values, but the Clojure reader
  ; cannot deal with that colon, therefore all delimiter colons have to be removed before reading a JSON data.
  (letfn [(remove-delimiter-colons-f [%] (string/replace-part % #"(?<=\"[a-zA-Z0-9\-\_]+\")\:" " "))]
         (-> n remove-delimiter-colons-f)))
