
(ns hiccup.walk
    (:require [candy.api         :refer [return]]
              [hiccup.type       :as type]
              [mid-fruits.random :as random]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn walk
  ; @param (hiccup) n
  ; @param (function) f
  ;
  ; @example
  ;  (walk [:td [:p {:style {:color "red"}}]]
  ;        #(conj % "420"))
  ;  =>
  ;  [:td [:p {:style {:color "red"}} "420"] "420"]
  ;
  ; @return (hiccup)
  [n f]
  (if (type/hiccup? n)
      (letfn [(walk-f [%1 %2] (conj %1 (walk %2 f)))]
             (reduce walk-f [] (f n)))
      (return n)))

(defn explode
  ; @param (string) n
  ; @param (hiccup) container
  ;
  ; @example
  ;  (explode "ab" [:div])
  ;  =>
  ;  [:div [:span "a"]
  ;        [:span "b"])
  ;
  ; @return (nil or hiccup)
  [n container]
  (if (and (string? n)
           (type/hiccup? container))
      (letfn [(f [%1 %2] (conj %1 ^{:key (random/generate-uuid)} [:span %2]))]
             (reduce f container n))))
