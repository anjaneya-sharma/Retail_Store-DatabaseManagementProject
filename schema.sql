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
    PRIMARY KEY (pt_id)
);

CREATE TABLE Places(
	pl_cus_id INT NOT NULL UNIQUE,
    pl_order_id INT NOT NULL UNIQUE,
    pl_cart_id INT NOT NULL UNIQUE,
    PRIMARY KEY(pl_cus_id, pl_order_id, pl_cart_id)
);

CREATE TABLE Pays(
	pys_pt_id INT NOT NULL UNIQUE,
    pys_cus_id INT NOT NULL UNIQUE,
    pys_order_id INT NOT NULL UNIQUE,
    PRIMARY KEY(pys_pt_id, pys_cus_id, pys_order_id)
);

CREATE TABLE Manages(
	m_admin_id INT NOT NULL UNIQUE,
    m_p_id INT NOT NULL UNIQUE,
    PRIMARY KEY(m_admin_id, m_p_id)
);

CREATE TABLE Is_Added_To(
    ias_p_id INT NOT NULL UNIQUE,
    ias_cart_id INT NOT NULL UNIQUE,
    PRIMARY KEY(ias_p_id, ias_cart_id)
);

CREATE TABLE Cart(
	cart_id INT NOT NULL UNIQUE ,
	total_price INT DEFAULT 0,
    PRIMARY KEY(cart_id)
);

CREATE TABLE Category(
	cat_id INT NOT NULL UNIQUE,
    cat_name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (cat_id)
);

CREATE TABLE Seller(
	s_id INT NOT NULL UNIQUE,
    s_username VARCHAR(255) NOT NULL UNIQUE,
    s_password VARCHAR(255) NOT NULL,
    
	s_firstname VARCHAR(255) NOT NULL,
	s_lastname VARCHAR(255) NOT NULL,
    s_mobile INT NOT NULL,
    s_email VARCHAR(255) NOT NULL,
    s_city VARCHAR(255) NOT NULL,
    s_pin_code VARCHAR(6) NOT NULL,
    
    PRIMARY KEY (s_id)
);

CREATE TABLE Delivery_Partner(
	dp_id INT NOT NULL UNIQUE,
    dp_username VARCHAR(255) NOT NULL UNIQUE,
    dp_password VARCHAR(255) NOT NULL,

	dp_firstname VARCHAR(255) NOT NULL,
	dp_lastname VARCHAR(255) NOT NULL,
    dp_mobile INT NOT NULL,
    dp_email VARCHAR(255) NOT NULL,
    dp_city VARCHAR(255) NOT NULL,
    dp_pin_code VARCHAR(6) NOT NULL,

    PRIMARY KEY (dp_id)
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
    p_discrip VARCHAR(255),
    p_stock INT DEFAULT 0,
    p_cat_id INT NOT NULL,
    PRIMARY KEY (p_id),
    FOREIGN KEY (p_cat_id) REFERENCES Category(cat_id)
);

CREATE TABLE Cart_Product_List(
	cp_id INT NOT NULL UNIQUE,
    cp_quantity INT,
    cp_cart_id INT NOT NULL UNIQUE,
    cp_price INT ,
    cp_pid INT NOT NULL UNIQUE, 
    PRIMARY KEY(cp_id),
    FOREIGN KEY(cp_pid) REFERENCES Product (p_id),
    FOREIGN KEY(cp_cart_id) REFERENCES CART(cart_id)
);

CREATE TABLE _Order (
	order_id INT NOT NULL UNIQUE,
    order_delivery_status VARCHAR(255) NOT NULL ,
    order_dp_id INT NOT NULL ,
    order_date  DATE NOT NULL ,
    order_ship_date DATE  NOT NULL ,
    order_cus_id INT NOT NULL UNIQUE,
    PRIMARY KEY(order_id),
    FOREIGN KEY(order_dp_id) REFERENCES Delivery_Partner(dp_id)
);
CREATE TABLE Customer(
	cus_id INT NOT NULL UNIQUE,
    cus_username VARCHAR(255) NOT NULL UNIQUE,
    cus_password VARCHAR(255) NOT NULL,
    
	cus_firstname VARCHAR(255) NOT NULL,
	cus_lastname VARCHAR(255) NOT NULL,
    cus_mobile INT NOT NULL,
    cus_email VARCHAR(255) NOT NULL,
	cus_street VARCHAR(255) NOT NULL,
    cus_city VARCHAR(255) NOT NULL,
    cus_pin_code VARCHAR(6) NOT NULL,
    
    cus_cart_id INT NOT NULL UNIQUE,
    cus_sub_id INT NOT NULL UNIQUE,
    
    PRIMARY KEY (cus_id),
    FOREIGN KEY (cus_cart_id) REFERENCES Cart(cart_id),
    FOREIGN KEY (cus_sub_id) REFERENCES Subscription(sub_id) 
);

-- debug
drop table Admin, _Order,Payment, Places, Manages, Pays, Is_Added_To, Cart_Product_List, Cart, Category, Seller, Delivery_Partner, Product, Subscription, Customer;
show tables;

