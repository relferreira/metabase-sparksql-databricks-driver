(ns metabase.driver.connection-test
  (:require [expectations :refer [expect]]
            [metabase.driver.connection :as connection]))

(expect
 nil
 (connection/decorate-and-fix nil))

(expect
 some?
 (connection/decorate-and-fix {}))
