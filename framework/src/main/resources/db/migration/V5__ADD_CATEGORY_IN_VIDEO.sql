ALTER TABLE videos
ADD COLUMN category_id BIGINT NOT NULL
FOREIGN KEY (category_id) REFERENCES categories (id);