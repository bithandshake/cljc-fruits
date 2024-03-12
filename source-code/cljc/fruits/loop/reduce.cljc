
(ns fruits.loop.reduce)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reduce-kv-indexed
  ; @description
  ; Extends the 'reduce-kv' function with providing the actual index to the applied function.
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (map) n
  ;
  ; @usage
  ; (reduce-kv-indexed (fn [result dex k v]) nil {})
  ;
  ; @return (*)
  [f initial n]
  (letfn [(f0 [[result dex] k v]
              [(f result dex k v)
               (inc dex)])]
         (-> (reduce-kv f0 [initial 0] n) first)))

(defn reduce-range
  ; @param (function) f
  ; @param (*) initial
  ; @param (integer)(opt) start
  ; @param (integer) end
  ;
  ; @usage
  ; (reduce-range (fn [result x]) nil 10)
  ;
  ; @usage
  ; (reduce-range (fn [result x]) nil 10 20)
  ;
  ; @return (*)
  ([f initial end]
   (reduce f initial (range end)))

  ([f initial start end]
   (reduce f initial (range start end))))

(defn reduce-indexed
  ; @description
  ; Works the same as the 'reduce' function, but the 'f' function takes the index of the current item as its second parameter.
  ;
  ; @param (function) f
  ; @param (*) initial
  ; @param (collection) coll
  ;
  ; @usage
  ; (reduce-indexed (fn [result dex x]) nil [:a :b])
  ;
  ; @return (*)
  [f initial coll]
  (reduce-kv f initial coll))
