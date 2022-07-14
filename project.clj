(defproject metabase/sparksql-databricks-driver "1.2.0"
  :min-lein-version "2.5.0"

  :repositories {"project" "file:repo"}

  :dependencies
  [
   [local/simba-spark-jdbc42 "2.6.22"]
  ]

  ;; only used for the lein with-drivers stuff (i.e. tests and REPL)
  :aot [metabase.driver.FixedHiveDriver]

  :profiles
  {:provided
   {:dependencies
    [
      [metabase-core "1.0.0-SNAPSHOT"]
      [clojure.java-time "0.3.1"]
    ]
    }

   :uberjar
   {:auto-clean    true
    :aot           :all
    :javac-options ["-target" "1.8", "-source" "1.8"]
    :target-path   "target/%s"
    :uberjar-name  "sparksql-databricks.metabase-driver.jar"}})
