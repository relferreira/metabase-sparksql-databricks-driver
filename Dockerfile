FROM metabase/metabase-head:latest

ENV MB_DB_CONNECTION_TIMEOUT_MS=60000

COPY ./target/uberjar/sparksql-databricks.metabase-driver.jar /plugins/
