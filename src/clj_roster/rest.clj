(ns clj-roster.rest
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-roster.core :refer :all]))

(def service-registry (in-memory-registry))

(defroutes registry-routes
           (GET "/:name/:environment" [name environment]
             (let [endpoint (get-endpoint service-registry
                                          (->ServiceRequest name environment))]
               (if (not= :unknown endpoint)
                 endpoint
                 (route/not-found (str name " has no endpoint in " environment)))))

           (PUT "/:name/:environment" [name environment :as {input-stream :body}]
             (put-endpoint service-registry
                           (->ServiceRequest name environment)
                           (slurp input-stream)))

           (DELETE "/:name/:environment" [name environment]
             (delete-endpoint service-registry
                              (->ServiceRequest name environment))
             {:status 200}))
