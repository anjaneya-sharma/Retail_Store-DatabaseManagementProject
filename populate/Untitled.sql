-- BROWSING ALL THE PRODUCTS OF A PARTICULAR CATEGORY  --->1
SELECT p_name
FROM Product
WHERE p_cat_id= 55;

-- Updating THE COST OF THE PRODUCTS    ----->2
UPDATE Product
SET p_cost = 0.5*p_cost 
WHERE p_cat_id= 76;

-- UPDATING THE STOCK OF THE PRODUCTS--------->3
UPDATE Product
SET p_stock = p_stock+50
WHERE p_id>400 AND p_id<416;


-- CHECKING PAYMENT DETAILS OF A PARTICULAR USER------->4
SELECT pt_id , pt_date_time
FROM Payment
WHERE pt_id IN (SELECT order_id
			   FROM _Order
			   WHERE order_cus_id= (SELECT cus_id
									FROM Customer
									WHERE cus_username='hternentp'))
ORDER BY pt_amount DESC;

-- CHECKING DELIVERY STATUS OF ALL ORDERS OF A PARTICULAR CUSTOMER  ------->5
SELECT order_id,order_delivery_status
FROM _ORDER
WHERE order_cus_id IN(SELECT cus_id
							FROM Customer
							WHERE cus_username='hternentp');

-- CHECKING LOCALITY NAMES WHERE A PARTICULAR DELIVERY PARTNER HAS TO DELIVER    ---------->**6
SELECT order_cus_street
FROM _Order
WHERE order_dp_id=(SELECT dp_id
				   FROM Delivery_partner
				   WHERE dp_username='elindsley1')
ORDER BY order_ship_date ASC;

-- REMOVING PARTICULAR DELIVERY PARTNERS , SELLERS  QUERY---------->7
DELETE FROM Customer WHERE cus_id IN(27,35);
DELETE FROM Seller WHERE s_id IN(25,35);




-- DELIVERY PARTNER UPDATES DELIVERY STATUS  QUERY------>8
UPDATE _Order
SET order_delivery_status='delivered'
WHERE order_id>15 and order_id<20;

-- CUSTOMER WALLET BALANCE UPDATE  QUERY--------->9
UPDATE Customer
SET cus_wallet=cus_wallet+10000
WHERE cus_id=150;

-- CHECKING SUMMARIZED PRODUCT DETAILS ALONG WITH IT'S CATEGORY    QUERY---------->10
SELECT p_id , p_name ,p_cost , cat_id , cat_name
FROM Product
INNER JOIN Category ON p_cat_id=cat_id;

-- CHECKING INFORMATION OF ALL THE STAKEHOLDERS AT ONCE  QUERY--------->11
SELECT cus_firstname , cus_lastname , cus_mobile , cus_city from Customer
UNION
SELECT dp_firstname , dp_lastname , dp_mobile  , dp_city from Delivery_Partner
UNION
SELECT s_firstname , s_lastname , s_mobile , s_city from Seller ; 




-- ERROR CHECKING QUERy  (DELETING PRIMARY KEY)    QUERRY---->12
DELETE FROM Delivery_Partner WHERE dp_id IN(20,25);

-- ERROR CHECKING QUERY (DELETING FOREIGN KEY)     QUERRY---->13
DELETE FROM Product WHERE p_cat_id IN(20,25);

-- ERROR CHECKING QUERY (ADDING OUT OF DOMAIN VALUES)   QUERRY---->14
insert into Cart (cart_id, total_price) values (1, -159);

-- ERROR CHECKING QUERY (ADDING NULL VALUES AS PRIMARY KEYS)  QUERRY----->15
insert into Category (cat_id, cat_name) values (null, 'Vinegar - Champagne');
SELECT user,authentication_string,plugin,host FROM mysql.user;

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;