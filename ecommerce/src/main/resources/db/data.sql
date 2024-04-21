--Shops
INSERT INTO ecommerce.shops (name) VALUES ('Lazada');
INSERT INTO ecommerce.shops (name) VALUES ('Shopee');
INSERT INTO ecommerce.shops (name) VALUES ('Q10');

--Lazada records
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (1, 'UEN123', 'Bluetooth Speaker', (select id from ecommerce.shops where name ='Lazada'), 5, 100.0);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (2, 'UEN783', 'Camera', (select id from ecommerce.shops where name ='Lazada'), 100, 105.0);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (3, 'UEN435', 'Tablet', (select id from ecommerce.shops where name ='Lazada'), 32, 620.0);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (4, 'UEN092', 'Laptop x1', (select id from ecommerce.shops where name ='Lazada'), 543, 3000.0);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (5, 'UEN8', 'Gaming Console', (select id from ecommerce.shops where name ='Lazada'), 32, 435.56);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (6, 'UEN12', 'Laptop x2', 3, (select id from ecommerce.shops where name ='Lazada'), 1589.23);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (7, 'UEN87', 'HDD', (select id from ecommerce.shops where name ='Lazada'), 52, 543.34);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (8, 'UEN93', 'SmartPhone', (select id from ecommerce.shops where name ='Lazada'), 0, 888.1);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (9, 'UEN11', 'Fan', (select id from ecommerce.shops where name ='Lazada'), 23, 89.0);
INSERT INTO products (external_id, uen, name, shop_id, quantity, price) VALUES (10, 'UEN5692', 'Monitor', (select id from ecommerce.shops where name ='Lazada'), 5, 7839.0);

--Shopee records
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(6, 'UEN54', 'Shirt', (select id from ecommerce.shops where name ='Shopee'), 23.32,4);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(7, 'UEN7', 'Shorts', (select id from ecommerce.shops where name ='Shopee'), 1.05,25);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(2, 'UEN49', 'Jeans', (select id from ecommerce.shops where name ='Shopee'), 62, 63);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(1, 'UEN01', 'Tops', (select id from ecommerce.shops where name ='Shopee'), 214, 3);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(4, 'UEN81', 'Dress', (select id from ecommerce.shops where name ='Shopee'), 332, 2.1);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(5, 'UEN14', 'Hat', (select id from ecommerce.shops where name ='Shopee'),0,0.23);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(9, 'UEN88', 'Shoes', (select id from ecommerce.shops where name ='Shopee'), 0, 5.24);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (8, 'UEN99', 'Socks', (select id from ecommerce.shops where name ='Shopee'), 55, 21.3);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES(10, 'UEN111', 'Glasses', (select id from ecommerce.shops where name ='Shopee'), 23, 89);
INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (3, 'UEN58', 'Cap', (select id from ecommerce.shops where name ='Shopee'), 5, 1);

--Q10 records

    INSERT INTO products (external_id, uen, name, shop_id, price, quantity)VALUES (1, 'UEN123', 'Plants', (SELECT id from ecommerce.shops WHERE name = 'Q10'), 22, 20);
    INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (2, 'UEN76', 'Curtain', (SELECT id from ecommerce.shops WHERE name = 'Q10'), 105, 100);
    INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (3, 'UEN4', 'Fridge', (SELECT id from ecommerce.shops WHERE name = 'Q10'), 620, 32);
    INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (4, 'UEN123', 'Oven', (SELECT id from ecommerce.shops WHERE name = 'Q10'), 3000, 543 );
    INSERT INTO products (external_id, uen, name, shop_id, price, quantity) VALUES (5, 'UEN58', 'Cap', (SELECT id from ecommerce.shops WHERE name = 'Q10'), 453.56,32 );
