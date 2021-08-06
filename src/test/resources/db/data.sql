INSERT INTO customer (id, customer_name) VALUES (1001, 'Tom Hanks');
INSERT INTO customer (id, customer_name) VALUES (1002, 'Spicy Peter');

INSERT INTO customer_phone_number (customer_id, phone_number, is_active) VALUES (1001, '0303 987 786', true);
INSERT INTO customer_phone_number (customer_id, phone_number, is_active) VALUES (1001, '0404 123 345', false);

INSERT INTO customer_phone_number (customer_id, phone_number, is_active) VALUES (1002, '0303 345 567', false);
INSERT INTO customer_phone_number (customer_id, phone_number, is_active) VALUES (1002, '0405 456 768', true);


