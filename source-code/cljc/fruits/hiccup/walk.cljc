
(ns fruits.hiccup.walk
    (:require [fruits.random.api :as random]
              [fruits.mixed.api :as mixed]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @description
  ; Iterates over the given 'n' HICCUP value and applies the given 'f' function each element.
  ;
  ; @param (hiccup) n
  ; @param (function) f
  ;
  ; @usage
  ; (walk [:td [:p {:style {:color "red"}}]]
  ;       #(conj % "420"))
  ; =>
  ; [:td [:p {:style {:color "red"}} "420"] "420"]
  ;
  ; @return (hiccup)
  [n f]
  (if (vector? n)
      (let [f (mixed/to-ifn f)]
           (letfn [(f0 [result x]
                       (conj result (walk x f)))]
                  (reduce f0 [] (f n))))
      (-> n)))

(defn explode
  ; @description
  ; Explodes the given 'n' collection to items wrapped with a '[:span]' tag into the given 'container' tag.
  ;
  ; @param (hiccup)(opt) container
  ; Default: [:div]
  ; @param (seqable) n
  ;
  ; @usage
  ; (explode [:div] "ab")
  ; =>
  ; [:div [:span "a"]
  ;       [:span "b"]
  ;
  ; @return (hiccup)
  ([n]
   (explode [:div] n))

  ([container n]
   (if (vector? container)
       (let [n (mixed/to-seqable n)]
            (letfn [(f0 [container x]
                        (conj container ^{:key (random/generate-uuid)} [:span x]))]
                   (reduce f0 container n))))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn put
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag.
  ;
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (seqable) n
  ;
  ; @usage
  ; (put [[:span "A"] [:span "B"]])
  ; =>
  ; [:div [:span "A"] [:span "B"]]
  ;
  ; @usage
  ; (put [:ul] [[:li "A"] [:li "B"]])
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @return (hiccup)
  ([n]
   (put [:div] n))

  ([container n]
   (if (vector? container)
       (let [n (mixed/to-seqable n)]
            (letfn [(f0 [container x]
                        (conj container ^{:key (random/generate-uuid)} x))]
                   (reduce f0 container (vec n)))))))

(defn put-with
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag while applying
  ; the given 'put-f' function on each item.
  ;
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (collection) n
  ; @param (function) put-f
  ;
  ; @usage
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [[:span "A"] [:span "B"]] my-put-f)
  ; =>
  ; [:div [:span "A" "X"] [:span "B" "X"]]
  ;
  ; @usage
  ; (defn my-put-f [%] (conj % "X"))
  ; (put-with [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ; =>
  ; [:ul [:li "A" "X"] [:li "B" "X"]]
  ;
  ; @return (hiccup)
  ([n f]
   (put-with [:div] n f))

  ([container n put-f]
   (if (vector? container)
       (let [n     (mixed/to-seqable n)
             put-f (mixed/to-ifn put-f)]
            (letfn [(f0 [container x]
                        (if x (conj container ^{:key (random/generate-uuid)} (put-f x))
                              (->   container)))]
                   (reduce f0 container (vec n)))))))

(defn put-with-indexed
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag while applying
  ; the given 'put-f' function on each item and providing the item index to the function.
  ;
  ; @param (keyword)(opt) container
  ; Default: [:div]
  ; @param (collection) n
  ; @param (function) put-f
  ;
  ; @usage
  ; (defn my-put-f [dex %] (conj % dex "X"))
  ; (put-with-indexed [[:span "A"] [:span "B"]] my-put-f)
  ; =>
  ; [:div [:span "A" 0 "X"] [:span "B" 1 "X"]]
  ;
  ; @usage
  ; (defn my-put-f [dex %] (conj % dex "X"))
  ; (put-with-indexed [:ul] [[:li "A"] [:li "B"]] my-put-f)
  ; =>
  ; [:ul [:li "A" 0 "X"] [:li "B" 1 "X"]]
  ;
  ; @return (hiccup)
  ([n f]
   (put-with-indexed [:div] n f))

  ([container n put-f]
   (if (vector? container)
       (let [n     (mixed/to-seqable n)
             put-f (mixed/to-ifn put-f)]
            (letfn [(f0 [container dex x]
                        (if x (conj container ^{:key (random/generate-uuid)} (put-f dex x))
                              (->   container)))]
                   (reduce-kv f0 container (vec n)))))))
