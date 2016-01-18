(ns canvas.core)

(enable-console-print!)

(def canvas-dom (.getElementById js/document "myCanvas"))

(def width (.-width canvas-dom))

(def ctx (.getContext canvas-dom "2d"))

(defn deg-to-rad
  [n]
  (* (/ Math/PI 180) n))

(defn sine-coord
  [x]
  (let [sin (Math/sin (deg-to-rad x))
        y (- 100 (* sin 90))]
    {:x x :y y}))

(defn fill-rect
  [x y colour]
  (set! (.-fillStyle ctx) colour)
  (.fillRect ctx x y 2 2))

(fill-rect 10 10 "#345666")

(def x-coords (iterate inc 0))

(defn draw-sine
  [color]
  (->> x-coords
     (take width)
     (map sine-coord)
     (map (fn [{:keys [x y]}]
            (fill-rect x y color)))
     (doall)))

(draw-sine "#232323")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
