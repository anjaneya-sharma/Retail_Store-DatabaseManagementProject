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