DROP DATABASE IF EXISTS uno;
CREATE DATABASE uno;
USE uno;

CREATE TABLE Users (
    ID INT NOT NULL AUTO_INCREMENT,
    Username VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Wins INT,
    Losses INT,
    PRIMARY KEY (ID)
);

INSERT INTO Users (Username, Password, Wins, Losses)
VALUES 
("Jared", "pwd", 1, 10),
("Patrick", "pwd", 11, 13),
("Lydia", "pwd", 15, 3);
