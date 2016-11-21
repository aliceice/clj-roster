(ns clj-roster.rest
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-roster.core :refer :all]))

(def service-registry (in-memory-registry))

(defroutes json-handler
           (GET "/:name/:environment" [name environment]
             (let [endpoint (get-endpoint service-registry
                                          {:name name :environment environment})]
               (if (not= :unknown endpoint)
                 endpoint
                 (route/not-found (str  name " has no endpoint in " environment))))))

