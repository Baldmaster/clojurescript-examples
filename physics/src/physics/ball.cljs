(ns physics.ball
  (:require [physics.protocols :as p]
            [physics.constants :as const]))

;;(defprotocol Draw
;;  "Draw something"
;;  (draw [entity context]))

(defrecord Ball [x y vx vy radius color]
  p/Draw
  (p/draw
    [ball ctx]
    (do
      (set! (.-fillStyle ctx) color)
      (. ctx (beginPath))
      (. ctx (arc x y radius 0 (* 2 Math/PI) true))
      (. ctx (closePath))
      (. ctx (fill))))
  p/Kinematics
  (p/move
    [ball width height]
    (let [dy (+ vy const/g)
        [y' vy'] (if (> (+ y dy) (- height radius))
                   [(- height radius) (* dy -0.98)]
                   [(+ y dy) dy])
         x' (if (> (+ x vx) (+ width radius))
              (* -2 radius)
              (+ x vx))]
    (assoc ball :x x' :y y' :vy vy'))))

;;Add prototype function
;;(set! (.. Ball -prototype -draw)
;;      (fn [ctx]
;;        (this-as this (p/draw this ctx))))

(defn new-ball
  [x y & {:keys [vx vy radius color]
      :or {vx 0 vy 0 radius 10 color "#111"}}]
  (Ball. x y vx vy radius color))
