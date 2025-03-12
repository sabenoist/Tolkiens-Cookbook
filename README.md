# Tolkiens-Cookbook
A Java Spring Boot project.

## Requirements to be installed beforehand
- Java 17
- Docker
- Docker Compose

## How to run
### To run the unit and integration tests
1. open a terminal and go to the backend folder. 
2. run the command: 
```PowerShell
.\mvnw test
```

### To run the application
1. open a terminal and go to the backend folder.
2. run the command: 
```PowerShell
.\mvnw clean spring-boot:run
```

This will automatically create a docker container that hosts a postgres database on port 5432. The cookbook API will be running on localhost:8080.

### To import pre-made recipes and ingredients into the database
1. open a terminal and go to the database folder.

#### on Windows Powershell
2. run the command: 
```PowerShell
Get-Content cookbook_dump.sql | docker exec -i cookbook-db psql -U postgres -d cookbook
```

#### on Bash
2. run the command: 
```Shell
docker exec -i cookbook-db psql -U postgres -d cookbook < cookbook_dump.sql
```

## API Endpoints
You can make requests to the endpoints using either Postman or cURL or a similar software. GET requests can also be made inside a normal browser.

### GET ingredient
Returns a list of existing ingredients in the database.

```
localhost:8080/api/ingredients
```

You can also search for ingredients by id.

```
localhost:8080/api/ingredients/<id>
```

### POST ingredient
Creates a new ingredient in the database. 

```
localhost:8080/api/ingredients
```
```JSON
{
  "name": "banana",
  "category": "FRUIT"
}
```

### PUT ingredient
Modify an ingredient in the database.

```
localhost:8080/api/ingredients/<id>
```
```JSON
{
  "name": "strawberry",
  "category": "FRUIT"
}
```

### DELETE ingredient
Deletes an ingredient from the database and also removes it from all recipes it was linked in.

```
localhost:8080/api/ingredients/<id>
```

### GET recipe
```
http://localhost:8080/api/recipes
```

You can also search for recipes by id.
```
localhost:8080/api/recipes/<id>
```

Or use filters such as includeIngredients and excludeIngredients (seperate include and exlude multiple ingredients by comma), keyword (to search the instructions for a keyword), or isVegetarian (for (non-) vegetarian recipes only).

?includeIngredients=<strings split by a comma>
``` 
http://localhost:8080/api/recipes/filter?includeIngredients=salt
```

?excludeIngredients=<strings split by a comma>
``` 
http://localhost:8080/api/recipes/filter?excludeIngredients=salt
```

?keyword=<text>
```
http://localhost:8080/api/recipes/filter?keyword=oven
```

?isVegetarian=<boolean>
```
http://localhost:8080/api/recipes/filter?servings=1
```

?servings=<integer>
```
http://localhost:8080/api/recipes/filter?isVegetarian=true
```

Combining filters
```
http://localhost:8080/api/recipes/filter?includeIngredients=sugar&excludeIngredients=milk&keyword=oven&isVegetarian=false
```

### POST recipe
Creates a new recipe with existing ingredients in the database.
```
http://localhost:8080/api/recipes
```

```JSON
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
```
http://localhost:8080/api/recipes/<id>
```

```JSON
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
```
localhost:8080/api/ingredients/<id>
```