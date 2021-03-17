(defproject metabase/sparksql-databricks-driver "1.2.0"
  :min-lein-version "2.5.0"

  :plugins [[s3-wagon-private "1.3.4"]]

  :repositories [
    ["releases" {:url "s3p://<s3_bucket>/maven/releases/" :no-auth true :sign-releases false}]
    ["snapshots" {:url "s3p://<s3_bucket>/maven/snapshots/" :no-auth true}]
  ]

  :dependencies
  [
   [simba/simba-spark-jdbc42 "2.6.17.1021"]
  ]

  ;; only used for the lein with-drivers stuff (i.e. tests and REPL)
  :aot [metabase.driver.FixedHiveDriver]

  :profiles
  {:provided
   {:dependencies
    [
      [metabase-core "1.0.0-SNAPSHOT"]
      [clojure.java-time "0.3.1"]
    ]}

   :uberjar
   {:auto-clean    true
    :aot           :all
    :javac-options ["-target" "1.8", "-source" "1.8"]
    :target-path   "target/%s"
    :uberjar-name  "sparksql-databricks.metabase-driver.jar"}})
