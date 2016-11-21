(ns clj-roster.rest-test
  (:require [clj-roster.rest :refer :all]
            [midje.sweet :refer [facts fact]]
            [ring.mock.request :as mock]))

(def path "/example-service/dev")
(def endpoint "http://www.example-service.com/dev")

(def get-endpoint-request (mock/request :get path))
(def put-endpoint-request (mock/request :put path endpoint))
(def delete-endpoint-request (mock/request :delete path))

(defn expected-response [status body]
  {:status status
   :body body
   :headers {"Content-Type" "text/html; charset=utf-8"}})

(def not-found-response (expected-response 404 "example-service has no endpoint in dev"))

(facts "about the roster rest-interface"
       (fact "GET /<serviceName>/<environment> with an unknown service returns 404"
             (registry-routes get-endpoint-request)
             => not-found-response)
       (fact "PUT to /<service-name>/<environment> adds endpoint to registry"
             (registry-routes put-endpoint-request)
             (registry-routes get-endpoint-request)
             => (expected-response 200 endpoint))
       (fact "DELETE to /<service-name>/<environment> removes endpoint from registry"
             (registry-routes put-endpoint-request)
             (registry-routes delete-endpoint-request)
             (registry-routes get-endpoint-request)
             => not-found-response))
