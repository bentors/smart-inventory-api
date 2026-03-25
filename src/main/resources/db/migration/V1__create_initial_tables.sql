-- V1__create_initial_tables.sql

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       login VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE products (
                          id UUID PRIMARY KEY,
                          sku VARCHAR(100) NOT NULL UNIQUE,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          cost DECIMAL(10, 2) NOT NULL,
                          current_stock INTEGER NOT NULL,
                          min_stock_alert INTEGER NOT NULL,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);

CREATE TABLE stock_movements (
                                 id UUID PRIMARY KEY,
                                 product_id UUID NOT NULL,
                                 quantity INTEGER NOT NULL,
                                 type VARCHAR(50) NOT NULL,
                                 reason VARCHAR(255),
                                 created_at TIMESTAMP NOT NULL,

                                 CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);