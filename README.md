# Kata Orders Importer Basic

The objective of this kata is import into a database, a list of orders. At the Basic level, the origin of the orders
will be CSV files with a fixed structure.

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

1. Navigate to the `resources` folder within the project.
2. Place the files you want to import into the `resources/import` folder.
3. Open the `Main.java` file and modify the `importCsvPath` variable to point to the path of the file you wish to
   import. Make sure the file is in `.csv` format.

```
// In Main.java
String importCsvPath = "/import/your_imported_file.csv";
```

## Running the Project

Once the database is up and running, and you've configured the import file path, run the project `Main`.

## Exporting Data

If everything has been set up and executed correctly, the exported file should be created in the `resources/export`
directory.
