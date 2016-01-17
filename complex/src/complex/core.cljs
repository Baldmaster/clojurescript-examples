(ns complex.core
  (:require ))

(enable-console-print!)


(defprotocol Show
  (show [x] "Show something"))

(defprotocol Num
  (negate [x] "get negative number")
  (is-zero? [x] ""))

(defprotocol ComplexNum
  (addc [a b] "Complex numbers addition")
  (subc [a b] "Complex numbers subtraction")
  (multc [a b] "")
  (divc [a b] ""))

(deftype Complex [real im]
  ComplexNum
  (addc [a b]    (Complex.
                 (+ real (.-real b))
                 (+ im   (.-im b))))
  (subc [a b]  (Complex.
                 (- real (.-real b))
                 (- im   (.-im b))))
  (multc [a b]   (Complex.
                 (- (* real (.-real b)) (* im (.-im b)))
                 (+ (* im (.-real b)) (* real (.-im b)))))
  (divc [x y] (if (is-zero? y)
                  (throw "Divide by zero!")
                  (let [a real
                        b im
                        c (.-real y)
                        d (.-im y)
                        e (+ (* c c) (* d d))]
                    (Complex.
                     (/ (+ (* a c) (* b d)) e)
                     (/ (- (* b c) (* a d)) e)))))
  Num
  (negate [x] (Complex. (* -1 real) (* -1 im)))
  (is-zero? [x] (and (zero? real) (zero? im))))

;; Define getters, equivalent to this js code:
;; Complex.prototype.getSmth = function () { return this.smth };
(set! (.. Complex -prototype -getReal) (fn [] (this-as this (.-real this))))
(set! (.. Complex -prototype -getIm) (fn [] (this-as this (.-im this))))
;; 'add n this' will fail if n is not complex number
(set! (.. Complex -prototype -add) (fn [n] (this-as this (addc n this))))
(set! (.. Complex -prototype -subtr) (fn [n] (this-as this (addc (negate n) this))))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
