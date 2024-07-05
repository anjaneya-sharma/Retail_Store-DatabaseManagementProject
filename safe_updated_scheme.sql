CREATE TABLE Admin(
	admin_id INT NOT NULL UNIQUE,
    admin_username VARCHAR(255) NOT NULL UNIQUE,
    admin_password VARCHAR(255) NOT NULL,
    PRIMARY KEY (admin_id)
);

CREATE TABLE Payment(
	pt_id INT NOT NULL UNIQUE,
    pt_amount INT DEFAULT 0,
    pt_date_time DATE,
    PRIMARY KEY (pt_id),
    
    CONSTRAINT amt CHECK (pt_amount>0)
);

CREATE TABLE Places(
	pl_cus_id INT NOT NULL,
    pl_order_id INT NOT NULL UNIQUE,
    pl_cart_id INT NOT NULL UNIQUE
);

CREATE TABLE Pays(
	pys_pt_id INT NOT NULL UNIQUE,
    pys_cus_id INT NOT NULL,
    pys_order_id INT NOT NULL UNIQUE
);

CREATE TABLE Cart(
	cart_id INT NOT NULL UNIQUE ,
	total_price INT DEFAULT 0,
    PRIMARY KEY(cart_id),
    
    CONSTRAINT prc CHECK (total_price>0)
);

CREATE TABLE Category(
	cat_id INT NOT NULL UNIQUE,
    cat_name VARCHAR(255) NOT NULL ,
    PRIMARY KEY (cat_id)
);

CREATE TABLE Seller(
	s_id INT NOT NULL UNIQUE,
    s_username VARCHAR(255) NOT NULL UNIQUE,
    s_password VARCHAR(255) NOT NULL,
    
	s_firstname VARCHAR(255) NOT NULL,
	s_lastname VARCHAR(255) NOT NULL,
    s_mobile BIGINT NOT NULL,
    s_email VARCHAR(255) NOT NULL,
    s_city VARCHAR(255) NOT NULL,
    s_pin_code VARCHAR(25),
    
    PRIMARY KEY (s_id)
);

CREATE TABLE Delivery_Partner(
	dp_id INT NOT NULL UNIQUE,
    dp_username VARCHAR(255) NOT NULL UNIQUE,
    dp_password VARCHAR(255) NOT NULL,
    
    dp_salary INT NOT NULL,

	dp_firstname VARCHAR(255) NOT NULL,
	dp_lastname VARCHAR(255) NOT NULL,
    dp_mobile BIGINT NOT NULL,
    dp_email VARCHAR(255) NOT NULL,
    dp_city VARCHAR(255) NOT NULL,
    dp_pin_code VARCHAR(25),
    PRIMARY KEY (dp_id),
	CONSTRAINT salary_check CHECK(dp_salary>0)
);

CREATE TABLE Subscription(
	sub_id INT NOT NULL UNIQUE,
    sub_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (sub_id)
);


CREATE TABLE Product(
	p_id INT NOT NULL UNIQUE,
    p_name VARCHAR(255) NOT NULL,
	p_cost INT NOT NULL,
    p_descrip VARCHAR(500),
    p_stock INT DEFAULT 0,
    p_cat_id INT NOT NULL,
    PRIMARY KEY (p_id),
    FOREIGN KEY (p_cat_id) REFERENCES Category(cat_id),
    
    
    CONSTRAINT price_check CHECK (p_cost>0 and p_stock>=0)
);

CREATE TABLE Cart_Product_List(
	cp_id INT NOT NULL UNIQUE,
    cp_quantity INT,
    cp_cart_id INT NOT NULL,
    cp_price INT ,
    cp_pid INT NOT NULL, 
    PRIMARY KEY(cp_id),
    FOREIGN KEY(cp_pid) REFERENCES Product (p_id),
    FOREIGN KEY(cp_cart_id) REFERENCES CART(cart_id),
    
    CONSTRAINT price_check2 CHECK (cp_price>0 and cp_quantity>0)
);

CREATE TABLE _Order (
	order_id INT NOT NULL UNIQUE,
    order_delivery_status VARCHAR(255) NOT NULL ,
    order_dp_id INT NOT NULL ,
    order_date  DATE NOT NULL ,
    order_ship_date DATE  NOT NULL ,
    order_cus_id INT NOT NULL,
    
     order_cus_firstname VARCHAR(255) NOT NULL,
	 order_cus_lastname VARCHAR(255) NOT NULL,
	 order_cus_mobile BIGINT NOT NULL,
	 order_cus_email VARCHAR(255) NOT NULL,
	 order_cus_street VARCHAR(255) NOT NULL,
	 order_cus_city VARCHAR(255) NOT NULL,
	 order_cus_pin_code VARCHAR(25),
    
    PRIMARY KEY(order_id),
    FOREIGN KEY(order_dp_id) REFERENCES Delivery_Partner(dp_id)
);
CREATE TABLE Customer(
	cus_id INT NOT NULL UNIQUE,
    cus_username VARCHAR(255) NOT NULL UNIQUE,
    cus_password VARCHAR(255) NOT NULL,
    
	cus_firstname VARCHAR(255) NOT NULL,
	cus_lastname VARCHAR(255) NOT NULL,
    cus_mobile BIGINT NOT NULL,
    cus_email VARCHAR(255) NOT NULL,
	cus_street VARCHAR(255) NOT NULL,
    cus_city VARCHAR(255) NOT NULL,
    cus_pin_code VARCHAR(25),            
    
    cus_cart_id INT NOT NULL UNIQUE,
    cus_sub_id INT NOT NULL UNIQUE,
    
    cus_wallet INT NOT NULL ,
    
    PRIMARY KEY (cus_id),
    FOREIGN KEY (cus_cart_id) REFERENCES Cart(cart_id),
    FOREIGN KEY (cus_sub_id) REFERENCES Subscription(sub_id) ,
    
    CONSTRAINT wallet_ballance CHECK(cus_wallet>=0)
);
 
 
 -- Triggers 
delimiter //
CREATE TRIGGER ANOTHER_ONE
before insert on Customer
for each row 
set new.cus_wallet=new.cus_wallet+100;
end if; //
 
 
-- salary basic 
delimiter $$
CREATE TRIGGER basic_salary
BEFORE INSERT ON Delivery_Partner
for each row 
set new.dp_salary=10000;
end if; $$


drop trigger basic_salary;
drop trigger ANOTHER_ONE;
  
drop table Admin, _Order,Payment, Places, Pays, Cart_Product_List, Cart, Category, Seller, Delivery_Partner, Product, Subscription, Customer;
show tables;