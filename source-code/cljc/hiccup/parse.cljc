
(ns hiccup.parse
    (:require [mid-fruits.candy  :refer [return]]
              [mid-fruits.css    :as css]
              [hiccup.attributes :as attributes]
              [hiccup.walk       :as walk]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse-css
  ; @param (hiccup) n
  ;
  ; @example
  ;  (unparse-css [:td [:p {:style {:color "red"}}]])
  ;  =>
  ;  [:td [:p {:style "color: red;"}]]
  ;
  ; @example
  ;  (unparse-css [:td [:p {:style "color: red;"}]])
  ;  =>
  ;  [:td [:p {:style "color: red;"}]]
  ;
  ; @return (hiccup)
  [n]
  (letfn [(f [n] (let [style (attributes/get-style n)]
                      (if (map? style)
                          (attributes/set-style n (css/unparse style))
                          (return               n))))]
         (walk/walk n f)))
