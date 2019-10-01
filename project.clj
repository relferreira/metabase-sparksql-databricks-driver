(defproject metabase/sparksql-databricks-driver "1.0.0"
  :min-lein-version "2.5.0"

  :repositories [["bintray" "https://dl.bintray.com/ifood/third"]]

  :dependencies
  [
   [simba/simba-spark-jdbc41 "2.6.3.1003"]
  ]

  ;; only used for the lein with-drivers stuff (i.e. tests and REPL)
  :aot [metabase.driver.FixedHiveDriver]

  :profiles
  {:provided
   {:dependencies
    [[metabase-core "1.0.0-SNAPSHOT"]]}

   :uberjar
   {:auto-clean    true
    :aot           :all
    :javac-options ["-target" "1.8", "-source" "1.8"]
    :target-path   "target/%s"
    :uberjar-name  "sparksql-databricks.metabase-driver.jar"}})
