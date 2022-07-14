# Metabase Driver: Spark Databricks

**Credits**: This repository is only an updated version of the work of Fernando Goncalves and Rajesh Kumar Ravi. 

All you need you do is drop the driver in your `plugins/` directory. You can grab it [here](https://github.com/ifood/metabase-sparksql-databricks-driver/releases/download/1.1.0/sparksql-databricks.metabase-driver.jar) or build it yourself:

## Building the driver (the fast way)

Use the `Dockerfile` on this repo:

- `curl -L "https://github.com/relferreira/metabase-sparksql-databricks-driver/releases/download/1.2.0/sparksql-databricks.metabase-driver.jar" -o sparksql-databricks.metabase-driver.jar`
- `docker build -t metabase:metabase-head-databricks-1.2.0 .`

And you can deploy to some docker registry of your own and use the image!

Example of running:
- `docker run -d -p 3000:3000 --name metabase metabase:metabase-head-databricks-1.2.0`

And access `http://localhost:3000`.

## Building the driver (advanced way)

### Download Simba Spark Driver

Download the simba spark driver version 2.6.22 at [Databricks download page](https://databricks.com/spark/jdbc-drivers-archive) and move the `SparkJDBC42.jar` file to the `/driver` folder at the root of the project. 
After that, install this dependency using the following command:

```bash
mvn deploy:deploy-file -DgroupId=local -DartifactId=simba-spark-jdbc42 \
    -Dversion=2.6.27 -Dpackaging=jar -Dfile=driver/SparkJDBC42.jar \
    -Durl=file:repo
```

### Prereq: Install Metabase as a local maven dependency, compiled for building drivers

Clone the [Metabase repo](https://github.com/metabase/metabase) first if you haven't already done so.

```bash
cd /path/to/metabase/
lein install-for-building-drivers
```

### Build the Spark Databricks driver

```bash
# (In the sparksql-databricks driver directory)
lein clean
DEBUG=1 LEIN_SNAPSHOTS_IN_RELEASE=true lein uberjar
```

### Copy it to your plugins dir and restart Metabase

```bash
mkdir -p /path/to/metabase/plugins/
cp target/uberjar/sparksql-databricks.metabase-driver.jar /path/to/metabase/plugins/
jar -jar /path/to/metabase/metabase.jar
```

*or:*

```bash
mkdir -p /path/to/metabase/plugins
cp target/uberjar/sparksql-databricks.metabase-driver.jar /path/to/metabase/plugins/
cd /path/to/metabase_source
lein run
```
