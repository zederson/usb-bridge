(ns usb-bridge.configuration
  (:require [clojure.java.io :as io])
  (:import  [java.io PushbackReader]))

(def PATH "./resources/application_config.clj")

(def config
  (with-open [r (io/reader PATH)]
    (read (PushbackReader. r))))

(def port (-> (config :arduino) :port))
(def baud-rate (-> (config :arduino) :baud_rate))

