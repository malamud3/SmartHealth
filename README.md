x# Project Requirements Document

## 1. Introduction
“SmartHealth” is a super app that connects professional dietitians with potential clients. It offers two mini-applications to cater to different user needs: "DietitiansHelper" and "Healthy."

### 1.1. Purpose of System
The purpose of “SmartHealth” is to help people improve their overall health and support dietitians in delivering professional services. The system provides access to nutritional information about various ingredients, allowing people to make informed choices. It also enables dietitians to create customized recipes based on individual needs and preferences.

### 1.2. Scope of System
The "SmartHealth" system primarily functions as a back-end application that provides infrastructure for front-end applications such as "Healthy" and "DietitiansHelper." The system uses Spoonacular API to provide nutritional information and functionality to support dietitians in their professional services. It also utilizes MongoDB as a database technology and provides efficient storage using pagination.

## 2. Actors and Goals
**Primary actors:**
- Dietitians: Can build recipes.
- Customers: Can subscribe to dietitians to view their creations.

**Support actors:**
- Time: Required for proper documentation and calorie tracking.
- Spoonacular API: Provides data for each ingredient.
- MongoDB: Database technology for storing data.

## 3. Functional Requirements
### User Functions:
- User (Dietitian/Customer) can create an account with username, email, password, and avatar.
- User (Dietitian/Customer) can perform authentication.
- User (Dietitian/Customer) can edit their data, including avatar and username.

### Dietitian Functions:
- Dietitian can create, delete, modify, and get recipes.
- Dietitian can search recipes by recipe name, ingredient, or specific diet.

### Customer Functions:
- Customer can follow a dietitian.
- Customer can unfollow a dietitian.
- Customer can set their health diets (e.g., Gluten Free, Vegan, Ketogenic, Vegetarian).
- Customer can track their daily food intake using a food diary and calorie counter.
- Customer can provide feedback and ratings through a rating system and testimonials.

### Admin Functions:
- Admin has access to regular administrative tasks.

## 3.1. Use Case Diagram
### Use Case: Login
...

## Miniapps:
### Use Case: Create recipe
...

### Use Case: Find recipe
...

### Use Case: Modify recipe
...

### Use Case: Delete recipe
...

### Use Case: Get recipe
...

### Use Case: Get random recipe
...

### Use Case: Follow dietitian
...

## Tech Doc
The following is a list of tools and frameworks that were utilized during the project:

### Frameworks and Environments:
- Java Spring Framework
- Spring Tools
- IntelliJ IDEA

### Database:
- MongoDB
- Mongo Express

### Project Management and Teamwork:
- Trello
- Google Meet
- Teams
- Slack
- WhatsApp

### Design:
- Figma
- Canva

### Containerization:
- Docker

### Version Control:
- Bitbucket

### Diagramming:
- Lucidchart
- sequencediagram.com

### Tests:
- Postman

### Client Side:
- React Native
