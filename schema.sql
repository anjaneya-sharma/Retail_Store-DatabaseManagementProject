CREATE DATABASE Online_Retail_Store;

USE Online_Retail_Store;

CREATE TABLE Admin(
	
	Admin_ID INT NOT NULL UNIQUE,
    Admin_Username VARCHAR(255) NOT NULL,
    Admin_Password VARCHAR(255) NOT NULL,
    PRIMARY KEY (admin_id)
);

CREATE TABLE Seller(
	s_id INT NOT NULL UNIQUE,
    s_username VARCHAR(255) NOT NULL,
    s_password VARCHAR(255) NOT NULL,
    PRIMARY KEY (s_id)
);

CREATE TABLE Delivery_Partner(
	dp_id INT NOT NULL UNIQUE,
    dp_username VARCHAR(255) NOT NULL,
    dp_password VARCHAR(255) NOT NULL,
    dp_det_id INT NOT NULL UNIQUE,
    PRIMARY KEY (dp_id),
    FOREIGN KEY (dp_det_id) REFERENCES Personal_Details (det_id)
);

CREATE TABLE Customer(
	cus_id INT NOT NULL UNIQUE,
    cus_username VARCHAR(255) NOT NULL,
    cus_password VARCHAR(255) NOT NULL,
    cus_cart_id INT NOT NULL UNIQUE,
    cus_sub_id INT NOT NULL UNIQUE,
    PRIMARY KEY (cus_id),
    FOREIGN KEY (cus_cart_id) REFERENCES Personal_Details(det_id),
    FOREIGN KEY (cus_sub_id) REFERENCES Subscription(sub_id) 
);

CREATE TABLE Personal_Details(
	det_id INT NOT NULL UNIQUE,
    det_firstname VARCHAR(255) NOT NULL,
	det_lastname VARCHAR(255) NOT NULL,
    mobile VARCHAR(10) NOT NULL,
    email VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    pin_code VARCHAR(6) NOT NULL,
    PRIMARY KEY (det_id)
);

CREATE TABLE Subscription(
	sub_id INT NOT NULL UNIQUE,
    sub_name VARCHAR(255) NOT NULL UNIQUE,
    sub_cus_id INT NOT NULL UNIQUE,
    PRIMARY KEY (sub_id),
    FOREIGN KEY (sub_cus_id) REFERENCES Customer (cus_id)
);

CREATE TABLE Category(
	cat_id INT NOT NULL UNIQUE,
    cat_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (cat_id)
);

CREATE TABLE Cart(
	cart_id INT NOT NULL UNIQUE ,
	total_price INT DEFAULT 0,
    PRIMARY KEY(cart_id)
);

CREATE TABLE Cart_Product_List(
	cp_id INT NOT NULL UNIQUE,
    cp_quantity INT ,
    cp_price INT ,
    cp_pid INT NOT NULL UNIQUE,
    PRIMARY KEY(cp_id),
    FOREIGN KEY(cp_pid) REFERENCES Product (p_id)
);