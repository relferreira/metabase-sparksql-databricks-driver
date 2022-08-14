# Metabase Driver: Spark Databricks

**Support**: This driver currently only works with Databricks Cluster's Endpoint. SQL Warehouse Endpoints are not supported yet.

**Credits**: This repository is only an updated version of the work of Fernando Goncalves and Rajesh Kumar Ravi. 

All you need you do is drop the driver in your `plugins/` directory. You can grab it [here](https://github.com/relferreira/metabase-sparksql-databricks-driver/releases/download/1.3.0/sparksql-databricks.metabase-driver.jar) or build it yourself:

## Building the driver (the fast way)

Use the `Dockerfile` on this repo:

```bash
curl -L "https://github.com/relferreira/metabase-sparksql-databricks-driver/releases/download/1.3.0/sparksql-databricks.metabase-driver.jar" -o sparksql-databricks.metabase-driver.jar
mkdir target
mv sparksql-databricks.metabase-driver.jar target/
docker build -t metabase:metabase-head-databricks-1.3.0 .
```

And you can deploy to some docker registry of your own and use the image!

Example of running:
- `docker run -d -p 3000:3000 --name metabase metabase:metabase-head-databricks-1.3.0`

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

*or:*

```bash
mkdir -p /path/to/metabase/plugins
cp target/sparksql-databricks.metabase-driver.jar /path/to/metabase/plugins/
cd /path/to/metabase_source
lein run
```
