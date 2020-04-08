FROM metabase/metabase:v0.35.1

ENV MB_DB_CONNECTION_TIMEOUT_MS=60000

COPY sparksql-databricks.metabase-driver.jar /plugins/
