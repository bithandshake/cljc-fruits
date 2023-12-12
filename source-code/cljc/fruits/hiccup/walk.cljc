
(ns fruits.hiccup.walk
    (:require [fruits.random.api :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @param (hiccup) n
  ; @param (function) f
  ;
  ; @usage
  ; (walk [:td [:p {:style {:color "red"}}]]
  ;       #(conj % "420"))
  ;
  ; @example
  ; (walk [:td [:p {:style {:color "red"}}]]
  ;       #(conj % "420"))
  ; =>
  ; [:td [:p {:style {:color "red"}} "420"] "420"]
  ;
  ; @return (hiccup)
  [n f]
  (if (and (-> n vector?)
           (-> f fn?))
      (letfn [(f0 [result x]
                  (conj result (walk x f)))]
             (reduce f0 [] (f n)))
      (-> n)))

(defn explode
  ; @param (hiccup)(opt) container
  ; Default: [:div]
  ; @param (string) n
  ;
  ; @usage
  ; (explode [:div] "ab")
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
   (if (and (-> container vector?)
            (-> n         string?))
       (letfn [(f0 [container x]
                   (conj container ^{:key (random/generate-uuid)} [:span x]))]
              (reduce f0 container n)))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

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
   (if (and (-> container vector?)
            (-> n         seqable?))
       (letfn [(f0 [container x]
                   (conj container ^{:key (random/generate-uuid)} x))]
              (reduce f0 container (vec n))))))

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
   (if (and (-> container vector?)
            (-> n         seqable?)
            (-> put-f     fn?))
       (letfn [(f0 [container x]
                   (if x (conj container ^{:key (random/generate-uuid)} (put-f x))
                         (->   container)))]
              (reduce f0 container (vec n))))))

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
   (if (and (-> container vector?)
            (-> n         seqable?)
            (-> put-f     fn?))
       (letfn [(f0 [container dex x]
                   (if x (conj container ^{:key (random/generate-uuid)} (put-f dex x))
                         (->   container)))]
              (reduce-kv f0 container (vec n))))))
