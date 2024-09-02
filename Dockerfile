#Copied from https://github.com/dacort/metabase-athena-driver/blob/d7572cd99551ea998a011f8f00a1e39c1eaa59b8/Dockerfile
ARG METABASE_VERSION=v0.50.23

FROM clojure:openjdk-11-tools-deps-slim-buster AS stg_base

# Reequirements for building the driver
RUN apt-get update && \
    apt-get install -y \
    curl \
    make \
    unzip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set our base workdir
WORKDIR /build

# We need to retrieve metabase source
# Due to how ARG and FROM interact, we need to re-use the same ARG
# Ref: https://docs.docker.com/engine/reference/builder/#understand-how-arg-and-from-interact
ARG METABASE_VERSION
RUN curl -Lo - https://github.com/metabase/metabase/archive/refs/tags/${METABASE_VERSION}.tar.gz | tar -xz \
    && mv metabase-* metabase

# Driver source goes inside metabase source so we can use their build scripts
WORKDIR /build/driver

# Copy our project assets over
COPY deps.edn ./
COPY project.clj ./
COPY src/ ./src
COPY resources/ ./resources

WORKDIR /build/metabase

# Now build the driver
FROM stg_base as stg_build
RUN clojure \
    -Sdeps "{:aliases {:sparksql-databricks-v2 {:extra-deps {com.metabase/sparksql-databricks {:local/root \"/build/driver\"}}}}}" \
    -X:build:sparksql-databricks-v2 \
    build-drivers.build-driver/build-driver! \
    "{:driver :sparksql-databricks-v2, :project-dir \"/build/driver\", :target-dir \"/build/driver/target\"}"

# We create an export stage to make it easy to export the driver
FROM scratch as stg_export
COPY --from=stg_build /build/driver/target/sparksql-databricks-v2.metabase-driver.jar /

# Now we can run Metabase with our built driver
FROM metabase/metabase:${METABASE_VERSION} AS stg_runner

# A metabase user/group is manually added in https://github.com/metabase/metabase/blob/master/bin/docker/run_metabase.sh
# Make the UID and GID match
COPY --chown=2000:2000 --from=stg_build \
    /build/driver/target/sparksql-databricks-v2.metabase-driver.jar \
    /plugins/sparksql-databricks.metabase-driver.jar

RUN wget https://github.com/relferreira/metabase-sparksql-databricks-driver/releases/download/1.6.0/sparksql-databricks.metabase-driver.jar -O /plugins/sparksql-databricks.metabase-driver-old.jar
