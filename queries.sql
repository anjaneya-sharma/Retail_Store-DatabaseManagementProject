-- just to see results for 1
SELECT *
FROM Product
WHERE p_cat_id= (SELECT cat_id
			     FROM Category
				 WHERE cat_name='Cheese - Romano, Grated');

-- QUERY for 1
UPDATE Product
SET p_cost = 0.5*p_cost
WHERE p_cat_id= (SELECT cat_id
			     FROM Category
				 WHERE cat_name='Cheese - Romano, Grated');
              

-- just to see results for 2
SELECT *
FROM Product
WHERE p_id>400 AND p_id<415;

-- QUERY for 2
UPDATE Product
SET p_stock = p_stock+50
WHERE p_id>400 AND p_id<416;

-- QUERY for 3
SELECT *
FROM Product
WHERE p_cat_id = (SELECT cat_id
				  FROM Category
				  WHERE cat_name='Poppy Seed');                  

-- QUERY for 5
SELECT *
FROM Payment
WHERE pt_id IN (SELECT order_id
			   FROM _Order
			   WHERE order_cus_id= (SELECT cus_id
									FROM Customer
									WHERE cus_username='mborles1'))
ORDER BY pt_amount DESC;

-- QUERY for 6
SELECT *
FROM _Order
WHERE order_dp_id=(SELECT dp_id
				   FROM Delivery_partner
				   WHERE dp_username='elindsley1')
ORDER BY order_ship_date ASC;