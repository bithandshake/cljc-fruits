
(ns fruits.svg.attributes)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view-box-dimensions
  ; @param (px) width
  ; @param (px) height
  ;
  ; @usage
  ; (view-box-dimensions 1024 1024)
  ; =>
  ; "0 0 1024 1024"
  ;
  ; @return (string)
  [width height]
  (str "0 0 " width " " height))

(defn wrapper-attributes
  ; @param (map) attributes
  ; {:height (px)
  ;  :style (map)(opt)
  ;  :width (px)}
  ;
  ; @usage
  ; (wrapper-attributes {:height 256 :width 256})
  ; =>
  ; {:style {:height "100%" :width "100%"}
  ;  :view-box "0 0 256 256"
  ;  :xmlns "..."}
  ;
  ; @return (map)
  ; {:style (map)
  ;   {:height (string)
  ;    :width (string)}
  ;  :view-box (string)
  ;  :xmlns (string)}
  [{:keys [height style width] :as attributes}]
  (merge (dissoc attributes :height :width)
         {:style    (merge {:height "100%" :width "100%"} style)
          :view-box (view-box-dimensions width height)
          :xmlns    "http://www.w3.org/2000/svg"}))
