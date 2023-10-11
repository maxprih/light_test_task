CREATE TABLE "User"
(
    id       SERIAL PRIMARY KEY,
    login    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    name     TEXT NOT NULL
);

CREATE TABLE Role
(
    id        SERIAL PRIMARY KEY,
    role_name TEXT NOT NULL,
    user_id   INT  NOT NULL REFERENCES "User" (id)
);

CREATE TABLE Balance
(
    id      SERIAL PRIMARY KEY,
    amount  INT NOT NULL default 0,
    user_id INT NOT NULL REFERENCES "User" (id)
        check ( amount >= 0 )
);

CREATE TABLE Transaction
(
    id      SERIAL PRIMARY KEY,
    amount  INT       NOT NULL,
    date    TIMESTAMP not null default now(),
    user_id INT       NOT NULL REFERENCES "User" (id)
);

CREATE TABLE Country
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE Pokemon
(
    id         SERIAL PRIMARY KEY,
    name       TEXT NOT NULL UNIQUE,
    health     INT  NOT NULL,
    damage     INT  NOT NULL,
    defense    INT  NOT NULL,
    country_id INT  NOT NULL REFERENCES Country (id)
);

CREATE TABLE League
(
    id         SERIAL PRIMARY KEY,
    name       TEXT NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,
    check ( end_date >= start_date )
);

CREATE TABLE Match(
    id SERIAL PRIMARY KEY,
    first_player_id INT NOT NULL REFERENCES Pokemon(id),
    second_player_id INT NOT NULL REFERENCES Pokemon(id),
    date TIMESTAMP NOT NULL,
    league_id INT NOT NULL REFERENCES League(id),
    country_id INT NOT NULL REFERENCES Country(id)
);

CREATE TYPE match_event_status AS ENUM ('WIN', 'LOSE', 'TBD');

CREATE TABLE Match_Event(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    status match_event_status NOT NULL default 'TBD',
    coefficient DOUBLE PRECISION NOT NULL,
    match_id INT NOT NULL REFERENCES Match(id),
    check ( coefficient > 1 )
);

CREATE TABLE Match_Event_Bet(
    match_event_id INT NOT NULL REFERENCES Match_Event(id),
    bet_id INT NOT NULL REFERENCES Bet(id),
    PRIMARY KEY (match_event_id, bet_id)
);

CREATE TYPE bet_status AS ENUM ('WIN', 'LOSE', 'TBD');

CREATE TABLE Bet(
    id SERIAL PRIMARY KEY,
    amount INT NOT NULL,
    status bet_status NOT NULL default 'TBD',
    date TIMESTAMP NOT NULL,
    user_id INT NOT NULL REFERENCES "User"(id)
    check ( amount > 0 )
);