DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS quotes;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP INDEX IF EXISTS ix_quote_id_username;
DROP INDEX IF EXISTS ix_auth_username;

CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
    password VARCHAR_IGNORECASE(500) NOT NULL,
    enabled  BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username VARCHAR_IGNORECASE(50) NOT NULL,
    authority VARCHAR_IGNORECASE(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username ON authorities (username,authority);

CREATE TABLE IF NOT EXISTS quotes
(
    id IDENTITY NOT NULL PRIMARY KEY,
    quote VARCHAR(500),
    timestamp TIMESTAMP,
    username VARCHAR(50) NOT NULL,
    votes_sum INTEGER
);

CREATE TABLE IF NOT EXISTS votes
(
    id IDENTITY NOT NULL PRIMARY KEY,
    quote_id INTEGER NOT NULL,
    username VARCHAR(50) NOT NULL,
    vote VARCHAR(8),
    timestamp TIMESTAMP,
    current_votes_sum INTEGER
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_quote_id_username ON votes (quote_id,username);