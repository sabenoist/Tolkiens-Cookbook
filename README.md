# Tolkiens-Cookbook
A Java Spring Boot project.

## Requirements to be installed beforehand
Java 17
Docker
Docker Compose

## How to run
### To run the unit and integration tests
1. open a terminal and go to the backend folder. 
2. run the command: .\mvnw test

### To run the application
1. open a terminal and go to the backend folder.
2. run the command: .\mvnw clean spring-boot:run

This will automatically create a docker container that hosts a postgres database on port 5432. The cookbook API will be running on localhost:8080.

### To import pre-made recipes and ingredients into the database
1. open a terminal and go to the database folder.

#### on Windows Powershell
2. run the command: Get-Content cookbook_dump.sql | docker exec -i cookbook-db psql -U postgres -d cookbook

#### on Bash
2. run the command: docker exec -i cookbook-db psql -U postgres -d cookbook < cookbook_dump.sql

## API Endpoints
You can make requests to the endpoints using either Postman or cURL or a similar software. GET requests can also be made inside a normal browser.

### GET ingredient
Returns a list of existing ingredients in the database.

```HTTP GET api/ingredients
localhost:8080/api/ingredients
```

You can also search for ingredients by id.

```HTTP GET api/ingredients{:id}
localhost:8080/api/ingredients/<id>
```

### POST ingredient
Creates a new ingredient in the database. 

```HTTP POST api/ingredient
{
  "name": "banana",
  "category": "FRUIT"
}
```

### PUT ingredient
Modify an ingredient in the database.

```HTTP PUT api/ingredient/{:id}
localhost:8080/api/ingredients/<id>

JSON request body:
{
  "name": "strawberry",
  "category": "FRUIT"
}
```

### DELETE ingredient
deletes an ingredient from the database and also removes it from all recipes it was linked in.

```HTTP DELETE api/ingredient/{:id}
localhost:8080/api/ingredients/<id>
```

### GET recipe
```HTTP GET api/recipes
http://localhost:8080/api/recipes
```

you can also search for recipes by id.
```HTTP GET api/recipes{:id}
localhost:8080/api/recipes/<id>
```

or use filters such as includeIngredients and excludeIngredients (seperate include and exlude multiple ingredients by comma), keyword (to search the instructions for a keyword), or isVegetarian (for (non-) vegetarian recipes only).
```HTTP GET includeIngredients
http://localhost:8080/api/recipes/filter?includeIngredients=salt
```
```HTTP GET excludeIngredients
http://localhost:8080/api/recipes/filter?excludeIngredients=salt
```
```HTTP GET keyword
http://localhost:8080/api/recipes/filter?keyword=oven
```
```HTTP GET isVegetarian
http://localhost:8080/api/recipes/filter?isVegetarian=true
```
```HTTP GET combined
http://localhost:8080/api/recipes/filter?includeIngredients=sugar&excludeIngredients=milk&keyword=oven&isVegetarian=false
```

### POST recipe
Creates a new recipe with existing ingredients in the database.
```HTTP POST api/recipes
http://localhost:8080/api/recipes

{
  "name": "Sweet Bread",
  "servings": 4,
  "instructions": "Mix the flour with the sugar and put it in the oven.",
  "ingredients": [
    {
      "ingredient": {
        "id": 7
      },
      "quantity": "a lot"
    },
    {
      "ingredient": {
        "id": 17
      },
      "quantity": "5 tablespoons"
    }
  ]
}
```

### PUT recipe
Updates an existing recipe.
```HTTP PUT api/recipes/{:id}
http://localhost:8080/api/recipes/<id>

{
  "name": "Salty Bread",
  "servings": 4,
  "instructions": "Mix the flour with the salt and put it in the oven.",
  "ingredients": [
    {
      "ingredient": {
        "id": 7
      },
      "quantity": "a lot"
    },
    {
      "ingredient": {
        "id": 9
      },
      "quantity": "3 tablespoons"
    }
  ]
}
```

### DELETE recipe
Deletes an existing recipe.
```HTTP DELETE api/recipes/{:id}
localhost:8080/api/ingredients/<id>
```