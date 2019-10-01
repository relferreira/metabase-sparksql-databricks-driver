(ns metabase.driver.FixedSparkDriver
  (:gen-class
   :extends com.simba.spark.jdbc41.Driver
   :exposes-methods {connect superConnect}
   :init init
   :prefix "driver-"
   :constructors {[] []})
  (:require [metabase.driver.connection :as connection]))

(defn driver-init
  "Initializes the Spark driver"
  []
  [[] nil])

(defn driver-connect
  "Connects to a Spark database, fixing the connection to with Metabase"
  [^com.simba.spark.jdbc41.Driver this, ^String url, ^java.util.Properties info]
  (connection/decorate-and-fix (.superConnect this url info)))
