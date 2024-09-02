# Metabase Driver: Spark Databricks
So the credits are a bit complicated, but originally, this driver was developed by Fernando Goncalves and Rajesh Kumar Ravi. Their original
repository is no longer around. However, github user [relferreira](https://github.com/relferreira), kindly updates it [here](https://github.com/relferreira/metabase-sparksql-databricks-driver/tree/master). However, his solution
does not allow for OAuth Secrets, which was something solved by [shrodingers](https://github.com/shrodingers) at [Brigad](https://github.com/Brigad/metabase-sparksql-databricks-driver).

Thus, this work is a combination of two somewhat actively maintained repositories. All that I do is to merge the two solutions and update the driver to work with Metabase 0.50.23.

## Installation

To build a dockerized Metabase including the Databricks driver from this repository, simply run:

```
docker build -t metabase:0.50.23-databricks -f Dockerfile .
```

The Metabase Databricks driver gets build and included in a final Metabase docker image.
