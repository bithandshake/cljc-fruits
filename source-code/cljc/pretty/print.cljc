
(ns pretty.print
    (:require [loop.api   :refer [reduce-indexed reduce-kv-indexed]]
              [noop.api   :refer [return]]
              [string.api :as string]
              [vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn string->wrap?
  ; @param (string) n
  ;
  ; @example
  ; (string->wrap? "[:a :b :c]")
  ; =>
  ; false
  ;
  ; @return (boolean)
  [n]
  (string/min-length? n 60))

(defn mixed->wrap-items?
  ; @param (*) n
  ;
  ; @example
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
  ; @param (string) n
  ;
  ; @return (string)
  [n]
  (string/replace-part n #"\r\n}" "}"))

(defn key-tabs
  ; @param (integer) depth
  ;
  ; @example
  ; (key-tabs 2)
  ; =>
  ; "  "
  ;
  ; @return (string)
  [depth]
  (apply str (repeat depth string/TAB)))

(defn break
  ; @param (n) string
  ; @param (integer) depth
  ;
  ; @example
  ; (break "{:foo "bar"}" 2)
  ; =>
  ; "\r\n  "
  ;
  ; @return (string)
  [n depth]
  (let [key-tabs (key-tabs depth)]
       (str string/BREAK key-tabs n)))

(defn map-wrap
  ; @param (n) string
  ;
  ; @example
  ; (map-wrap ":foo []")
  ; =>
  ; "{:foo []}"
  ;
  ; @return (string)
  [n]
  (str "{" n "}"))

(defn vector-wrap
  ; @param (string) n
  ;
  ; @example
  ; (vector-wrap ":foo :bar")
  ; =>
  ; "[:foo :bar]"
  ;
  ; @return (string)
  [n]
  (str "[" n "]"))

(defn append-vector-v
  ; @param (string) o
  ; @param (string) v
  ; @param (map) options
  ; {:depth (integer)
  ;  :first-item? (boolean)
  ;  :wrap-items? (boolean)}
  ;
  ; @example
  ; TODO
  ;
  ; @return (string)
  [o v {:keys [depth first-item? wrap-items?]}]
  (let [v (if (and wrap-items? (not first-item?)) (break v (inc depth)) v)]
       (cond first-item? (str o v)
             :return     (str o string/TAB v))))

(defn append-map-kv
  ; @param (string) o
  ; @param (string) k
  ; @param (string) v
  ; @param (map) options
  ; {:depth (integer)
  ;  :first-item? (boolean)
  ;  :wrap-items? (boolean)}
  ;
  ; @example
  ; TODO
  ;
  ; @return (string)
  [o k v {:keys [depth first-item? wrap-items?]}]
  (let [k (if (and wrap-items? (not first-item?)) (break k (inc depth)) k)
        v (if (string->wrap? v) (break v (inc depth)) v)]
       (cond first-item? (str o k string/TAB v)
             :return     (str o string/TAB k string/TAB v))))

(defn fn>string
  ; @param (function) n
  ;
  ; @return (string)
  [n]
 ;(str "$FUNCTION")
  (str n))

(defn float->string
  ; @param (float) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn integer->string
  ; @param (integer) n
  ;
  ; @return (string)
  [n]
  (str n))

(defn nil->string
  ; @param (nil) n
  ;
  ; @return (string)
  [_]
  (str "nil"))

(defn string->string
  ; @param (string) n
  ;
  ; @return (string)
  [n]
  ; A (pr-str *) függvény használata biztosítja, hogy az n paraméterben levő
  ; escape-elt quote karakterek (\") megmaradjanak.
  ; Az (str *) függvény kiértékeli a n string tartalmát és eltávolítja az
  ; escape-eléshez szükséges visszaper karaktereket.
  ;
 ;(str "\"" n "\"")
  (pr-str n))

(defn var->string
  ; @param (symbol) n
  ;
  ; @return (string)
  [n]
 ;(str "$SYMBOL")
  (str n))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn mixed->string
  ; @description
  ; The <pre> HTML tag makes the output more human-readable.
  ;
  ; @param (*) n
  ; @param (map) options
  ; {:abc? (boolean)
  ;   Alphabetical ordered map items
  ;   Default: false}
  ;
  ; @usage
  ; (mixed->string {:a {:b "a/b"}})
  ;
  ; @usage
  ; [:pre (mixed->string {:a {:b "a/b"}})]
  ;
  ; @return (string)
  ([n]
   (mixed->string n {}))

  ([n {:keys [abc?]}]
   (letfn [(map->string
             ; @param (map) n
             ; @param (map) options
             ; {:depth (integer)
             ;  :wrap-items? (boolean)(opt)}
             ;
             ; @return (string)
             [n {:keys [depth wrap-items?]}]
             (letfn [(f [o dex k v]
                        (append-map-kv o (mixed->string k {:depth 0})
                                         (mixed->string v {:depth       (inc depth)
                                                           :wrap-items? (mixed->wrap-items? v)})
                                         {:depth       depth
                                          :first-item? (= dex 0)
                                          :wrap-items? wrap-items?}))]
                    (map-wrap (reduce-kv-indexed f nil n))))

           (map->ordered-string
             ; @param (map) n
             ; @param (map) options
             ; {:depth (integer)
             ;  :wrap-items? (boolean)(opt)}
             ;
             ; @return (string)
             [n {:keys [depth wrap-items?]}]
             (let [ordered-keys (vector/abc-items (keys n))]
                  (letfn [(f [o dex k]
                             (let [v (get n k)]
                                  (append-map-kv o (mixed->string k {:depth 0})
                                                   (mixed->string v {:depth       (inc depth)
                                                                     :wrap-items? (mixed->wrap-items? v)})
                                                   {:depth       depth
                                                    :first-item? (= 0 dex)
                                                    :wrap-items? wrap-items?})))]
                         (map-wrap (reduce-indexed f nil ordered-keys)))))

           (vector->string
             ; @param (vector) n
             ; @param (map) options
             ; {:depth (integer)
             ;  :wrap-items? (boolean)(opt)}
             ;
             ; @return (string)
             [n {:keys [depth wrap-items?]}]
             (letfn [(f [o dex x]
                        (append-vector-v o (mixed->string x {:depth       (inc depth)
                                                             :wrap-items? (mixed->wrap-items? x)})
                                           {:depth       depth
                                            :first-item? (= dex 0)
                                            :wrap-items? wrap-items?}))]
                    (vector-wrap (reduce-indexed f nil n))))

           (mixed->string
             ; @param (*) n
             ; @param (map) options
             ; {:depth (integer)
             ;  :wrap-items? (boolean)(opt)}
             ;
             ; @return (string)
             [n {:keys [depth wrap-items?] :as options}]
             (cond (and (map? n) abc?) (map->ordered-string n options)
                   (map?     n)        (map->string         n options)
                   (vector?  n)        (vector->string      n options)
                   (fn?      n)        (fn>string           n)
                   (float?   n)        (float->string       n)
                   (integer? n)        (integer->string     n)
                   (nil?     n)        (nil->string         n)
                   (string?  n)        (string->string      n)
                   (var?     n)        (var->string         n)
                   :return             (str                 n)))]

         ; mixed->string
         (remove-unnecessary-breaks (mixed->string n {:depth 0 :wrap-items? (mixed->wrap-items? n)})))))
