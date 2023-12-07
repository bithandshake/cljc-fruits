
(ns fruits.eql.core)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn id->document-link
  ; @param (string) id
  ; @param (keyword)(opt) namespace
  ;
  ; @example
  ; (id->document-link "my-directory")
  ; =>
  ; {:id "my-directory"}
  ;
  ; @example
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
  ; @example
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
  ; @example
  ; (id->document-entity "my-directory")
  ; =>
  ; [:id "my-directory"]
  ;
  ; @example
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
  ; @example
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
  ; @example
  ; (id->placeholder "my-id")
  ; =>
  ; :>/my-id
  ;
  ; @return (keyword)
  [id]
  (keyword (str ">/" (name id))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn append-to-query
  ; @param (nil or vector) query
  ; @param (keyword, map, string or vector) query-parts
  ;
  ; @example
  ; (append-to-query nil :all-users)
  ; =>
  ; [:all-users]
  ;
  ; @example
  ; (append-to-query [] :all-users)
  ; =>
  ; [:all-users]
  ;
  ; @example
  ; (append-to-query [:all-users]
  ;                  [:directory/id :my-directory])
  ; =>
  ; [:all-users [:directory/id :my-directory]]
  ;
  ; @return (vector)
  [query & query-parts]
  (cond (vector?  query) (vec (concat query   query-parts))
        (nil?     query) (vec (concat []      query-parts))
        :return          (vec (concat [query] query-parts))))
