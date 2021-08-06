DROP TABLE IF EXISTS customer;

DROP TABLE IF EXISTS customer_phone_number;

CREATE TABLE customer (
  id DECIMAL(10)  NOT NULL PRIMARY KEY,
  customer_name VARCHAR(100) NOT NULL
);

CREATE TABLE customer_phone_number (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  customer_id DECIMAL(10)  NOT NULL,
  phone_number VARCHAR(30) NOT NULL,
  is_active BOOLEAN NOT NULL,
  CONSTRAINT customer_phone_number_fk_1 FOREIGN KEY (customer_id) REFERENCES customer (id) ON UPDATE CASCADE
);


CREATE INDEX customer_phone_number_idx1 ON customer_phone_number (customer_id);

CREATE UNIQUE INDEX customer_phone_number_idx_2 ON customer_phone_number(phone_number);
