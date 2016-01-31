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
;;(def frame-id (atom nil))

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

(def entities (take 100 balls))

(. canvas
   (addEventListener "click"
                     ((fn [frame-id]
                        (fn []
                          (if (nil? @frame-id)
                            (animate entities frame-id)
                            (do
                              (. js/window (cancelAnimationFrame @frame-id))
                              (reset! frame-id  nil))))) (atom nil))))


(defn draw!
  [entity]
  (p/draw entity ctx))

(defn render!
  [coll]x
  (do
    (. ctx (clearRect 0 0 width height))
    (doseq [entity coll]
      (.draw entity ctx))))

(defn animate [entities frame-id]
  (letfn [(loop [state]
            (fn []
              (->>
               (. js/window requestAnimationFrame
                  (loop (map #(p/move % width height) state)))
               (reset! frame-id))
              (render! state)))]
    ((loop entities))))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
