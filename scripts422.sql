CREATE TABLE cars
(
    Id    SERIAL PRIMARY KEY,
    Brand VARCHAR,
    Model VARCHAR,
    Price BIGINT
);

CREATE TABLE drivers
(
    Id              SERIAL PRIMARY KEY,
    Name            VARCHAR,
    Age             INTEGER,
    Driving_license BOOLEAN,
    Car_id          SERIAL REFERENCES cars (Id)
);