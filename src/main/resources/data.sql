DROP TABLE IF EXISTS user_dashboard_relation;
DROP TABLE IF EXISTS user_element_relation;
DROP TABLE IF EXISTS element;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS dashboard;
DROP TABLE IF EXISTS comment;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstname VARCHAR(250) NOT NULL,
  lastname VARCHAR(250) NOT NULL,
  company VARCHAR(250) DEFAULT NULL,
  voting_factor INT NOT NULL DEFAULT 1,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(100) NOT NULL,
  admin boolean NOT NULL DEFAULT 0,
  note VARCHAR(250) DEFAULT NULL,
  UNIQUE KEY unique_email (email)
);

CREATE TABLE dashboard (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE user_dashboard_relation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT  NOT NULL,
  dashboard_id INT  NOT NULL,
  FOREIGN KEY(user_id) REFERENCES user(id),
  FOREIGN KEY(dashboard_id) REFERENCES dashboard(id)
);

CREATE TABLE element (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(250) NOT NULL,
  category VARCHAR(100) NULL,
  created_by_user_id INT  NOT NULL,
  dashboard_id INT  NOT NULL,
  online boolean NOT NULL DEFAULT 0,
  FOREIGN KEY(created_by_user_id) REFERENCES user(id),
  FOREIGN KEY(dashboard_id) REFERENCES dashboard(id)
);

CREATE TABLE user_element_relation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT  NOT NULL,
  element_id INT  NOT NULL,
  FOREIGN KEY(user_id) REFERENCES user(id),
  FOREIGN KEY(element_id) REFERENCES element(id)
);

CREATE TABLE comment (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  text VARCHAR(1000) NOT NULL,
  user_id INT  NOT NULL,
  element_id INT  NOT NULL,
  created_date DATETIME NOT NULL,
  FOREIGN KEY(user_id) REFERENCES user(id),
  FOREIGN KEY(element_id) REFERENCES element(id)
);

INSERT INTO user (id, firstname, lastname, company, voting_factor, email, password, admin) VALUES
  (0, 'Dalibor', 'Malic', 'Billionaire Industrialist', 5, 'muhahaha@gmx.net', '$2a$10$gC3nMxctkrbbaAm.ngk5ROQa3aA2uaqgxSH8jtmCYh8IJavUYW3w2', 1),
  (1, 'Peter', 'Kraushaar', 'Kraushaar', 30, 'peter@kraushaar.de', '$2a$10$gC3nMxctkrbbaAm.ngk5ROQa3aA2uaqgxSH8jtmCYh8IJavUYW3w2', 0),
  (2, 'Max', 'Mustermann', 'Hobby Ferienhausvermietung', 1, 'max@mustermann.de', '$2a$10$gC3nMxctkrbbaAm.ngk5ROQa3aA2uaqgxSH8jtmCYh8IJavUYW3w2', 0);

INSERT INTO dashboard (id, name) VALUES
  (0, 'vOffice Feature Voting');

INSERT INTO user_dashboard_relation (user_id, dashboard_id) VALUES
  (0, 0),
  (1, 0),
  (2, 0);

INSERT INTO element (id, title, description, category, created_by_user_id, dashboard_id, online) VALUES
  (0, 'Ersatzvermietung', 'Blablabla', 'vOffice', 0, 0, 1),
  (1, 'Osterhasen Such Event', 'Eieieieie', 'Event', 1, 0, 0);


INSERT INTO user_element_relation (user_id, element_id) VALUES
  (0, 0),
  (1, 1);

INSERT INTO comment (user_id, element_id, text, created_date) VALUES
  (0, 0, 'Das ist ja ein echt tolles Feature', '2020-01-26 20:01:00'),
  (1, 0, 'Ja darauf warten wir wirklich auch schon seit Ewigkeiten!', '2020-01-26 20:06:00');