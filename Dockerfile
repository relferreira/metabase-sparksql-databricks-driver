FROM metabase/metabase:v0.43.4

ENV MB_DB_CONNECTION_TIMEOUT_MS=60000

COPY ./target/sparksql-databricks.metabase-driver.jar /plugins/

RUN chmod 744 /plugins/sparksql-databricks.metabase-driver.jar