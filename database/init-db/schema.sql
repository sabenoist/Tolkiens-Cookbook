CREATE TABLE recipes (
    recipe_id SERIAL PRIMARY KEY,
    recipe_name TEXT NOT NULL UNIQUE
);

CREATE TABLE ingredients (
    ingredient_id SERIAL PRIMARY KEY,
    ingredient_name TEXT NOT NULL UNIQUE
);

CREATE TABLE recipe_ingredients (
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity TEXT NOT NULL, -- e.g., "2 cups", "1 tsp"
    PRIMARY KEY (recipe_id, ingredient_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id) ON DELETE CASCADE
);

\i /docker-entrypoint-initdb.d/cookbook_dump.sql