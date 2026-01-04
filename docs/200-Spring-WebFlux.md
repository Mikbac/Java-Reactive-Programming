# Spring WebFlux

## Spring Data R2DBC (Reactive Relational Database Connectivity)

JPA is for blocking, synchronous database access. In reactive applications we should use R2DBC.

Priorities:

* Non-blocking
* Reactive streams support
* Backpressure support

https://r2dbc.io/drivers/

Supported databases:

* clickhouse-r2dbc
* cloud-spanner-r2dbc
* jasync-sql (R2DBC wrapper for Java & Kotlin Async Database Driver for MySQL and PostgreSQL)
* oracle-r2dbc
* r2dbc-h2
* r2dbc-mariadb
* r2dbc-mssql
* r2dbc-mysql
* r2dbc-postgresql

Data types mapping: https://r2dbc.io/spec/1.0.0.RELEASE/spec/html/#datatypes.mapping
