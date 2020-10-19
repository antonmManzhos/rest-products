1) create db rest-products
2) sat properties to db in application.properties file
2) run these sql
CREATE TABLE Products (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price float8 not NULL,
    date DATE  not NULL
);

INSERT INTO Products (name, price, date)
VALUES('Test product 1', 9.99, '2020-10-18');