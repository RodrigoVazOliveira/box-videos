CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL NOT NULL,
    name VARCHAR(200) NOT NULL,
    nick VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    access_role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT USERS_PK PRIMARY KEY (id)
);

INSERT INTO users
    (name,
    nick,
    email,
    password,
    access_role)
VALUES
    ('Rodrigo Vaz', 'rodrigo_vaz', 'rodrigovaz@gmail.com', 'T0qGvbthoc3wmzs6Cd9ajQ==', 'ADMIN'),
    ('Vitor Da Silva', 'vitor_silva', 'vitor_silva@gmail.com', 'T0qGvbthoc3wmzs6Cd9ajQ==', 'USER');