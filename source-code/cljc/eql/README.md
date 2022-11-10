
# query (query-request)
  Az egyes query (query-request) lekérések egy vagy több query-question kérdés
  vagy query-action kérés felsorolásai egy vektorban.
  A query vektor elemei lehetnek: resolver-id, document-entity, joined-query-question
  query-action, ...

  @param (vector) query
   [(keyword) resolver-id
    (vector) document-entity
    (map) joined-query-question
    (?) query-action]



# resolver-id
  Egy paramétert nem fogadó resolver azonosítója.
  Pl.: :all-users



# document-link
  Egy dokumentumban egy vagy több másik dokumentumot úgy érdemes tárolni (hozzácsatolni),
  hogy a csatolt dokumentumoknak csak olyan kivonatát táruljuk ami nem tartalmaz
  változó adatot.
  Ilyen nem változó adat a csatolt dokumentumok azonosítója, aminek segítségével
  egy csatolt dokumentumokat egyértelműen azonosíthatjuk és elérhetjük az
  eredeti tárolási helyükön.
  Pl.: [{:account/id "0ce14671-e916-43ab-b057-0939329d4c1b"
         :games [{:game/id "9cea3696-56ca-4be5-a5f2-e7477d9f43fb"}
                 {...}]}]



# document-entity
  Olyan adat, amely egyértelműen azonosít egy dokumentumot.
  Pl.: [:directory/id "my-directory"]
       [:passenger/passport "KI-1993-6503688-FF"]



# joined-query-question
  Egy térképben összekapcsolt entitás és az entitás által azonosított
  dokumentumból kért adatok felsorolása egy vektorban.
  Pl.: {[:directory/id "my-directory"] [:directory/alias]}



# query-question
  Egy query-question kérdés lehet resolver-id azonosító, entity vagy joined-query-question térkép.
  Pl.: :all-users
       [:directory/id "my-directory"]
       {[:directory/id "my-directory"] [:directory/alias]}



# query-action
  Egy mutation függvény neve és a függvény számára átadott paraméterek térképe
  egy listában.
  Pl.: (media/create-directory! {:source-directory-id \"root\"})



# query-response
  A szerver válasza a query vektorban felsorlt kérdésekre és kérésekre.
  A válasz típusa térkép, amiben a kérdések és kérések a kulcsok és az azokra
  adott válaszok az értékek.
  {:all-users                     [{...} {...}]
   [:directory/id "my-directory"] {:directory/alias "My directory"}
   media/create-directory!        "Directory created"}



# query-answer
  A query-response térkép értékei a query-answer válaszok.
  Pl.: [{...} {...}]
       {:directory/alias "My directory"}



# query-key
  A query-response térkép kulcsai a query-key kulcsok. Egy query-key kulcs lehet
  egy query-question kérdés, vagy egy mutation függvény neve.
  Pl.: :all-users
       [:directory/id "my-directory"]
       mutation-f-name
