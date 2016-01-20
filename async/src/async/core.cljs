(ns async.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async
             :as a
             :refer [>! <! chan buffer close! put!
                     alts! timeout]]))

(enable-console-print!)


(defn upper-caser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/upper-case (<! in)))))
    out))

(defn reverser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/reverse (<! in)))))
    out))

(defn printer
  [in]
  (go (while true (println (<! in)))))


(def in-chan (chan))
(def upper-caser-out (upper-caser in-chan))
(def reverser-out (reverser upper-caser-out))
(printer reverser-out)

(go (>! in-chan " ,olleH"))
(go (>! in-chan "!dlrow"))


(defn on-js-reload []
)
