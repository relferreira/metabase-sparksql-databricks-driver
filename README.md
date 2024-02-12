# Metabase Driver: Spark Databricks

**Credits**: This repository is only an updated version of the work of Fernando Goncalves and Rajesh Kumar Ravi.

## Installation

To build a dockerized Metabase including the Databricks driver from this repository, simply run:

```
docker build -t metabase:0.46.6.2-databricks -f Dockerfile .
```

The Metabase Databricks driver gets build and included in a final Metabase docker image.

### To be fixed for >= v0.46:

To run the tests for this driver, run the following:

```
docker build -t metabase/databricks-test --target stg_test .
docker run --rm --name mb-test metabase/databricks-test
```

or, if you have Clojure on your local machine, just:

```
clojure -X:test
```

# Connecting

## Parameters

![Connection Parameters](docs/parameters.png)

- Display Name: a identification name for your database in Metabase
- Host: your Databricks URL (adb-XXXXXXXXX.azuredatabricks.net)
- Databricks client id: The id of your service-principal
- Databricks OAuth Secret: Your OAuth2 secret for the service-principal
- HTTP Path: The path to the SQL Warehouse, usually `/sql/1.0/endpoints/***`
- Catalog : The name of the catalog you want to connect to
- Schema : If you want to restrict the connection to a specific database / schema
- Port : 443 by default, located in advanced settings
- Additional JDBC connection string options (SSL, transportMode etc...), located in advanced settings

## Building the driver (the fast way)

Use the `Dockerfile` on this repo:

```bash
docker build -t metabase:0.46.6.2-databricks .
```

This image contains both the original and updated driver versions

And you can deploy to some docker registry of your own and use the image!

Example of running:

```bash
docker run -d -p 3000:3000 --name metabase metabase:0.46.6.2-databricks
```

And access `http://localhost:3000`.

## Building the driver (advanced way)

### Prereq: Install Metabase as a local maven dependency, compiled for building drivers

Clone the [Metabase repo](https://github.com/metabase/metabase) first if you haven't already done so.

```bash
cd /path/to/metabase/
./bin/build
```

### Build the Spark Databricks driver

```bash
# (In the sparksql-databricks driver directory)
clojure -X:build :project-dir "\"$(pwd)\""
```

### Copy it to your plugins dir and restart Metabase

```bash
mkdir -p /path/to/metabase/plugins/
cp target/sparksql-databricks.metabase-driver.jar /path/to/metabase/plugins/
jar -jar /path/to/metabase/metabase.jar
```

_or:_

```bash
mkdir -p /path/to/metabase/plugins
cp target/sparksql-databricks.metabase-driver.jar /path/to/metabase/plugins/
cd /path/to/metabase_source
lein run
```
