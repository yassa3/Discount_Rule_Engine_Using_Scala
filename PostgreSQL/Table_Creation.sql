CREATE TABLE orders (
    timestamp VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    expiry_date VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    average_discount DECIMAL(5, 2) NOT NULL,
    discounted_price DECIMAL(10, 2) NOT NULL
);
