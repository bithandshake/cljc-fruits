
(ns fruits.hiccup.parse
    (:require [fruits.css.api           :as css]
              [fruits.hiccup.attributes :as attributes]
              [fruits.hiccup.type       :as type]
              [fruits.hiccup.walk       :as walk]
              [fruits.keyword.api       :as keyword]
              [fruits.string.api        :as string]
              [fruits.vector.api        :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-css
  ; @description
  ; Parses the inline CSS styles within the given 'n' HICCUP value (recursively).
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (parse-css [:td [:p {:style "color: red;"}]])
  ; =>
  ; [:td [:p {:style {:color "red"}}]]
  ;
  ; @return (hiccup)
  [n]
  (if (vector? n)
      (letfn [(f0 [element]
                  (let [style (attributes/get-style element)]
                       (if (-> style string?) ; <- The style could be already a map.
                           (-> element (attributes/set-style (css/parse style)))
                           (-> element))))]
             (walk/walk n f0))))

(defn unparse-css
  ; @description
  ; Unparses the inline CSS styles within the given 'n' HICCUP value (recursively).
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (unparse-css [:td [:p {:style {:color "red"}}]])
  ; =>
  ; [:td [:p {:style "color: red;"}]]
  ;
  ; @return (hiccup)
  [n]
  (if (vector? n)
      (letfn [(f0 [element]
                  (let [style (attributes/get-style element)]
                       (if (-> style map?) ; <- The style could be already a string.
                           (-> element (attributes/set-style (css/unparse style)))
                           (-> element))))]
             (walk/walk n f0))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn unparse-class-vectors
  ; @description
  ; Unparses the CSS class vectors within the given 'n' HICCUP value (recursively).
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (unparse-class-vectors [:div {:class [:my-class :another-class]}])
  ; =>
  ; [:div.my-class.another-class]
  ;
  ; @return (hiccup)
  [n]
  ; - Accepting vectors as a class is a feature in hiccup released in version 2.0.0-alpha2.
  ;   https://github.com/weavejester/hiccup/pull/139/commits/2e7481e3630e3e8f669840d0ffede7fa97e42f0c
  ; - Unfortunatelly the hiccup interpreter does not accept keyword type class names:
  ;   [:div {:class ["my-class"]}] <- Good :)
  ;   [:div {:class [:my-class]}]  <- Bad  :(
  (if (vector? n)
      (letfn [(f0 [tag-name class-group] (keyword/join (f1 tag-name class-group) "."))
              (f1 [tag-name class-group] (vector/cons-item class-group tag-name))
              (f2 [element]
                  (or (if-let [{:keys [class]} (attributes/get-attributes element)]
                              (if (-> class vector?)
                                  (-> element (update 1 dissoc :class)
                                              (update 0 f0 class))))
                      (-> element)))]
             (walk/walk n f2))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parse-newlines
  ; @description
  ; Parses the newline characters to '[:br]' tags within the given 'n' HICCUP value (recursively).
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (parse-newlines [:p "Hello world!\nIt's me!"])
  ; =>
  ; [:p "Hello world!" [:br] "It's me!"]
  ;
  ; @return (hiccup)
  [n]
  (if (vector? n)
      (letfn [(f0 [element] (reduce f1 [] element))
              (f1 [result content] (if (string/contains-part? content "\n")
                                       (as-> content % (string/split        % #"\n")
                                                       (vector/gap-items    % [:br])
                                                       (vector/concat-items result %))
                                       (vector/conj-item result content)))]
             (walk/walk n f0))))

(defn unparse-newlines
  ; @description
  ; Unparses the '[:br]' tags to newline characters within the given 'n' HICCUP value (recursively).
  ;
  ; @param (hiccup) n
  ;
  ; @usage
  ; (unparse-newlines [:p "Hello world! [:br] It's me!"])
  ; =>
  ; [:p "Hello world!"\n"It's me!"]
  ;
  ; @return (hiccup)
  [n]
  (if (vector? n)
      (letfn [(f0 [element] (reduce f1 [] element))
              (f1 [result content] (if (type/tag-name? content :br)
                                       (vector/conj-item result "\n")
                                       (vector/conj-item result content)))]
             (walk/walk n f0))))
