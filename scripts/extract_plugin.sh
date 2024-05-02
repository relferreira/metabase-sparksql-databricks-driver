SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)

docker buildx build --build-arg METABASE_VERSION=v0.49.7 --target stg_export --platform "linux/amd64" -t metabase:databricks-plugin "$SCRIPT_DIR/.."
container_id=$(docker create "metabase:databricks-plugin" /bin/bash)
docker cp "$container_id:/sparksql-databricks-v2.metabase-driver.jar" "$SCRIPT_DIR/../dist/databricks-sql.metabase-driver.jar"
docker rm "$container_id"