
(ns fruits.hiccup.put
    (:require [fruits.mixed.api  :as mixed]
              [fruits.random.api :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn put
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag.
  ;
  ; @param (keyword) container
  ; @param (seqable) n
  ;
  ; @usage
  ; (put [:ul] [[:li "A"] [:li "B"]])
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @return (hiccup)
  [container n]
  ; @note (#5060)
  (if (vector? container)
      (let [n (mixed/to-seqable n)]
           (letfn [(f0 [container x]
                       (conj container ^{:key (random/generate-uuid)} [:<> x]))]
                  (reduce f0 container (vec n))))))

(defn put-with
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag while applying
  ; the given 'put-f' function on each item.
  ;
  ; @param (keyword) container
  ; @param (collection) n
  ; @param (function) put-f
  ; @param (function)(opt) key-f
  ;
  ; @usage
  ; (put-with [:ul]
  ;           [{:content [:li "A"]}
  ;            {:content [:li "B"]}]
  ;           :content)
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @usage
  ; (put-with [:ul]
  ;           [{:key "my-react-key"      :content [:li "A"]}
  ;            {:key "another-react-key" :content [:li "B"]}]
  ;           :content
  ;           :key)
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @return (hiccup)
  ([container n put-f]
   (put-with container n put-f (fn [_] (random/generate-uuid))))

  ([container n put-f key-f]
   ; @note (#5060)
   (if (vector? container)
       (let [n     (mixed/to-seqable n)
             put-f (mixed/to-ifn put-f)
             key-f (mixed/to-ifn key-f)]
            (letfn [(f0 [container x]
                        (if x (conj container ^{:key (key-f x)} [:<> (put-f x)])
                              (->   container)))]
                   (reduce f0 container (vec n)))))))

(defn put-with-indexed
  ; @description
  ; Conjugates the items of the given 'n' collection to the given 'container' tag while applying
  ; the given 'put-f' function on each item and providing the item index to the function.
  ;
  ; @param (keyword) container
  ; @param (collection) n
  ; @param (function) put-f
  ; @param (function)(opt) key-f
  ;
  ; @usage
  ; (put-with-indexed [:ul]
  ;                   [{:content [:li "A"]}
  ;                    {:content [:li "B"]}]
  ;                   (fn [dex item] (:content item)))
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @usage
  ; (put-with-indexed [:ul]
  ;                   [{:key "my-react-key"      :content [:li "A"]}
  ;                    {:key "another-react-key" :content [:li "B"]}]
  ;                   (fn [dex item] (:content item))
  ;                   :key)
  ; =>
  ; [:ul [:li "A"] [:li "B"]]
  ;
  ; @return (hiccup)
  ([container n put-f]
   (put-with-indexed container n put-f (fn [_] (random/generate-uuid))))

  ([container n put-f key-f]
   ; @note (#5060)
   ; To attach a React key to the output of the given 'put-f' function, the '[:<> ...]' React form is required.
   (if (vector? container)
       (let [n     (mixed/to-seqable n)
             put-f (mixed/to-ifn put-f)
             key-f (mixed/to-ifn key-f)]
            (letfn [(f0 [container dex x]
                        (if x (conj container ^{:key (key-f x)} [:<> (put-f dex x)])
                              (->   container)))]
                   (reduce-kv f0 container (vec n)))))))
