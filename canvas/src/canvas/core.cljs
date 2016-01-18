(ns canvas.core
  (:require [monet.canvas :as canvas]))

(enable-console-print!)

(def canvas-dom (.getElementById js/document "myCanvas"))

(def monet-canvas (canvas/init canvas-dom "2d"))


(canvas/add-entity monet-canvas :background
                   (canvas/entity {:x 0 :y 0 :w 100 :h 100}
                                  nil
                                  (fn [ctx val]
                                    (-> ctx
                                        (canvas/fill-style "#123456")
                                        (canvas/fill-rect val)))))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
