
(ns fruits.eql.convert)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn id->document-link
  ; @param (string) id
  ; @param (keyword)(opt) namespace
  ;
  ; @usage
  ; (id->document-link "my-directory")
  ; =>
  ; {:id "my-directory"}
  ;
  ; @usage
  ; (id->document-link "my-directory" :directory)
  ; =>
  ; {:directory/id "my-directory"}
  ;
  ; @return (map)
  ([id]           {:id id})
  ([id namespace] {(keyword (name namespace) "id") id}))

(defn document-link->id
  ; @param (map) document-link
  ;
  ; @usage
  ; (document-link->id {:directory/id "my-directory"})
  ; =>
  ; "my-directory"
  ;
  ; @return (string)
  [document-link]
  (-> document-link vals first))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn id->document-entity
  ; @param (string) id
  ; @param (keyword)(opt) namespace
  ;
  ; @usage
  ; (id->document-entity "my-directory")
  ; =>
  ; [:id "my-directory"]
  ;
  ; @usage
  ; (id->document-entity "my-directory" :directory)
  ; =>
  ; [:directory/id "my-directory"]
  ;
  ; @return (vector)
  ([id]           [:id id])
  ([id namespace] [(keyword (name namespace) "id") id]))

(defn document-entity->id
  ; @param (vector) document-entity
  ;
  ; @usage
  ; (document-entity->id [:directory/id "my-directory"])
  ; =>
  ; "my-directory"
  ;
  ; @return (string)
  [document-entity]
  (second document-entity))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn id->placeholder
  ; @param (string) id
  ;
  ; @usage
  ; (id->placeholder "my-id")
  ; =>
  ; :>/my-id
  ;
  ; @return (keyword)
  [id]
  (keyword (str ">/" (name id))))
