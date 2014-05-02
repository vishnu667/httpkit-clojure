(ns httpkit-clojure.core
  (:gen-class)
  (:use [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]
        org.httpkit.server)
  )


(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (@server :timeout 100)
    (reset! server nil)))

(defn show-landing-page [req] ;; ordinary clojure function, accepts a request map, returns a response map
  ;; return landing page's html string. Using template library is a good idea:
  ;; mustache (https://github.com/shenfeng/mustache.clj, https://github.com/fhd/clostache...)
  ;; enlive (https://github.com/cgrand/enlive)
  ;; hiccup(https://github.com/weavejester/hiccup)
  )

(defroutes all-routes
  (GET "/hello" [] app)
  (GET "/" [] show-landing-page)
  )

(defn -main [& args]
  ;; The #' is useful, when you want to hot-reload code
  ;; You may want to take a look: https://github.com/clojure/tools.namespace
  ;; and http://http-kit.org/migration.html#reload
  (def port 8080)
  (println "server is Started in port number" port)
  (println "the arguments are "  (= -1 (.indexOf args "a")))

  (run-server (site #'all-routes) {:port port}))