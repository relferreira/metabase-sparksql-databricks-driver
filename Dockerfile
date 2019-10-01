FROM metabase/metabase-head:latest

ENV MB_DB_CONNECTION_TIMEOUT_MS=60000

COPY sparksql-databricks.metabase-driver.jar /app/plugins/
