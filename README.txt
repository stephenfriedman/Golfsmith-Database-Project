STEPHEN A. FRIEDMAN
CSE 241 PROJECT
04/26/2015

WHAT'S WHERE
This README is in the top level directory along with Manifest1.txt, ojdbc6.jar, saf217project.jar,
Interface1.class, and 2 directories. One directory is called saf217 and the other called dataGen. In saf217  is Interface1.java.
In dataGen are all the java files I used to generate data.



USAGE INSTRUCTIONS

Begin by locating my password from CourseSite, youll need it imediately when you run Inteface1. Interface1 is the name of the class that contains all of my code
for this project. It contains the main method that runs that allows you to test all of the interfaces I made. When you run the program, you'll be prompted
to enter in the my password from CourseSite then you will be able to choose which interface you wouuld like to use. My enterprise is GolfSmith. For the purposes
of this project we have to make the following assumptions: they only have store in the USA, items are bought and sold in American currency, all vendors are from the
USA as well, customers are not allowed to purchase more than $10,000 of goods per order, each customer has a unique email address but customers can share a mutual
phone number, and that when a purchase order is created to restock a good(s) in a store, they get re-stocked immediately (no shipping wait time). There are 2
interfaces that ae rather self explanatroy and those are NEW CUSTOMER INFO and UPDATE CUSTOMER INFO which allows a clerk or customer  to enter in new or update a
customer's info so that they are registered with GolfSmith. When using UPDATE CUSTOMER INFO, the user needs to know the first name,last name, and phone # of a customer
to be able to update that customer's information. One easy customer to test this on is customer #1 Stevie Littel Gregory@idell.us 7667708791. Another interface
called CHECK INVENTORY allows an employee to view inventory levels of items at any of the 53 stores. Another interface called RESTOCK GOODS allows a purchasing
agent employee to view the inventory levels at a store and then purchase new inventory from one of the vendors to re-stock the goods. Again, this re-stocking
happens immediately. Let it be noted that store #53 represents the online store. This online store is represented in the database just like any other store is, 
the only difference is that its address attributes are all called 'online'. Using the PURCHASE interface simulated either a customer shopping online if they choose 
so or a cashier checking out a customer at the register. Becuase I do not have the capability to integrate barcode scanners, unfortunately when using this interface
each item's UPC must be entered manually as well as the quantity of each item. The ANALYTICS interface allows an employee to view information about the company 
from a variety of statistics that they can generate. You will also notice that I never keep track of credit card numbers. In my opinion there is no reason for 
a company to be storing credit card information. A company should note whether or not a purchase was made of cash or credit, but storing credit card info only
leads to the risk of the secure information being stolen or ending up in the wrong hands. Also take note that I used 11 digit UPC codes rather than 12 digit codes.
I also never implemented any code that prevent concurrency issues, I was unsure how to implement it. Another thing to note is that case sensitivity should not be an
issue. I made 29 different products in my product table which is enough to generate "interesting" data.

DATA SOURCES

I generated most of the data on my own by writing Java programs. I did however use an online resource to help me generate some of the customer information.
I used http://www.generatedata.com/ and http://www.random.org/ 
All of the data pertaining to the product information was either made up by myself or pulled from http://www.golfsmith.com/
You can see that I have included a bunch of the programs I wrote that generated this data for me.

EXTRAS

I wrote 2 triggers that updated the inventory after an insert into purchase_orders or sales_order.
These triggers cannot be seen here in this zip file, they are in Oracle but you can see them work by making purchases/sales and then viewing the updated inventory.
I also wrote a handful of programs that generated data for me that gets put into the database.

COMMENTS

I had a great time doing this project. It was a fun challenge and is the most code I have ever written for a project. Feels good to have made this. I'm proud.