(ns phisics.core
  (:require [goog.Timer :as Timer]))

(enable-console-print!)

(def canvas (. js/document (getElementById "myCanvas")))

(def ctx (. canvas (getContext "2d")))

(def width (.-width canvas))

(def height (.-height canvas))

(def radius 25)

(def color "#0055ff")

(def g 0.1)

(def x 200)

(def y 200)

(def state (atom
            {:x 200
             :y 200
             :vx 2
             :vy 0}))

(defn draw
  [x y]
  (do
   (. ctx (clearRect 0 0 width height))
   (set! (.-fillStyle ctx) color)
   (. ctx (beginPath))
   (. ctx (arc x y radius 0 (* 2 Math/PI) true))
   (. ctx (closePath))
   (. ctx (fill))))

(defn move
  []
  (let [{:keys [x y vx vy]} @state
        [y' vy'] (if (> (+ y vy g) (- height radius))
                   [(- height radius) (* (+ vy g) -0.98)]
                   [(+ y vy g) (+ vy g)])
        x' (if (> (+ x vx) width)
             (* -1 radius)
             (+ x vx))]
    (do
      (swap! state
             assoc
             :vy vy'
             :x  x'
             :y  y'
             
      (draw x' y')))))

(defn start
  []
  (js/setInterval move (/ 1000 60)))

(start)






(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
