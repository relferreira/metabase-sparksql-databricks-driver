(ns metabase.driver.hive-like-test
  (:require [expectations :refer [expect]]
            [metabase.driver.sql-jdbc.sync :as sql-jdbc.sync]
            [metabase.driver.sql.query-processor :as sql.qp]
            [metabase.util.honeysql-extensions :as hx]
            [honeysql.core :as hsql]
            ))

;; make sure the various types we use for running tests are actually mapped to the correct DB type
(expect :type/Text     (sql-jdbc.sync/database-type->base-type :hive-like :string))
(expect :type/Integer  (sql-jdbc.sync/database-type->base-type :hive-like :int))
(expect :type/Integer  (sql-jdbc.sync/database-type->base-type :hive-like :INT))
(expect :type/Date     (sql-jdbc.sync/database-type->base-type :hive-like :date))
(expect :type/DateTime (sql-jdbc.sync/database-type->base-type :hive-like :timestamp))
(expect :type/Float    (sql-jdbc.sync/database-type->base-type :hive-like :double))

(expect 
  (hx/+ (hx/->timestamp :%now) (hsql/raw "(INTERVAL '3' month)"))
  (sql.qp/add-interval-honeysql-form :hive-like :%now 1 :quarter))

(expect 
  (hx/+ (hx/->timestamp :%now) (hsql/raw "(INTERVAL '6' month)"))
  (sql.qp/add-interval-honeysql-form :hive-like :%now 2 :quarter))

(expect 
  (hx/+ (hx/->timestamp :%now) (hsql/raw "(INTERVAL '1' year)"))
  (sql.qp/add-interval-honeysql-form :hive-like :%now 1 :year))
