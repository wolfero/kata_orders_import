CREATE SCHEMA IF NOT EXISTS ordersschema;
CREATE TABLE ordersschema.orders (
    uuid UUID PRIMARY KEY,
    id VARCHAR(255),
    region VARCHAR(255),
    country VARCHAR(255),
    item_type VARCHAR(255),
    sales_channel VARCHAR(255),
    priority VARCHAR(255),
    date DATE,
    ship_date DATE,
    units_sold BIGINT,
    unit_price NUMERIC(10, 2),
    unit_cost NUMERIC(10, 2),
    total_revenue NUMERIC(15, 2),
    total_cost NUMERIC(15, 2),
    total_profit NUMERIC(15, 2),
    self VARCHAR(1024)
);