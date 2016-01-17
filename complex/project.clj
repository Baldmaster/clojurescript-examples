(defproject complex "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-3"]]

  :source-paths ["src"]

  :profiles
  {:dev {:dependencies [[figwheel-sidecar "0.5.0-1"]
                        [com.cemerick/piggieback "0.2.1"]]
         :source-paths ["cljs_src" "dev"]}
   :repl {:plugins [[cider/cider-nrepl "0.9.1"]] }}
  
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}  
  
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                :figwheel {:on-jsload "complex.core/on-js-reload"}

                :compiler {:main complex.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/complex.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/complex.js"
                           :main complex.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"] ;; watch and update CSS
             })
