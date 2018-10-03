# COMP 304 - Mobile Application Development Fall2018
## Lab Assignment #2 
The purpose of this lab assignment is to:
- Use Android UI controls to develop an interactive mobile application

## Exercise 1
Your client needs an Android application to allow customers to order food using their smart phone. Develop an Android app as described below:

The main screen will display the company logo and a button “__Enter__”. 

The second screen allows the customer to choose the __food types__:
- Vegetables and legumes/beans
- Fruit
- Grain (cereal) foods
- Lean meats and poultry, fish, eggs, tofu, nuts and seeds and legumes/beans
- Milk, yogurt cheese and/or alternatives

Use __an options Menu control__ to implement this selection. Display the proper screen with corresponding __food items__ whenever the user selects a food type from the menu. For example, if the user selects Vegetables and legumes/beans food type, the next screen will display different veggies and legumes/beans. Use __check boxes__ to show the food items.

The user may use the __menu control__ to choose another food type, and so on.

The check-out screen displays the selected food items and corresponding prices. It also prompts the user with __payment option__ (cash, credit card, debit card). Use a group of __radio buttons__ to select the payment method.

The next screen asks the user to enter credit/debit card information if the user chooses either of these payment methods.

Use _EditText_ controls and other UI elements to allow the user to enter __customer’ information:__ full name, credit/debit card number. The rest of the fields will be __different for each student__. For example, you may create fields for _favorite food type, favorite food item, favorite app_, etc. Create 2-3 fields named as mentioned above. Provide __validation__ for these entries using the proper attributes/methods/constructor for each UI control.

Use _TextView_ objects to create the label components for your UI. Allow the user to use the "__Back__" key to go back to the previous screen.

Display the order information in the last screen when the user finalizes the order.

Use styles and themes to create a nice look and feel of your app. Use drawable objects to display the logo for the company, food types, food items, etc. 
