ALTER TABLE site_cities ADD CONSTRAINT site_cities_users
    FOREIGN KEY (user_id)
    REFERENCES site_users (id);
