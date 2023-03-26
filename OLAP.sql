-- SELECT 
--     DATE_FORMAT(DATE_SUB(pt_date_time, INTERVAL DAY(pt_date_time)-1 DAY), '%Y-%m') AS month, 
--     SUM(pt_amount) AS revenue
-- FROM Payment
-- WHERE pt_date_time BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW()
-- GROUP BY month;

-- SELECT 
--     cus_id, 
--     CONCAT(cus_firstname, ' ', cus_lastname) AS customer_name, 
--     SUM(cp_quantity * cp_price) AS total_spent
-- FROM Customer
-- JOIN Cart ON Customer.cus_cart_id = Cart.cart_id
-- JOIN Cart_Product_List ON Cart.cart_id = Cart_Product_List.cp_cart_id
-- GROUP BY cus_id
-- ORDER BY total_spent DESC
-- LIMIT 10;

-- SELECT 
--     Category.cat_name, 
--     SUM(cp_quantity) AS total_sold
-- FROM Category
-- JOIN Product ON Category.cat_id = Product.p_cat_id
-- JOIN Cart_Product_List ON Product.p_id = Cart_Product_List.cp_pid
-- GROUP BY Category.cat_name;

-- SELECT 
--     Delivery_Partner.dp_id, 
--     CONCAT(dp_firstname, ' ', dp_lastname) AS delivery_partner_name, 
--     COUNT(CASE WHEN order_delivery_status = 'Delivered on time' THEN 1 END) AS delivered_on_time, 
--     COUNT(CASE WHEN order_delivery_status = 'Delivered late' THEN 1 END) AS delivered_late
-- FROM Delivery_Partner
-- JOIN _Order ON Delivery_Partner.dp_id = _Order.order_dp_id
-- GROUP BY Delivery_Partner.dp_id;

-- SELECT c.cus_id, c.cus_username, c.cus_firstname, c.cus_lastname, 
--        SUM(cpl.cp_quantity * p.p_cost) AS total_spent
-- FROM Customer c
-- JOIN Cart_Product_List cpl ON cpl.cp_cart_id = c.cus_cart_id
-- JOIN Product p ON p.p_id = cpl.cp_pid
-- GROUP BY c.cus_id, c.cus_username, c.cus_firstname, c.cus_lastname
-- ORDER BY total_spent DESC
-- LIMIT 10;

-- CREATE TRIGGER update_wallet
-- AFTER INSERT ON Payment
-- FOR EACH ROW
-- UPDATE Customer SET cus_wallet = cus_wallet - NEW.pt_amount
-- WHERE Customer.cus_id = (SELECT Places.pl_cus_id FROM Places WHERE Places.pl_order_id = NEW.pt_id);


-- CREATE TRIGGER update_stock
-- AFTER INSERT ON Cart_Product_List
-- FOR EACH ROW
-- UPDATE Product SET p_stock = p_stock - NEW.cp_quantity
-- WHERE Product.p_id = NEW.cp_pid;

-- DELIMITER //

-- CREATE TRIGGER before_insert_cart_product_list
-- BEFORE INSERT ON Cart_Product_List
-- FOR EACH ROW
-- BEGIN
--     DECLARE stock INT;
--     SELECT p_stock INTO stock FROM Product WHERE p_id = NEW.cp_pid;
--     IF stock < NEW.cp_quantity THEN
--         SIGNAL SQLSTATE '45000' 
--             SET MESSAGE_TEXT = 'Not enough stock available';
--     END IF;
-- END//

-- DELIMITER ;

-- SELECT *
-- FROM cart_product_list;

-- SELECT * 
-- FROM Product;

-- UPDATE Product
-- SET p_stock=9
-- WHERE p_id=99;

-- insert into Cart_Product_List (cp_id, cp_quantity, cp_cart_id, cp_price, cp_pid) values (1002, 9, 19, 7500, 99);

-- customer with most $$$ spent
SELECT 
    cus_id, 
    cus_firstname, cus_lastname,
    SUM(cp_quantity * cp_price) AS total_spent
FROM Customer
JOIN Cart ON Customer.cus_cart_id = Cart.cart_id
JOIN Cart_Product_List ON Cart.cart_id = Cart_Product_List.cp_cart_id
GROUP BY cus_id
ORDER BY total_spent DESC
LIMIT 50;

-- Trending Categories

SELECT 
    Category.cat_name, 
    SUM(cp_quantity) AS total_in_cart
FROM Category
JOIN Product ON Category.cat_id = Product.p_cat_id
JOIN Cart_Product_List ON Product.p_id = Cart_Product_List.cp_pid
GROUP BY Category.cat_name
ORDER BY total_in_cart DESC
LIMIT 10;

-- Trending Products

SELECT p.p_name, COUNT(DISTINCT cpl.cp_cart_id) as cart_count
FROM Product p
JOIN Cart_Product_List cpl ON p.p_id = cpl.cp_pid
GROUP BY p.p_id
ORDER BY cart_count DESC;

-- Get the total sales revenue for each category:

SELECT c.cat_name, SUM(cp.cp_price * cp.cp_quantity) AS total_revenue
FROM Category c
JOIN Product p ON c.cat_id = p.p_cat_id
JOIN Cart_Product_List cp ON p.p_id = cp.cp_pid
GROUP BY c.cat_name
ORDER BY total_revenue DESC;

-- top 10 delivery partner with the most number of orders

SELECT dp.dp_firstname, dp.dp_lastname, COUNT(o.order_id) AS num_deliveries
FROM Delivery_Partner dp
JOIN _Order o ON dp.dp_id = o.order_dp_id
GROUP BY dp.dp_id
ORDER BY num_deliveries DESC
LIMIT 10;

select * from Customer;

select * from Admin;

select * from Delivery_Partner;

select * from Product;

UPDATE Product SET p_stock = p_stock + 12 WHERE p_id=1;
SELECT * FROM Delivery_Partner WHERE dp_username ="ddansken2";

Select * FROM Delivery_Partner WHERE dp_id=11;

insert into Admin (admin_id, admin_username, admin_password) values (100000, 'a', 'a');
insert into Seller (s_id, s_username, s_password, s_firstname, s_lastname, s_mobile, s_email, s_city, S_pin_code) values (100000, 'a', 'a', 'Boigie', 'Tschursch', 9237956099, 'btschursch5@cloudflare.com', 'Enskededalen', '121 32');
insert into Delivery_Partner (dp_id, dp_username, dp_password, dp_firstname, dp_lastname, dp_mobile, dp_email, dp_city, dp_pin_code) values (100000, 'a', 'a', 'Arluene', 'Cutchee', 5737982881, 'acutchee2m@cdc.gov', 'Mosoc Llacta', null);
insert into Customer (cus_id, cus_username, cus_password, cus_firstname, cus_lastname, cus_mobile, cus_email, cus_street, cus_city, cus_pin_code, cus_cart_id, cus_sub_id, cus_wallet) values (100000, 'a', 'a', 'Natalina', 'Hanscombe', 5811320105, 'nhanscombe6c@blogs.com', 'Clemons', 'Šoštanj', '3325', 100000, 100000, 614848);
insert into Cart (cart_id, total_price) values (100000, 197922);
insert into Subscription (sub_id, sub_name) values (100000, 'gold');

delete from delivery_partner where dp_id=100000;

select * from Delivery_Partner where dp_id=100000;
select * from Delivery_Partner;