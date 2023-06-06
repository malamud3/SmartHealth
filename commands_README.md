# Command: CREATE_RECIPE

The `CREATE_RECIPE` command is used to create a new empty recipe with a unique recipe ID and the provided recipe name. The command returns the newly created recipe object, and it is added to the recipe List in the targetObject. To activate this command, follow these steps:

1. In the `commandAttributes` section, provide the recipe name using the parameter `"recipeName"` and its corresponding value as a string.

Executing this command will create a new empty recipe with a unique recipe ID and the specified recipe name. The command will return the newly created recipe object, and it will be added to the `recipeList` in the `targetObject`.


# Command: ADD_INGREDIENT

The `ADD_INGREDIENT` command is used to add an ingredient with a specified amount to a recipe in the `targetObject`. The command requires the following parameters in the `commandAttributes` section:

- `recipeId`: The ID of the recipe to which the ingredient will be added.
- `ingredientName`: The name of the ingredient to be added.
- `amountInGrams`: The amount of the ingredient in grams.

The command will find the recipe with the provided `recipeId` in the `targetObject`'s recipe list, add the ingredient with the specified amount to it, and return the updated recipe object.

Executing this command will add the specified ingredient with the provided amount to the recipe identified by `recipeId` in the `targetObject`. The command will return the updated recipe object.


# Command: DELETE_ALL_RECIPES

The `DELETE_ALL_RECIPES` command is used to delete all recipes in the `targetObject`. This command does not require any command attributes.

When executed, the command will delete all recipes in the `targetObject`'s recipe list and return an empty list to indicate that the deletion was successful.

Executing this command will delete all recipes in the `targetObject`'s recipe list. The command will return an empty list to indicate that all recipes were successfully deleted.


# Command: DELETE_RECIPE

The `DELETE_RECIPE` command is used to delete a specific recipe from the `targetObject` based on the provided `recipeId`. This command requires the following command attributes:

- `recipeId`: The ID of the recipe to be deleted.

When executed, the command will remove the recipe with the specified `recipeId` from the `targetObject`'s recipe list. If the deletion is successful, the command will return an empty list to indicate that the recipe was successfully deleted.

Executing this command will remove the recipe with the specified `recipeId` from the `targetObject`'s recipe list. The command will return an empty list to indicate that the recipe was successfully deleted.


# Command: FIND_RECIPE

The `FIND_RECIPE` command is used to search for a new recipe based on the provided `recipeName` and `diet` (optional) using an external API. This command requires the following command attributes:

- `recipeName`: The name of the recipe to search for.
- `diet` (optional): The desired diet type for the recipe. Diet options: Gluten Free, Ketogenic, Vegetarian, Lacto-Vegetarian, Ovo-Vegetarian, Vegan, Pescetarian, Paleo, Primal, Low FODMAP, Whole30.

When executed, the command will search for a recipe with the specified name and diet (if provided) using an external API. It will then add the found recipe to the `targetObject`'s recipe list and return the new recipe.


# Command: GET_ALL_RECIPES

The `GET_ALL_RECIPES` command is used to retrieve

 all of the recipes stored in the `targetObject`. This command does not require any command attributes.

When executed, the command will retrieve all the recipes from the `targetObject`'s recipe list and return them as a list.


# Command: MODIFY_RECIPE

The `MODIFY_RECIPE` command is used to modify a specific recipe. This command requires the `recipeId` attribute to identify the recipe to be modified. Additionally, it supports the following optional attributes: `recipeName`, `recipeTitle`, and `recipeImage`. Any combination of these attributes can be provided to modify the corresponding fields of the recipe.

When executed, the command will find the recipe in the `targetObject` using the provided `recipeId` and update the specified attributes with the new values. If an optional attribute is not provided, the corresponding field in the recipe will remain unchanged.

Executing this command will locate the recipe with the specified `recipeId` in the `targetObject`'s recipe list and modify the provided attributes with the new values. The command will return the modified recipe as a response.


# Command: REMOVE_INGREDIENT

The `REMOVE_INGREDIENT` command is used to remove a specific ingredient from a recipe in the `targetObject`. This command requires the `recipeId` attribute to identify the recipe from which the ingredient should be removed. Additionally, it requires the `ingredientId` attribute to identify the ingredient to be removed.

When executed, the command will find the recipe in the `targetObject` using the provided `recipeId` and locate the ingredient with the specified `ingredientId`. It will then remove the ingredient from the recipe.

Executing this command will locate the recipe with the specified `recipeId` in the `targetObject`'s recipe list and remove the ingredient with the specified `ingredientId` from the recipe. The command will return the updated recipe as a response.


# Command: FOLLOW_DIETITIAN

The `FOLLOW_DIETITIAN` command is used to add the user who invoked the command to the followers list of the `targetObject`. This command does not require any additional attributes in the `commandAttributes`.

When executed, the command will add the user who invoked the command to the followers list of the `targetObject`.

Executing this command will add the user who invoked the command to the followers list of the `targetObject`. The command will return an empty list as a response.


# Command: UNFOLLOW_DIETITIAN

The `UNFOLLOW_DIETITIAN` command is used to remove the user who invoked the command from the followers list of the `targetObject`. This command does not require any additional attributes in the `commandAttributes`.

When executed, the command will remove the user who invoked the command from the followers list of the `targetObject`.

Executing this command will remove the user who invoked the command from the followers list of the `targetObject`. The command will return an empty list as a response.