DROP TABLE IF EXISTS LIBRARY_DATA;

CREATE TABLE LIBRARY_DATA (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL
);