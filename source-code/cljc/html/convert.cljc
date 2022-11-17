
(ns html.convert
    (:require [clojure.string]
              [clojure.walk]
              [goog.string]
             ;[hickory.core :as hc]
              [candy.api :refer [param return]]
              [reagent-hickory.hickory.core :as hc]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn string->tokens
  "Takes a string with syles and parses it into properties and value tokens"
  [n]
  {:pre  [(string? n)]
   :post [(even? (count %))]}
  (if-not (empty? n)
          (->> (clojure.string/split n #";")
               (mapcat #(clojure.string/split % #":"))
               (map clojure.string/trim))))

(defn tokens->map
  "Takes a seq of tokens with the properties (even) and their values (odd)
   and returns a map of {properties values}"
  [n]
  {:pre  [(even? (count n))]
   :post [(map? %)]}
  (zipmap (keep-indexed #(if (even? %1) %2) n)
          (keep-indexed #(if (odd?  %1) %2) n)))

(defn style->map
  "Takes an inline style attribute stirng and converts it to a React Style map"
  [n]
  (tokens->map (string->tokens n)))

(defn filter-angular
  "Remove ng-* angular tags from hiccup data structure"
  [n]
  (->> n (filter (fn [[key _]]
                     (not (goog.string/startsWith (name key) "ng-"))))
         (into {})))

(defn hiccup->sablono
  "Transforms a style inline attribute into a style map for React"
  [n]
  (clojure.walk/postwalk (fn [x] (if (vector?          x)
                                     (filterv identity x)
                                     (return           x)))
                         (clojure.walk/postwalk (fn [x] (if (and (string? x)
                                                                 (or (re-matches      #"\s+" x)
                                                                     (goog.string/startsWith x "<!--")))
                                                            (return x)
                                                            (if (map? x)
                                                                (filter-angular (if (contains? x :style)
                                                                                    (update-in x [:style] style->map)
                                                                                    (return    x)))
                                                                (return x))))
                                                (param n))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn to-hiccup
  ; @param (string) n
  ;
  ; @usage
  ;  (to-hiccup "<div")
  ;
  ; @example
  ;  (to-hiccup "<div>")
  ;  =>
  ;  [:div]
  ;
  ; @return (vector)
  [n]
  #?(:cljs (if (and (-> n string?)
                    (-> n  empty? not))
               (try (->> (hc/parse-fragment n)
                         (map #(-> % hc/as-hiccup hiccup->sablono))
                         (filter identity)
                         first)
                    (catch :default e (.log js/console e))))))
