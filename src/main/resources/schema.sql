drop table if exists votes;
drop table if exists quotes;
drop table if exists users;
drop index if exists ix_quote_id_username;

create table if not exists users
(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null
);

create table if not exists quotes
(
    id IDENTITY not null primary key,
    quote varchar(500),
    timestamp timestamp,
    username VARCHAR(50),
    votes_sum INTEGER,
    vote_id INTEGER
);

create table if not exists votes
(
    id IDENTITY not null primary key,
    quote_id INTEGER,
    username VARCHAR(50),
    vote VARCHAR(8),
    timestamp TIMESTAMP,
    current_votes_sum INTEGER,
    constraint fk_votes_quotes foreign key(quote_id) references quotes(id)
);

create unique index if not exists ix_quote_id_username on votes (quote_id,username);