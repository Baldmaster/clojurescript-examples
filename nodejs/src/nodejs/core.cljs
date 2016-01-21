(ns nodejs.core)

(enable-console-print!)

(def fibonacci-seq (map first (iterate (fn [[a b]] [b (+ a b)]) [1 1])))


(defn -main
  [n]
  (let [k (js/parseInt n)]
    (println (first (drop (dec k) fibonacci-seq)))))
  

(set! *main-cli-fn* -main)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
