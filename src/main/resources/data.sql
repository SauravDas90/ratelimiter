DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS usersapikey;
DROP TABLE IF EXISTS organizations;
DROP TABLE IF EXISTS developers;



CREATE TABLE users (
  user_name VARCHAR(50) NOT NULL PRIMARY KEY,
  last_name VARCHAR(50) NOT NULL
);

INSERT INTO users (user_name, last_name) VALUES
  ('Saurav', 'Das'),
  ('Vettel', 'Seb'), 
  ('Santul','Sharma') ;


CREATE TABLE usersapikey (
  user_api_key VARCHAR(100) PRIMARY KEY,
  user_name VARCHAR(50) NOT NULL,
  api_name VARCHAR(50) NOT NULL,
  rate_limit INT NOT NULL
);
 
INSERT INTO usersapikey (user_api_key,user_name, api_name, rate_limit) VALUES
  ('Saurav_developers','Saurav', 'developers', 2),
  ('Vettel_developers','Vettel', 'developers', 100),
  ('Saurav_organizations','Saurav', 'organizations', 5),
  ('Santul_organizations','Santul','organizations', 4)  ;
  
 
CREATE TABLE organizations (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  org_name VARCHAR(250) NOT NULL,
  org_address VARCHAR(250) NOT NULL,
  no_of_employees INT 
);
 
INSERT INTO organizations (org_name, org_address, no_of_employees) VALUES
  ('Samsung', 'Noida', 20000),
  ('Microsft', 'Noida', 100),
  ('Maruti', 'Gurgaon', 5000),
  ('BlueOptima', 'London', 250);
  
 CREATE TABLE developers (	
  id INT AUTO_INCREMENT  PRIMARY KEY,
  dev_name VARCHAR(250) NOT NULL,
  experience INT 
);
 
INSERT INTO developers (dev_name, experience) VALUES
  ('Linus', 30),
  ('Gates', 40),
  ('Zuckerberg',12);
  
  

  
  
 