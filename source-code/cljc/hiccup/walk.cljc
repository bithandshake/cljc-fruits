
(ns hiccup.walk
    (:require [hiccup.type :as type]
              [random.api  :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @param (hiccup) n
  ; @param (function) f
  ;
  ; @example
  ; (walk [:td [:p {:style {:color "red"}}]]
  ;       #(conj % "420"))
  ; =>
  ; [:td [:p {:style {:color "red"}} "420"] "420"]
  ;
  ; @return (hiccup)
  [n f]
  (if (type/hiccup? n)
      (letfn [(walk-f [%1 %2] (conj %1 (walk %2 f)))]
             (reduce walk-f [] (f n)))
      (-> n)))

(defn explode
  ; @param (hiccup)(opt) container
  ; Default: [:div]
  ; @param (string) n
  ;
  ; @example
  ; (explode [:div] "ab")
  ; =>
  ; [:div [:span "a"]
  ;       [:span "b"]
  ;
  ; @return (hiccup)
  ([n]
   (explode [:div] n))

  ([container n]
   (and (string? n)
        (type/hiccup? container)
        (letfn [(f [%1 %2] (conj %1 ^{:key (random/generate-uuid)} [:span %2]))]
               (reduce f container n)))))

(defn put
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (collection) n
  ;
  ; @usage
  ; (put [[:span "A"] [:span "B"]])
  ;
  ; @usage
  ; (put [:ul] [[:li "A"] [:li "B"]])
  ;
  ; @example
  ; (put [[:span "A"] [:span "B"]])
  ; =>
  ; [:div [:span "A"] [:span "B"]]
  ;
  ; @example
  ; (put [:ul] [[:li "A"] [:li "B"]])
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @return (hiccup)
  ([n]
   (put [:div] n))

  ([container n]
   (and (seqable? n)
        (type/hiccup? container)
        (letfn [(f [%1 %2] (conj %1 ^{:key (random/generate-uuid)} %2))]
               (reduce f container n)))))

(defn put-with
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (collection) n
  ; @param (function) put-f
  ;
  ; @usage
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [[:span "A"] [:span "B"]] my-put-f)
  ;
  ; @usage
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ;
  ; @example
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [[:span "A"] [:span "B"]] my-put-f)
  ; =>
  ; [:div [:span "A" "X"] [:span "B" "X"]]
  ;
  ; @example
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ; =>
  ; [:ul [:li "A" "X"] [:li "B" "X"]]
  ;
  ; @return (hiccup)
  ([n f]
   (put-with [:div] n f))

  ([container n put-f]
   (and (fn? put-f)
        (seqable? n)
        (type/hiccup? container)
        (letfn [(f [%1 %2] (if %2 (conj %1 ^{:key (random/generate-uuid)} (put-f %2))
                                  (->   %1)))]
               (reduce f container n)))))

(defn put-with-indexed
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (collection) n
  ; @param (function) put-f
  ;
  ; @usage
  ; (defn my-put-f [dex %] (conj % "X"))
  ; (put-with-indexed [[:span "A"] [:span "B"]] my-put-f)
  ;
  ; @usage
  ; (defn my-put-f [dex %] (conj % "X"))
  ; (put-with-indexed [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ;
  ; @example
  ; (defn my-put-f [dex %] (conj % dex "X"))
  ; (put-with-indexed [[:span "A"] [:span "B"]] my-put-f)
  ; =>
  ; [:div [:span "A" 0 "X"] [:span "B" 1 "X"]]
  ;
  ; @example
  ; (defn my-put-f [dex %] (conj % dex "X"))
  ; (put-with-indexed [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ; =>
  ; [:ul [:li "A" 0 "X"] [:li "B" 1 "X"]]
  ;
  ; @return (hiccup)
  ([n f]
   (put-with-indexed [:div] n f))

  ([container n put-f]
   (and (fn? put-f)
        (seqable? n)
        (type/hiccup? container)
        (letfn [(f [%1 %2 %3] (if %3 (conj %1 ^{:key (random/generate-uuid)} (put-f %2 %3))
                                     (->   %1)))]
               (reduce-kv f container n)))))
