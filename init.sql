CREATE SCHEMA IF NOT EXISTS ordersschema;
CREATE TABLE ordersschema.orders (
    order_id SERIAL PRIMARY KEY,
    order_priority VARCHAR(255),
    order_date DATE,
    region VARCHAR(255),
    country VARCHAR(255),
    item_type VARCHAR(255),
    sales_channel VARCHAR(255),
    ship_date DATE,
    units_sold INT,
    unit_price DECIMAL(15, 2),
    unit_cost DECIMAL(15, 2),
    total_revenue DECIMAL(15, 2),
    total_cost DECIMAL(15, 2),
    total_profit DECIMAL(15, 2)
);