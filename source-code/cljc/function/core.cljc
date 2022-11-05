
(ns function.core
    (:require [mid-fruits.candy  :refer [param]]
              [mid-fruits.string :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn ->js
  ; @param (string) var-name
  ;
  ; @example
  ;  (->js "vector.api/conj-item")
  ;  =>
  ;  "vector.api.conj_item"
  ;
  ; @return (string)
  [var-name]
  #?(:cljs (-> var-name (string/replace-part #"/" ".")
                        (string/replace-part #"-" "_"))))

(defn invoke
  ; @param (string) function-name
  ; @param (list of *) args
  ;
  ; @example
  ;  (invoke "vector.api/conj-item" [:a :b] :c)
  ;  =>
  ;  [:a :b :c]
  [function-name & args])
;  #?(:cljs (let [evaled (js/eval (->js function-name))]
;                (apply evaled args)]
;  #?(:clj  (apply (resolve (symbol function-name)) args)))
