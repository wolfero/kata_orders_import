# Kata Orders Importer Basic

The objective of this kata is import to a database, the list of online orders. The list of orders is available in a REST
API (public). The application must query this API to obtain all the orders to be imported.

```
https://kata-espublicotech.g3stiona.com:443/v1/orders
```

First call:
```
https://kata-espublicotech.g3stiona.com:443/v1/orders?page=1&max-per-page=1000
```

I used the next parameters:
1. ``page=1`` to bring first page of orders
2. ``max-per-page=1000`` to bring 1000 orders on one page. The range from this parameter is 1 to 1000.


## Functionality

Regardless of the origin of the orders, upon completing the import, it should display a summary of the number of orders
of each type according to different columns.

In addition, it should generate a file with the records sorted by order number. The field by which the resulting file
must be sorted is orderId. In the final summary, the count for each type of the fields should appear: Region, Country,
Item Type, Sales Channel, Order Priority.

## Format of the exported file

The resulting file must be a CSV, with the following columns:

- Order ID
- Order Priority
- Order Date
- Country
- Region
- Item Type
- Sales Channel
- Ship Date
- Units Sold
- Unit Price
- Unit Cost
- Total Revenue
- Total Cost
- Total Profit

## Prerequisites

- [Java](https://www.java.com/en/download/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

## Initial Setup

### Docker and Database

Before running the project, ensure Docker is running. Then, start the database by executing the following command in the
project root:

```bash
docker-compose up -d
```

This command will spin up the database needed for the project to run correctly. You can verify that the containers are
running with the following command:

```bash
docker ps
```

### Data Import Configuration

### !! WARNING, THE API HAS MORE THAN HALF A MILLION RECORDS THAT WILL BE LOADED INTO THE DATABASE AND THEN INTO THE DUMP FILE !!

To load all orders we need change in `OrderService` file the line 37.

```
change orderCount < 4
```

for 

```
apiOrders.getLinks().getNext() != null
```

## Running the Project

Once the database is up and running, and you've configured the import file path, run the project `Main`.

## Exporting Data

If everything has been set up and executed correctly, the exported file should be created in the `resources/export`
directory.
