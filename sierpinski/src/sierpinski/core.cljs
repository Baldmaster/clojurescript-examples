(ns sierpinski.core)

(enable-console-print!)

(defrecord Point [x y])

(defonce canvas (.getElementById js/document "canvas"))

(defonce context (.getContext canvas "2d"))

(defonce corners [(Point. (/ (.-width canvas) 2) 0)
                  (Point. 0 (.-height canvas))
                  (Point. (.-width canvas) (.-height canvas))])

(def p (atom (nth corners 0)))

(defn render!
  []
  (let [r (rand-int 3)
        {x1 :x y1 :y} (nth corners r)
        {x2 :x y2 :y}  @p
        x (/ (+ x1 x2) 2)
        y (/ (+ y1 y2) 2)]
    (.fillRect context x y 1 1)
    (swap! p assoc :x x :y y)
    (. js/window requestAnimationFrame render!)))

(render!)
