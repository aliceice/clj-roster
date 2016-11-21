(ns clj-roster.rest-test
  (:require [clj-roster.rest :refer :all]
            [midje.sweet :refer [facts fact]]
            [ring.mock.request :as mock]))

(facts "about the roster rest-interface"
       (fact "GET /<serviceName>/<environment> with an unknown service returns 404"
             (json-handler (mock/request :get "/example-service/dev"))
             => {:status 404
                 :body "example-service has no endpoint in dev"
                 :headers {"Content-Type" "text/html; charset=utf-8"}}))
