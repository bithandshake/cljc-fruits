
(ns fruits.pretty.utils
    (:require [fruits.string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn string->wrap?
  ; @ignore
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (string->wrap? "[:a :b :c]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (string/length-min? n 60))

(defn mixed->wrap-items?
  ; @ignore
  ;
  ; @param (*) n
  ;
  ; @usage
  ; (mixed->wrap-items? [:a :b :c])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (and (-> n str string->wrap?)
       (or (map?    n)
           (vector? n))))

(defn remove-unnecessary-breaks
  ; @ignore
  ;
  ; @param (string) n
  ;
  ; @return (string)
  [n]
  (string/replace-part n #"\r\n\}" "}"))

(defn key-tabs
  ; @ignore
  ;
  ; @param (integer) depth
  ;
  ; @usage
  ; (key-tabs 2)
  ; =>
  ; "  "
  ;
  ; @return (string)
  [depth]
  (apply str (repeat depth string/WHITE-SPACE)))

(defn break
  ; @ignore
  ;
  ; @param (n) string
  ; @param (integer) depth
  ;
  ; @usage
  ; (break "{:foo "bar"}" 2)
  ; =>
  ; "\r\n  "
  ;
  ; @return (string)
  [n depth]
  (let [key-tabs (key-tabs depth)]
       (str string/BREAK key-tabs n)))

(defn map-wrap
  ; @ignore
  ;
  ; @param (n) string
  ;
  ; @usage
  ; (map-wrap ":foo []")
  ; =>
  ; "{:foo []}"
  ;
  ; @return (string)
  [n]
  (str "{"n"}"))

(defn vector-wrap
  ; @ignore
  ;
  ; @param (string) n
  ;
  ; @usage
  ; (vector-wrap ":foo :bar")
  ; =>
  ; "[:foo :bar]"
  ;
  ; @return (string)
  [n]
  (str "["n"]"))

(defn append-vector-v
  ; @ignore
  ;
  ; @param (string) o
  ; @param (string) v
  ; @param (map) options
  ; {:depth (integer)
  ;  :first-item? (boolean)
  ;  :wrap-items? (boolean)}
  ;
  ; @usage
  ; TODO
  ;
  ; @return (string)
  [result v {:keys [depth first-item? wrap-items?]}]
  (let [v (if (and wrap-items? (not first-item?)) (break v (inc depth)) v)]
       (cond first-item? (str result v)
             :return     (str result string/WHITE-SPACE v))))

(defn append-map-kv
  ; @ignore
  ;
  ; @param (string) o
  ; @param (string) k
  ; @param (string) v
  ; @param (map) options
  ; {:depth (integer)
  ;  :first-item? (boolean)
  ;  :wrap-items? (boolean)}
  ;
  ; @usage
  ; TODO
  ;
  ; @return (string)
  [result k v {:keys [depth first-item? wrap-items?]}]
  (let [k (if (and wrap-items? (not first-item?)) (break k (inc depth)) k)
        v (if (string->wrap? v) (break v (inc depth)) v)]
       (cond first-item? (str result k string/WHITE-SPACE v)
             :return     (str result string/WHITE-SPACE k string/WHITE-SPACE v))))

(defn fn->string
  ; @ignore
  ;
  ; @param (function) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn float->string
  ; @ignore
  ;
  ; @param (float) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn integer->string
  ; @ignore
  ;
  ; @param (integer) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn nil->string
  ; @ignore
  ;
  ; @param (nil) n
  ;
  ; @return (string)
  [_]
  (str "nil"))

(defn string->string
  ; @ignore
  ;
  ; @param (string) n
  ;
  ; @return (string)
  [n]
  ; The use of the '(pr-str ...)' function ensures that escape-quoted characters (\") in the 'n' parameter remain intact.
  ; The '(str ...)' function evaluates the content of the 'n' string and removes the backslash characters necessary for escaping.
  ; (str "\"" n "\"")
  (pr-str n))

(defn var->string
  ; @ignore
  ;
  ; @param (var) n
  ;
  ; @return (string)
  [n]
  (str n))
