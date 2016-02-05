(ns guestbook.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [guestbook.core-test]))

(enable-console-print!)

(doo-tests 'guestbook.core-test)
