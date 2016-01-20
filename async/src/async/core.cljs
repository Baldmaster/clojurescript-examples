(ns async.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.core.async
             :as a
             :refer [>! <! chan buffer close! put!
                     alts! timeout]]))
(enable-console-print!)

(def canvas (.getElementById js/document "myCanvas"))

(def ctx (.getContext canvas "2d"))

(def h (.-height canvas))

(def w (.-width canvas))

(def center [(quot w 2) (quot h 2)])

(def k 1)

;;(.beginPath ctx)

;;(let [[x y] center]
;;  (. ctx (moveTo x y)))

(defn spiral-point
  [n j k]
  (let [[cx cy] center
        phi (* n j)
        r (* k n)
        x (+ 300 (* r (Math/cos phi)))
        y (+ 300 (* r (Math/sin phi)))]
    [x y]))


(defn coll->chan [coll]
  (let [ch (chan)]
    (go
      (loop [coll coll]
        (when-let [data (first coll)]
          (<! (timeout 10))
          (>! ch data)
          (recur (rest coll))))
      (close! ch))
    ch))

(def points (map spiral-point (iterate #(+ % 0.04) 0) (repeat 0.9) (repeat 11)))

(defn fill-dot
  [x y]
  (do
    (.beginPath ctx)
    (. ctx (arc x y 5 0 (* 2 Math/PI) false))
    (set! (.-fillStyle ctx) "green")
    (.fill ctx)
    (set! (.-strokeStyle ctx) "green")
    (.stroke ctx)))

(let [data-chan (coll->chan (take 700 points))]
  (go-loop []
    (when-let [[x y] (<! data-chan)]
      (fill-dot x y)
      (recur))))

(defn on-js-reload []
)
