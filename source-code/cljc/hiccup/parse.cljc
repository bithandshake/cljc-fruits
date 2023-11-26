
(ns hiccup.parse
    (:require [css.api           :as css]
              [hiccup.attributes :as attributes]
              [hiccup.walk       :as walk]
              [string.api        :as string]
              [vector.api        :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-css
  ; @param (hiccup) n
  ;
  ; @example
  ; (parse-css [:td [:p {:style "color: red;"}]])
  ; =>
  ; [:td [:p {:style {:color "red"}}]]
  ;
  ; @return (hiccup)
  [n])

(defn unparse-css
  ; @param (hiccup) n
  ;
  ; @example
  ; (unparse-css [:td [:p {:style {:color "red"}}]])
  ; =>
  ; [:td [:p {:style "color: red;"}]]
  ;
  ; @return (hiccup)
  [n]
  (letfn [(f0 [element]
              (let [style (attributes/get-style element)]
                   (if (-> style map?)
                       (-> element (attributes/set-style (css/unparse style)))
                       (-> element))))]
         (walk/walk n f0)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-newlines
  ; @param (hiccup) n
  ;
  ; @example
  ; (parse-newlines [:p "Hello world!\nIt's me!"])
  ; =>
  ; [:p "Hello world!" [:br] "It's me!"]
  ;
  ; @return (hiccup)
  [n]
  (letfn [(f0 [element] (reduce f1 [] element))
          (f1 [element content] (if (string/contains-part? content "\n")
                                    (as-> content % (string/split % #"\n")
                                                    (vector/gap-items % [:br])
                                                    (vector/concat-items element %))
                                    (vector/conj-item element content)))]
         (walk/walk n f0)))

(defn unparse-newlines
  ; @param (hiccup) n
  ;
  ; @example
  ; (unparse-newlines [:p "Hello world! [:br] It's me!"])
  ; =>
  ; [:p "Hello world!"\n"It's me!"]
  ;
  ; @return (hiccup)
  [n])
