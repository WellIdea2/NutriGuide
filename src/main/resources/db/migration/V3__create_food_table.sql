CREATE TABLE foods
(
    id              BINARY(16) NOT NULL PRIMARY KEY,
    food_type       VARCHAR(255) NOT NULL,
    name            VARCHAR(500) NOT NULL,
    user_id         BINARY(16) NOT NULL,
    calorie_amount  DOUBLE NOT NULL,
    calorie_unit    VARCHAR(255) NOT NULL,
    food_info       VARCHAR(255),
    food_large_info TEXT,
    food_picture    VARCHAR(5000),
    meal_id         BINARY(16),
    CONSTRAINT fk_food_meal FOREIGN KEY (meal_id)
        REFERENCES meals (id) ON DELETE CASCADE
);