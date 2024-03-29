
(ns fruits.reader.prepare
    (:require [clojure.string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn prepare-edn
  ; @description
  ; Removes object tags from the given 'n' EDN string to prevent the reader throwing errors of unknown objects.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (prepare-edn "{:my-object #object[...]}")
  ; =>
  ; "{:my-object :object-removed-by-reader}"
  ;
  ; @return (map)
  [n]
  (let [n (str n)]
       (letfn [; Removes the first '#object[...]' part from the given string.
               ; Returns NIL in case of no part has been found and removed!
               (remove-object-f [%] (if-let [open-position (clojure.string/index-of % "#object[")]
                                            (if-let [close-position (clojure.string/index-of % "]" open-position)]
                                                    (str (subs % 0 open-position)
                                                         ":object-removed-by-reader"
                                                         (subs % (inc close-position))))))

               ; Calls the 'remove-object-f' function recursivelly if an object has been removed (= the function returned the updated string).
               ; Returns the given string if the 'remove-object-f' returned NIL (= no part has been found and removed).
               (remove-objects-f [%] (if-let [% (remove-object-f %)]
                                             (-> % remove-objects-f)
                                             (-> %)))]

              ; The 'cljs.reader/read-string' function would throw the following error if the EDN contained an unknown object as tag.
              ; Therefore, this function removes the objects from the given EDN data.
              ; #error {:message No reader function for tag object., :data {:type :reader-exception, :ex-kind :reader-error}}
              (-> n remove-objects-f))))

(defn prepare-json
  ; @description
  ; Removes the delimiter colons from the given 'n' JSON string, to prevent the reader throwing errors.
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (prepare-json "{\"name\":\"value\"}")
  ; =>
  ; "{\"name\" \"value\"}"
  ;
  ; @usage
  ; (prepare-json "{\"name\":[\"value\"]}")
  ; =>
  ; "{\"name\" [\"value\"]}"
  ;
  ; @return (map)
  [n]
  ; https://ask.clojure.org/index.php/2366/accept-and-ignore-colon-between-key-and-value-in-map-literals
  ; In the JSON syntax there is a colon between names and values, but the Clojure reader
  ; cannot deal with that colon. Therefore, all delimiter colons have to be removed before reading a JSON data.
  (let [n (str n)]
       (letfn [(remove-delimiter-colons-f [%] (clojure.string/replace n #"(?<=\"[a-zA-Z0-9\-\_]+\")\:" " "))]
              (-> n remove-delimiter-colons-f))))
