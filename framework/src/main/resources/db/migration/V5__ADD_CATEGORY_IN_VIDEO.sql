ALTER TABLE videos
ADD COLUMN category_id BIGINT NOT NULL DEFAULT 1;

ALTER TABLE videos
ADD FOREIGN KEY (category_id) REFERENCES categories (id);