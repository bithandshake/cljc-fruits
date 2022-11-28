
(ns uri.check
    (:require [candy.api  :refer [return]]
              [string.api :as string]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn match-template?
  ; @param (string) path
  ; @param (string) template
  ;
  ; @usage
  ; (match-template? "/my-path/my-value" "/my-path/:my-param")
  ;
  ; @example
  ; (match-template? "/my-path/my-value" "/my-path/:my-param")
  ; =>
  ; true
  ;
  ; @return (boolean)
  [path template]
  (let [path                 (string/to-lowercase path)
        template             (string/to-lowercase template)
        path-parts           (string/split path     #"/")
        template-parts       (string/split template #"/")
        path-parts-count     (count path-parts)
        template-parts-count (count template-parts)]
                              ; Ha a dex nagyobb, mint a template-parts vektor elemeinek száma, ...
       (letfn [(f [dex] (cond (= dex template-parts-count)
                              ; ... akkor a path-parts és template-parts vektorok elemeinek
                              ; összeillesztése sikeres volt.
                              (return true)
                              ; Ha a vizsgált template-part első karaktere ":", ...
                              (= ":" (str (get-in template-parts [dex 0])))
                              ; ... akkor a vizsgált path-part értéke egy path-param, aminek
                              ; nem szükséges egyeznie, ezért átlép a következő iterációba.
                              (f (inc dex))
                              ; Ha a vizsgált path-part és template-part értéke megegyezik, ...
                              (= (get path-parts     dex)
                                 (get template-parts dex))
                              ; ... akkor átlép a következő iterációba.
                              (f (inc dex))))]
                           ; Ha a path-parts és template-parts vektorok elemeinek száma megegyezik ...
              (boolean (if (= path-parts-count template-parts-count)
                           (f 0))))))
