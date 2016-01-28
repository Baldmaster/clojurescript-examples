(ns physics.ball
  (:require [physics.protocols :as p]))

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
      (. ctx (fill)))))

(set! (.. Ball -prototype -draw)
      (fn [ctx]
        (this-as this (p/draw this ctx))))

(defn new-ball
  [x y & {:keys [vx vy radius color]
      :or {vx 0 vy 0 radius 10 color "#111"}}]
  (Ball. x y vx vy radius color))
