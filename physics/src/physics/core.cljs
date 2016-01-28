(ns physics.core
  (:require
   [physics.protocols :as p]
   [physics.ball :as b]
   [goog.Timer :as Timer]))

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
             :vx 0
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


(def ball (b/new-ball 300 300 :vx 3))


(defn random-value
  ([] (Math/round (Math/random)))
  ([from] (Math/round (+ (Math/random) from)))
  ([from to] (Math/round (-> (Math/random)
                             (* (- to from))
                             (+ from)))))


(def balls (repeatedly #(let [x (random-value 0 width)
                               y (random-value 0 height)
                               vx (random-value 1 10)
                               radius (random-value 10 30)]
                           (b/new-ball x y :vx vx :radius radius))))


(defn move-ball!
  [ball]
  (let [{:keys [x y vx vy radius]} ball
        dy (+ vy g)
        [y' vy'] (if (> (+ y dy) (- height radius))
                   [(- height radius) (* dy -0.98)]
                   [(+ y dy) dy])
         x' (if (> (+ x vx) (+ width radius))
              (* -2 radius)
              (+ x vx))]
    (do
      (set! (.-x ball) x')
      (set! (.-y ball) y')
      (set! (.-vy ball) vy'))))

(defn move!
  [ball]
  (do
    ;;(. ctx (clearRect 0 0 width height))
    (move-ball! ball)
    ;;(p/draw ball ctx)
    (. ball (draw ctx))))


(defn render!
  [coll]
  (do
    (. ctx (clearRect 0 0 width height))
    (-> (map #(move! %) coll)
        (dorun))))

(defn render
  []
  (do
    (. js/window (requestAnimationFrame render))
    (render! (take 100 balls))))

(render)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
