CREATE TABLE author
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE book
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR,
    author_id SERIAL REFERENCES author (id)
);

SELECT book.name
FROM book
JOIN author ON book.author_id=author.id
WHERE author.name='Толстой'