/*
 * Stephen A. Friedman
 * 04/26/2015
 * CSE 241 PROJECT
 * 
 * What a joy it has been to make this program. I'm serious though. I had a lot of fun making this monster
 * and I take a lot of pride in it. This was a great learning experience and certainly the longest program
 * I have ever written.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


public class Interface1 {
	static Connection con = null;
	public static void main (String[] args)
	{
		try {
			Class.forName ("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean loggedIn=false;
		while(!loggedIn)
		{
			String password=null;
			@SuppressWarnings("resource")
			Scanner getPassword=new Scanner(System.in);
			getPassword.reset();
			System.out.println("Input STEPHEN FRIEDMAN's password from coursesite to login in to his Oracle databases.");
			password=getPassword.next();
			try {
				loggedIn=true;
				 con =DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241","saf217",
					       password);
			} catch (SQLException e) {
				System.out.println("Invalid login info. Try again.");
				loggedIn=false;
			}
		}
		Scanner sc=null;
		boolean keepGoing=true;
		String userInput=null;
			while(keepGoing)
			{
				System.out.println("WELCOME TO GOLFSMITH!");
				System.out.println("Enter which interface you would like to use.");
				System.out.println("Choose from the following list: NEW CUSTOMER INFO, UPDATE CUSTOMER INFO, CHECK INVENTORY, RESTOCK GOODS, PURCHASE, ANALYTICS or QUIT to quit.");
			    Scanner scanner = new Scanner(System.in);
			    userInput = scanner.nextLine().trim().toLowerCase();
				if(userInput.equals("new customer info"))
				{
					newCustomer();
				}
				if(userInput.equals("update customer info"))
				{
					updateCustomerInfo();
				}
				if(userInput.equals("check inventory"))
				{
					checkInventory();
				}
				if(userInput.equalsIgnoreCase("restock goods"))
				{
					purchaseAgent();
				}
				if(userInput.equalsIgnoreCase("purchase"))
				{
					storePurchase();
				}
				if(userInput.equalsIgnoreCase("analytics"))
				{
					analytics();
				}
				if(userInput.equalsIgnoreCase("quit"))
				{
					System.out.println("----TERMINATING PROGRAM----");
					keepGoing=false;
				}
				
			
			}

		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//This method is used to produce statistics on the information in the database
	public static void analytics()
	{
		Statement s=null;
		ResultSet r =null;
		ResultSetMetaData rsmd=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scan=null;
		String input=null;
		boolean finishMethod=false;
		while(!finishMethod)
		{
			System.out.println("ANALYTICS INTERFACE");	
			boolean validInput=false;
			while(!validInput)
			{
				System.out.println("Choose from the following list: ");
				System.out.println("SALES BY STORE");
				System.out.println("SALES BY STATE");
				System.out.println("SALES BY BRAND");
				System.out.println("PURCHASES BY CUSTOMER");
				System.out.println("PURCHASES FROM VENDORS");
				System.out.println("TOTAL GROSS SALES");
				System.out.println("SALES BY TYPE");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(input.equals("sales by store"))
				{
					double totalSales=0;
					validInput=true;
					String storeNum=null;
					boolean validStoreEntered=false;
					while(!validStoreEntered)
					{
						System.out.println("Enter a store # or type online");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						if(validStore(input))
						{
							validStoreEntered=true;
							storeNum=input;
						}
						else if(input.equals("online"))
						{
							storeNum="53";
							validStoreEntered=true;
						}
						else
							System.out.println("INVALID STORE # ENTERED");
					}
					try {
						r=s.executeQuery("select price from sales_order where store_num='"+storeNum+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						rsmd=r.getMetaData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							String price=r.getString("price");
							totalSales+=Double.parseDouble(price);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total sales for store: "+storeNum+" are: $"+totalSales);
				}
				else if(input.equals("sales by state"))
				{
					double totalSales=0;
					validInput=true;
					String state=null;
					boolean validStateEntered=false;
					while(!validStateEntered)
					{
						System.out.println("Enter a state or type online");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						String formattedState=input.substring(0, 1).toUpperCase()+input.substring(1);
						if(validState(formattedState))
						{
							validStateEntered=true;
							state=formattedState;
						}
						else if(input.equals("online"))
						{
							validStateEntered=true;
							state=input;
						}
						else
							System.out.println("INVALID STATE ENTERED");
					}
					try {
						r=s.executeQuery("select price from sales_order natural join store where state='"+state+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						rsmd=r.getMetaData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							String price=r.getString("price");
							totalSales+=Double.parseDouble(price);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total sales for state: "+state+" are: $"+totalSales);
				}
				else if(input.equals("sales by brand"))
				{
					double totalSales=0;
					validInput=true;
					String brand=null;
					boolean validBrandEntered=false;
					while(!validBrandEntered)
					{
						System.out.println("Enter a brand");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						brand=input.substring(0,1).toUpperCase()+input.substring(1);
						if(validBrand(brand))
						{
							validBrandEntered=true;
						}
						else
							System.out.println("INVALID BRAND ENTERED");
					}
					try {
						r=s.executeQuery("select price from sales_order natural join product where brand_name='"+brand+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						rsmd=r.getMetaData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							String price=r.getString("price");
							totalSales+=Double.parseDouble(price);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total sales for brand: "+brand+" are: $"+totalSales);
				}
				else if(input.equals("purchases by customer"))
				{
					double totalSales=0;
					validInput=true;
					String customerNum=null;
					boolean validCustomerEntered=false;
					while(!validCustomerEntered)
					{
						System.out.println("Enter a customer email");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						customerNum=findCustNum(input,true);
						if(customerNum!=null)
						{
							validCustomerEntered=true;
						}
						else
							System.out.println("INVALID EMAIL ENTERED");
					}
					try {
						r=s.executeQuery("select price from sales_order where customer_num='"+customerNum+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						rsmd=r.getMetaData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							String price=r.getString("price");
							totalSales+=Double.parseDouble(price);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total sales for customer #: "+customerNum+" are: $"+totalSales);				}
				else if(input.equals("purchases from vendors"))
				{
					double totalSales=0;
					validInput=true;
					String vendorName=null;
					String venNum=null;
					boolean validVendorEntered=false;
					while(!validVendorEntered)
					{
						System.out.println("Enter a vendor name");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						vendorName = input.substring(0, 1).toUpperCase()+input.substring(1);
						System.out.println(vendorName);
					    venNum=validVendor(vendorName);
						if(venNum!=null)
						{
							validVendorEntered=true;
						}
						else
							System.out.println("INVALID VENDOR NAME ENTERED");
 					}
					try {
						r=s.executeQuery("select buying_price from purchase_orders where ven_num='"+venNum+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						rsmd=r.getMetaData();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							String price=r.getString("buying_price");
							totalSales+=Double.parseDouble(price);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					System.out.println("Total purchases from vendor: "+vendorName+" are: $"+totalSales);
				}
				else if(input.equals("total gross sales"))
				{
					double totalSales=0;
					validInput=true;
					try {
						r=s.executeQuery("select price from sales_order");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							totalSales+=r.getDouble("price");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total gross sales= $"+totalSales);
				}
				else if(input.equals("sales by type"))
				{
					double totalSales=0;
					validInput=true;
					String productType=null;
					boolean validProductType=false;
					while(!validProductType)
					{
						System.out.println("Enter a product type");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						if(!validType(input))
						{
							System.out.println("INVALID PRODUCT TYPE ENTERED");
						}
						else
						{
							validProductType=true;
							productType=input;
						}
					}
					try {
						r=s.executeQuery("select price from sales_order natural join product where product_type='"+productType+"'");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						while(r.next())
						{
							totalSales+=r.getDouble("price");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Total "+productType+" sales= $"+totalSales);
				}
				else
				{
					System.out.println("INVALID INPUT");
				}
			}
				System.out.println("To restart this interface type RESTART or type anything else to exit the interface.");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(!input.equals("restart"))
				{
					finishMethod=true;
				}
		}

	}


	public static boolean validType(String type)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select product_type from product where product_type='"+type+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//this interface simulates a customer checking out at the register. While it makes the clerk manually type
	//in a UPC for each item purchased, this is only implemented because I do not have the capability to integrate
	//with a bar code scanner which would pick up the product's UPC automatically. A trigger auto updates inventory.
	//Note that this could double as the online shopping interface. In my database, the online store is simple just
	//another store in the store table. 
	public static void storePurchase()
	{
		System.out.println("STORE PURCHASE INTERFACE");
		boolean finishMethod=false;
		boolean shopsOnline=false;
		Statement s=null;
		String input=null;
		Scanner scan=null;
		ResultSet results=null;
		ResultSetMetaData rsmd=null;
		String storeNum=null;
		
			boolean goodStore=false;
			while(!goodStore)
			{
				System.out.println("Please enter the store # you are at or type ONLINE to shop the web store.");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(input.equals("online")||input.equals("53"))
				{
					System.out.println("WELCOME TO GOLFSMITH ONLINE");
					shopsOnline=true;
					storeNum="53";
					onlineStore();
					goodStore=true;
				}
				else if(validStore(input))
				{
					System.out.println("WELCOME TO STORE # "+input);
					storeNum=input;
					inStorePurchase(input);
					goodStore=true;
				}
				else
					System.out.println("INVALID INPUT");
			}
	}
	
	public static void inStorePurchase(String storeNumber)
	{
		boolean finishMethod=false;
		Scanner scan=null;
		String input=null;
		double subtotal=0.0;
		String email=null;
		String phone=null;
		boolean usedEmail=false;
		String [][] items = new String[3][100];
		int itemIndex=0;
		while(!finishMethod)
		{
			String upc=null;
			boolean validUPC=false;
			while(!validUPC)
			{
				System.out.println("Enter the UPC of the item.");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				boolean validQuantity=false;
				if(!alreadyInTable("inventory","upc",input))
				{
					System.out.println("INVALID UPC ENTERED.");
					validQuantity=true;
				}
				else
				{
					upc=input;
					items[0][itemIndex]=input;
				}
			
				while(!validQuantity)
				{
					System.out.println("Enter the quantity.");
					scan=new Scanner(System.in);
					input=scan.nextLine().toLowerCase().trim();
					int qoh= quantityRemaining(storeNumber,upc);
					System.out.println("QOH: "+qoh);
					if(qoh<Integer.parseInt(input))
					{
						System.out.println("QUANTITY ENTERED IS MORE THAN THE AMOUNT REMAINING IN THIS STORE.");
						validUPC=false;
						validQuantity=true;
					}
					else
					{
						validQuantity=true;
						items[1][itemIndex]=input;
						validUPC=true;
					}
				}
			}
			//fetch price of the product
			double itemPrice=fetchPrice(storeNumber,items[0][itemIndex]);
			double dQuantity= Double.parseDouble(items[1][itemIndex]);
			String formattedTotalPrice= Double.toString(itemPrice*dQuantity);
			items[2][itemIndex]=formattedTotalPrice;
			double d = Double.parseDouble(items[2][itemIndex]);
			DecimalFormat df = new DecimalFormat("####.00");
			String temp = df.format(d);
			subtotal += Double.parseDouble(temp);
			itemIndex++;
			System.out.println("SUBTOTAL: "+subtotal);
			System.out.println("If you are done entering in the items type DONE. Or, type anything else to continuing entering UPCs.");
			scan=new Scanner(System.in);
			input=scan.nextLine().toLowerCase().trim();
			if(input.equals("done"))
			{
				finishMethod=true;
			}
		}
		
		System.out.println("CUSTOMER TOTAL IS 0"+subtotal);
		boolean enteredCustInfo=false;
		while(!enteredCustInfo)
		{
			System.out.println("Enter customer phone number");
			scan=new Scanner(System.in);
			input=scan.nextLine().toLowerCase().trim();
			String customerNum=null;
		    customerNum=fetchCustomerNum(input);
			if(customerNum==null)
			{
				System.out.println("Customer # not found in the database. Type NEW to set up a new customer account or anything else ");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(input.equals("new"))
				{
					email=newCustomer();
					usedEmail=true;
				}
			}
			else
			{
				phone=input;
				enteredCustInfo=true;
			}
			
		}
		if(usedEmail)
		{
			inStoreSO(items,getNextPrimaryKey("sales_order","so_num"),findCustNum(email,true),storeNumber,itemIndex);
		}
		else
		{
			inStoreSO(items,getNextPrimaryKey("sales_order","so_num"),findCustNum(phone,false),storeNumber,itemIndex);
		}
		System.out.println("THANK YOU FOR SHOPPING AT GOLFSMITH STORE # "+storeNumber);
		System.out.println("----------------------------------------------------------");
		System.out.println("");

	}
	
	//true is email false is phone
	public static String findCustNum(String var,boolean b)
	{
		Statement s=null;
		ResultSet r = null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b)
		{
			try {
				r=s.executeQuery("select customer_num from customer where email='"+var+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				r=s.executeQuery("select customer_num from customer where phone='"+var+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			if(r.next())
			{
				try 
				{
					return r.getString("customer_num");
				} 
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void inStoreSO(String[][] items,String so, String custNum,String storeNum,int itemIndex)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<itemIndex;i++)
		{
			DecimalFormat df = new DecimalFormat("#####.00");
			String dfString=df.format(Double.parseDouble(items[2][i]));
			double replacement = Double.parseDouble(dfString);
			int quantity=Integer.parseInt(items[1][i].trim());
				try {
					s.executeQuery("insert into sales_order (so_num,upc,customer_num,quantity,price,store_num) values ('"+so+"','"+items[0][i]+"','"+custNum+"',"+quantity+","+replacement+",'"+storeNum+"')");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
	public static String fetchCustomerNum(String phoneNum)
	{
		Statement s=null;
		ResultSet r= null;
		String customerNum=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select customer_num from customer where phone='"+phoneNum+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				try {
					customerNum= r.getString("customer_num");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerNum;
	}
	
	
	public static double fetchPrice(String storeNum, String upc)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select selling_price from inventory where store_num='"+storeNum+"' and upc='"+upc+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				try {
					return r.getDouble("selling_price");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1.0;
	}
	
	public static void onlineStore()
	{
		boolean finishMethod=false;
		String [][]cart= new String[6][100];
		Scanner scan=null;
		String input=null;
		String upc=null;
		int quantity=-1;
		int cartIndex=0;
		String email=null;
		String totalItemPrice=null;
		while(!finishMethod)
		{
			boolean finishShopping=false;
			while(!finishShopping)
			{
				if(cart[0][0]==null)
				{
					System.out.println("YOUR CART IS CURRENTLY EMPTY");
				}
				else
				{
					System.out.println("|UPC|        |QUANTITY|    |ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|");
					System.out.println("--------------------------------------------------------------------------------------");
					for(int i=0;i<cartIndex;i++)
					{
						System.out.println(cart[0][i]+"         "+cart[1][i]+"         "+cart[2][i]+"     "+cart[3][i]+"       "+cart[4][i]+"       $"+cart[5][i]);
					}
					
				}
				System.out.println("Continue shopping? Type YES to continue shopping or type anything else to checkout.");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(input.equals("yes"))
				{
					boolean finishAdding=false;
					while(!finishAdding)
						{
								boolean addedUPC=false;
								while(!addedUPC)
								{
									System.out.println("Please enter the UPC of the item you wish to add to cart or type BROWSE to browse GolfSmith goods.");
									scan=new Scanner(System.in);
									input=scan.nextLine().toLowerCase().trim();
									if(input.equals("browse"))
									{
										printTable("inventory",true,"store_num","53");
									}
									else
									{
										if(validUPC(input))
										{
											System.out.println("VALID UPC ENTERED.");
											upc=input;
											addedUPC=true;
										}
										else
										{
											System.out.println("INVALID UPC ENTERED");
										}
									}
								}
							boolean validQuantity=false;
							while(!validQuantity)
							{
								System.out.println("Enter the quantity of item "+upc+" you want to add to cart.");
								scan=new Scanner(System.in);
								input=scan.nextLine().toLowerCase().trim();
								try{
									quantity= Integer.parseInt(input);
									validQuantity=true;
								}
								catch(NumberFormatException e)
								{
									System.out.println("INVALID QUANTITY ENETERED");
									validQuantity=false;
								}
								if(quantityRemaining("53",upc)<Integer.parseInt(input))
								{
									System.out.println("Not enough quantity in stock. Enter a smaller quantity.");
								}
								
							}
							//now add the item to the cart
							cart[0][cartIndex]=upc;
							cart[1][cartIndex]=Integer.toString(quantity);
							//|ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|
							String [] data=onlineHelper(upc);
							cart[2][cartIndex]=data[0];
							cart[3][cartIndex]=data[1];
							cart[4][cartIndex]=data[2];
							double quanxprice=0;
							quanxprice=quantity*Double.parseDouble(data[3]);
							DecimalFormat df = new DecimalFormat("#####.00");
							String formattedPrice=df.format(quanxprice);
							cart[5][cartIndex]=formattedPrice;
							cartIndex++;
							System.out.println("ITEM(s) SUCCESSFULLY ADDED TO CART");
							System.out.println("If you are done adding items type DONE. Type anything else to continue adding items.");
							scan=new Scanner(System.in);
							input=scan.nextLine().toLowerCase().trim();
							if(input.equals("done"))
							{
								finishAdding=true;
							}
						}
				}
				else
				{
					finishShopping=true;
				}
			}
			System.out.println("BEGINNING CHECKOUT PROCESS");
			boolean loggedIn = false;
			while(!loggedIn)
			{
				System.out.println("If you are a registered GolfSmith customer please enter your email address. If you are not, type NEW CUSTOMER.");
				scan=new Scanner(System.in);
				input=scan.nextLine().toLowerCase().trim();
				if(input.equals("new customer"))
				{
					email=newCustomer();
				}
				else
				{
					if(isDuplicateCustomer(input))
					{
						email=input;
						loggedIn=true;
					}
					else
					{
						System.out.println("We do not recognize the email you used.");
					}
				}
			}
			boolean toShipping=false;
			while(!toShipping)
			{
					System.out.println("Review your order.");
					System.out.println("|UPC|        |QUANTITY|    |ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|");
					System.out.println("--------------------------------------------------------------------------------------");
					for(int i=0;i<cartIndex;i++)
					{
						System.out.println(cart[0][i]+"         "+cart[1][i]+"         "+cart[2][i]+"     "+cart[3][i]+"       "+cart[4][i]+"       $"+cart[5][i]);
					}
			
					double totalCost=0;
					for(int i=0;i<cartIndex;i++)
					{
						if(cart[5][i]!=null)
							totalCost+=Double.parseDouble(cart[5][i]);
					}

					boolean checkOrEdit=false;
					while(!checkOrEdit)
					{
						System.out.println("Type CHECKOUT to checkout or EDIT to edit your cart.");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						if(input.equals("checkout")&&totalCost>9999.99)
						{
							System.out.println("You cannot checkout until your order is less than $10,000.");
						}
						else if(input.equals("checkout"))
						{
							checkOrEdit=true;
							toShipping=true;
						}
						else if(input.equals("edit"))
						{
							
							boolean finishEdits=false;
							while(!finishEdits)
							{
								System.out.println("Enter the UPC of the item you want to edit");
								scan=new Scanner(System.in);
								input=scan.nextLine().toLowerCase().trim();
								for(int i=0;i<cartIndex;i++)
								{
									if(cart[0][i].equals(input))
									{
										boolean validChange=false;
										while(!validChange)
										{
											System.out.println("What do you want to change the quantity to?");
											scan=new Scanner(System.in);
											input=scan.nextLine().toLowerCase().trim();
											if(input.matches("[0-9]+"))
											{
												String updatedQuantity=input.toString();
												cart[1][i]=updatedQuantity;
												Double recalculatedTotalItemPrice=0.0;
												String[] values = onlineHelper(upc);
												String singlePrice=	values[3];
												Double newSinglePrice=Double.parseDouble(singlePrice);
												newSinglePrice*=Integer.parseInt(updatedQuantity);
												DecimalFormat df = new DecimalFormat("####.00");
												String dfString=df.format(newSinglePrice);
												System.out.println("new price is: "+dfString);
												cart[5][i]=dfString;
												validChange=true;
												i=cartIndex;
												System.out.println("|UPC|        |QUANTITY|    |ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|");
												System.out.println("--------------------------------------------------------------------------------------");
												for(int j=0;j<cartIndex;j++)
												{
													System.out.println(cart[0][j]+"         "+cart[1][j]+"         "+cart[2][j]+"     "+cart[3][j]+"       "+cart[4][j]+"       $"+cart[5][j]);
												}
												 totalCost=0;
												for(int j=0;j<cartIndex;j++)
												{
													if(cart[5][j]!=null)
														totalCost+=Double.parseDouble(cart[5][j]);
												}
											}
											else
												System.out.println("INVALID CAHNGE TO QUANTITY");
										}
									}
								}
								boolean contOrEdit=false;
								while(!contOrEdit)
								{
									System.out.println("Review your order.");
									System.out.println("|UPC|        |QUANTITY|    |ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|");
									System.out.println("--------------------------------------------------------------------------------------");
									for(int i=0;i<cartIndex;i++)
									{
										System.out.println(cart[0][i]+"         "+cart[1][i]+"         "+cart[2][i]+"     "+cart[3][i]+"       "+cart[4][i]+"       $"+cart[5][i]);
									}
									
									System.out.println("If you are done making edits type CONTINUE or type EDIT to continue editing the cart.");
									scan=new Scanner(System.in);
									input=scan.nextLine().toLowerCase().trim();
									if(input.equals("continue"))
									{
										finishEdits=true;
										contOrEdit=true;
									}
									else if(input.equals("edit"))
									{
										finishEdits=false;
										contOrEdit=true;
									}
									else
										System.out.println("INVALID INPUT");
								
								}
							}
						}
						else
							System.out.println("INVALID INPUT");
					}
					System.out.println("Review your order.");
					System.out.println("|UPC|        |QUANTITY|    |ITEM NAME|      |BRAND NAME|   |WEIGHT| |ITEM(S) PRICE($)|");
					System.out.println("--------------------------------------------------------------------------------------");
					for(int i=0;i<cartIndex;i++)
					{
						System.out.println(cart[0][i]+"         "+cart[1][i]+"         "+cart[2][i]+"     "+cart[3][i]+"       "+cart[4][i]+"       $"+cart[5][i]);
					}
				    totalCost=0;
					for(int i=0;i<cartIndex;i++)
					{
						if(cart[5][i]!=null)
							totalCost+=Double.parseDouble(cart[5][i]);
					}
					System.out.println("Your order total is: $"+totalCost);
					
					boolean validCredit=false;
					while(!validCredit)
					{
						System.out.println("Please enter a credit card number");
						scan=new Scanner(System.in);
						input=scan.nextLine().toLowerCase().trim();
						if(input.matches("[0-9]+") && input.length()==16)
						{
							validCredit=true;
							toShipping=true;
						}
						else
							System.out.println("INVALID CREDIT CARD NUMBER");
					}
			}
					System.out.println("Enter the address you wish to send your order to. Or, to use the address registered with your account, type ACCOUNT.");
					System.out.println("NOTE: IF YOU CHOOSE TO MANUALLY ENTER AN ADDRESS, WE WILL NOT VERIFY THE ADDRESS. ENSURE YOU ENTER IT PROPERLY.");
					scan=new Scanner(System.in);
					input=scan.nextLine().toLowerCase().trim();
					if(input.equals("account"))
					{
						System.out.println("SENDING ORDER TO THE FOLLOWING ADDRESS:");
						printTable("customer",true,"email",email);
					}
					else
					{
						System.out.println("SENDING ORDER TO THE FOLLOWING ADDRESS:");
						System.out.println(input);
					}
					System.out.println("To cancel the order type CANCEL or type anything else to submit the order.");
					scan=new Scanner(System.in);
					input=scan.nextLine().toLowerCase().trim();
					if(input.equals("cancel"))
					{
						cart = new String[6][100];
					}
					else
					{
						System.out.println("ORDER SUBMITTED. THANK YOU FOR SHOPPING WITH GOLFSMITH!");
						Statement s=null;
						ResultSet custResults = null;
						String customerNumber=null;
						try {
							s=con.createStatement();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							custResults=s.executeQuery("select customer_num from customer where email='"+email+"'");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							if(custResults.next())
							{
								customerNumber=custResults.getString(1);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						createSalesOrder(cart,customerNumber,"53");
						finishMethod=true;
					}
		}
	}
	
	public static int quantityRemaining(String storeNum,String upc)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select qoh from inventory where upc='"+upc+"' and store_num='"+storeNum+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return r.getInt("qoh");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static void createSalesOrder(String[][] items,String customerNumber,String storeNumber)
	{
		String soNum=null;
		String upc=null;
		String customerNum=customerNumber;
		int quantity=-1;
		double price=-1;
		String storeNum=storeNumber;
		
		soNum=getNextPrimaryKey("sales_order","so_num");
		for(int i=0;i<items[0].length;i++)
		{
			upc=items[0][i];
			quantity=Integer.parseInt(items[1][i]);
			price=Double.parseDouble(items[5][i]);
			DecimalFormat df = new DecimalFormat("#####.00");
			String formattedPrice=df.format(price);
			Double converted=Double.parseDouble(formattedPrice);
			String query="insert into sales_order (so_num,upc,customer_num,quantity,price,store_num) values('"+soNum+"','"+upc+"','"+customerNum+"',"+quantity+","+converted+",'"+storeNum+"')";
			Statement s=null;
			try {
				 s = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResultSet r=null;
			try {
				r=s.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static String[] onlineHelper(String upc)
	{
		String [] data = new String[4];
		Statement s=null;
		ResultSet results=null;
		ResultSetMetaData rsmd=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			results=s.executeQuery("select name, brand_name,weight from product where upc='"+upc+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				try {
					data[0]=results.getString("name");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					data[1]=results.getString("brand_name");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					data[2]=results.getString("weight");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			results=s.executeQuery("select selling_price from inventory where upc='"+upc+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				try {
					data[3]=Double.toString(results.getDouble("selling_price"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	//this method allows a purchasing agent to look up inventory then fill out a purchase order.
	//for the sake of simplicity, when the purchase order is completed, the goods ordered are instantly received by the store
	//and updated appropriately in inventory. By default, if the store does not have a UPC listed in their inventory when they 
	//make a purchase order, the price of the product at the store will be listed at 1.5x the buying price per unit.
	public static void purchaseAgent()
	{
		System.out.println("PURCHASING AGENT INTERFACE");
		boolean finishMethod=false;
		Scanner scan=null;
		String input=null;
		String upc=null;
		String poNum=null;
		String storeNum=null;
		String venNum=null;
		double buyingPrice=0;
		int quantity=0;
		while(!finishMethod)
		{
			boolean validStore=false;
			while(!validStore)
			{
				scan=new Scanner(System.in);
			    input=null;
				System.out.println("Please enter the store number you wish to make a purchase order for.");
				input=scan.nextLine().toLowerCase().trim();
				if(validStore(input))
				{
					System.out.println("VALID STORE NUMBER ENTERED");
					storeNum=input;
					validStore=true;
				}
				else
				{
					System.out.println("INVALID STORE NUMBER ENTERED");
				}
			}
			System.out.println("Would you like to view the current inventory levels at this store? If so, type YES or anything else for no.");
			scan=new Scanner(System.in);
		    input=scan.nextLine().toLowerCase().trim();
		    if(input.equals("yes"))
		    {
		    	//print out the inventory of the store
		    	printTable("inventory",true,"store_num",storeNum);
		    }
		    boolean validUPC =false;
		    while(!validUPC)
		    {
			    System.out.println("Enter which 11 digit UPC you would like to make a purchase order for.");
			    scan=new Scanner(System.in);
			    input=scan.nextLine();
			    if(validUPC(input))
			    {
			    	System.out.println("VALID UPC ENTERED");
			    	upc=input;
			    	validUPC=true;
			    }
			    else
				{
			    	System.out.println("INVALID UPC ENTERED");
				}
		    }
		    boolean validVendor=false;
		    while(!validVendor)
		    {
		    	System.out.println("Enter which vendor number you will be purchasing from. OR, to see a list of vendors type VENDORS. Or, to add a new vendor, type NEW VENDOR");
		    	scan=new Scanner(System.in);
			    input=scan.nextLine().toLowerCase().trim();
			    if(input.equals("vendors"))
			    {
			    	printTable("vendor",false,null,null);
			    }
			    else if(input.equals("new vendor"))
			    {
			    	System.out.println("Enter vendor name");
			    	scan=new Scanner(System.in);
				    input=scan.nextLine().toLowerCase().trim();
				    String vendorName=input;
				    boolean validVendorPhone =false;
				    //vendors can have the same name but must have a different phone number
				    while(!validVendorPhone)
				    {
					    System.out.println("Enter vendor phone number");
				    	scan=new Scanner(System.in);
					    input=scan.nextLine().toLowerCase().trim();
					    String vendorPhone=input;
					    if(alreadyInTable("vendor","phone",vendorPhone))
					    {
					    	System.out.println("The phone # you enetered already appears to be used by a vendor.");
					    }
					    else
					    {
					    	validVendorPhone=true;
					    	ResultSet addVendor=null;
					    	Statement s = null;
					    	try {
								s=con.createStatement();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					    	String pk = getNextPrimaryKey("vendor","ven_num");
					    	try {
								addVendor=s.executeQuery("insert into vendor (ven_num,name,phone)values('"+pk+"','"+vendorName+"','"+vendorPhone+"')");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }
				    }
				    System.out.println("VALID VENDOR INFORMATION ENTERED");
			    }
			    else
			    {
			    	//they chose to enter a vendor #
			    	//input is already loaded with vendor #
			    	if(!alreadyInTable("vendor","ven_num",input))
			    	{
			    		System.out.println("We do not recognize the vendor # you entered.");
			    	}
			    	else
			    	{
			    		validVendor=true;
			    		System.out.println("VALID VENDOR # ENTERED");
			    		venNum=input;
			    	}
			    }
		    	
		    }
		    boolean validPrice=false;
		    while(!validPrice)
		    {
		    	System.out.println("Enter the buying price for UPC: "+upc+" in dollars (you can include cents).");
		    	scan=new Scanner(System.in);
			    input=scan.nextLine().toLowerCase().trim();
			    if((input.contains("."))|| input.matches("[0-9]+"))
			    	
			    {
			    	System.out.println("VALID PRICE INPUTTED");
			    	buyingPrice=Double.parseDouble(input);
			    	validPrice=true;
			    }
			    else
			    	System.out.println("INVALID PRICE INPUTTED");
		    }
		    boolean validQuantity=false;
		    while(!validQuantity)
		    {
		    	System.out.println("Enter the quantity to buy between 1 and 10,000");
		    	scan=new Scanner(System.in);
			    input=scan.nextLine().toLowerCase().trim();
			    if(Integer.parseInt(input)<10000 &&Integer.parseInt(input)>0 && input.matches("[0-9]+"))
			    {
			    	System.out.println("VALID QUANTITY INPUTTED");
			    	quantity=Integer.parseInt(input);
			    	validQuantity=true;
			    }
			    else
			    	System.out.println("INVALID QUANTITY INPUTTED");
		    }
		    //add the new purchase order to the DB
		    //the good shout also hit inventory immediately because of the trigger I implemented in purchase_orders table
		    ResultSet r = null;
		    Statement s = null;
		    try {
				s=con.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
		    	poNum=getNextPrimaryKey("purchase_orders","po_num").toLowerCase().trim();
				r=s.executeQuery("insert into purchase_orders (po_num,store_num,ven_num,upc,buying_price,quantity) values('"+poNum+"','"+storeNum+"','"+venNum+"','"+upc+"',"+buyingPrice+","+quantity+")");
				finishMethod=true;
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				finishMethod=false;
			}
		    if(!finishMethod)
		    {
		    	System.out.println("Error entering the purchase order");
		    }
		    else
		    	{
		    		System.out.println("PURCHASE ORDER ENTERED INTO DATABASE AND INVENTORY UPDATED ACCORDINGLY");
		    		System.out.println("PO_NUM: "+poNum+" STORE_NUM: "+storeNum+" VEN_NUM: "+venNum+" UPC: "+upc+" BUYING_PRICE: "+buyingPrice+" QUANTITY: "+quantity);
		    	}
		    
		    System.out.println("To restart this interface type YES or type anything else to end it.");
		    scan=new Scanner(System.in);
		    input=scan.nextLine().toLowerCase().trim();
		    if(input.equals("yes"))
		    {
		    	finishMethod=false;
		    }
		    else
		    	finishMethod=true;
		}
		System.out.println("ENDING PURCHASING AGENT INTERFACE");
	}
	
	//determines if a tuple is already in a table given a certain column and value for the column. 
	//Only works if the value is a string
	public static boolean alreadyInTable(String tableName,String columnName,String value)
	{
		Statement s=null;
		ResultSet results=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSetMetaData rsmd=null;
		try {
			results=s.executeQuery("select "+columnName+" from "+tableName+" where "+columnName+"="+"'"+value+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rsmd=results.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				//there is at least one tuple this table with the given value in the given column
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//gets the next number to be used for primary key given the name of the primary key's column
	public static String getNextPrimaryKey(String tableName,String columnName)
	{
		Statement s=null;
		ResultSet results=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSetMetaData rsmd=null;
		try {
			results=s.executeQuery("select "+columnName+" from "+tableName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rsmd=results.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Integer> allKeys = new ArrayList<Integer>();
		try {
			while(results.next())
			{
				allKeys.add(Integer.parseInt(results.getString(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(allKeys);
		if(allKeys.isEmpty())
			return "1";
		return Integer.toString(allKeys.get(allKeys.size()-1)+1);
	}
	
	
	//prints out column labels and all results from the table
	public static void printTable(String tableName, boolean hasWhere,String whereColumn, String whereValue)
	{
		Statement s=null;
		ResultSet results=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!hasWhere)
		{
			try {
				 results=s.executeQuery("select * from "+tableName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				 results=s.executeQuery("select * from "+tableName+" where "+whereColumn+"='"+whereValue+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ResultSetMetaData rsmd = null;
		try {
			 rsmd=results.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				System.out.print(rsmd.getColumnName(i).toUpperCase()+"   ");
			}
			System.out.println("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			while(results.next())
			{
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					System.out.print(results.getString(i)+"   ");
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static boolean validUPC(String givenUPC)
	{
		Statement s=null;
		ResultSet results=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 results=s.executeQuery("select upc from product where upc='"+givenUPC+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				//a valid store # was given
				return true;
			}
			else
			{
				//invalid store num given
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean validStore(String givenStore)
	{
		Statement s=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet results = null;
		try {
			 results=s.executeQuery("select store_num from store where store_num='"+givenStore+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				//a valid store # was given
				return true;
			}
			else
			{
				//invalid store num given
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	//allows a warehouse manager or purchasing agent to view inventory levels for products at different stores by store, upc, or product_type
	public static void checkInventory()
	{
		boolean continueMethod=true;
		boolean usedStoreNum=false;
		String storeNum=null;
		Statement s=null;
		ResultSet ciResults = null;

		while(continueMethod)
			{
			Scanner ciScan=new Scanner(System.in);
			String ciInput=null;
			String street=null;
			String state=null;
			storeNum=null;
		    usedStoreNum=false;
			System.out.println("CHECKING INVENTORY INTERFACE");
			boolean storeNotLookedUp=true;
			System.out.println("Do you want to search by store? Type YES or anything else for no.");
			boolean usedStore=false;
			ciInput=ciScan.nextLine().trim().toLowerCase();
			 s = null;
			Connection c;
		
			if(ciInput.equals("yes"))
			{
				while(storeNotLookedUp)
						{
							usedStore=true;
							System.out.println("Please enter numerical store code. Or, to find a store using its address, type ADDRESS");
							ciScan.reset();
							ciInput=ciScan.nextLine().trim().toLowerCase();
							boolean allDigits=true;
				
							if(ciInput.equals("address"))
							{
								System.out.println("Please enter the EXACT street address (case sensitive) and state of the store");
								ciScan.reset();
								ciInput=ciScan.nextLine().trim();
							    state= ciInput.substring(ciInput.lastIndexOf(" ")+1).trim();
							    street = ciInput.substring(0,ciInput.indexOf(state)).trim();
							    state=state.toUpperCase();
								System.out.println("Entered state: "+state);
								System.out.println("Entered street: "+street);
								state=stateConverter(state);
								if(state==null)
								{
									storeNotLookedUp=true;
									System.out.println("INVALID STATE ENTERED.");
								}
								storeNotLookedUp=false;
							}
							
							//didn't lookup by address
							else
								{	
									storeNum=ciInput;
									usedStoreNum=true;
									storeNotLookedUp=false;
//									for(int i=0;i<storeNum.length();i++)
//									{
//										Character ccc = ciInput.charAt(i);
//										System.out.println(ccc);
//										if(!Character.isDigit(i))
//										{
//											allDigits=false;
//											System.out.println("booty all");
//										}
//									}
//
//									if(allDigits)
//									{
//										System.out.println("all digits is true");
//										usedStoreNum=true;
//										storeNotLookedUp=false;
//									}
								}
						}
						 s=null;
						try {
							s=con.createStatement();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					    ciResults=null;
						ResultSetMetaData rsmdStore=null;
						String fetchStoreQuery=null;
						if(usedStoreNum==true)
						{	
							//lookup by store num
							fetchStoreQuery="select * from store where store_num='"+storeNum+"'";
						}
						else
						{
							//lookup by address
							fetchStoreQuery="select * from store where street='"+street+"' and state='"+state+"'";
						}
						try {
							ciResults=s.executeQuery(fetchStoreQuery);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							 rsmdStore=ciResults.getMetaData();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					try {
						boolean multipleResults=false;
						if(ciResults.next())
						{
							multipleResults=true;
						}
						//print out the store table column name
						for(int i=1;i<=rsmdStore.getColumnCount();i++)
						{
							System.out.print(rsmdStore.getColumnName(i).toUpperCase()+"    ");
						}
						System.out.println("");

						//print out the store info
						for(int i=1;i<=rsmdStore.getColumnCount();i++)
						{
							if(multipleResults)
							{
								System.out.print(ciResults.getString(i)+"    ");
							}
							
							else if(!ciResults.next()&& multipleResults==false)
								{
									System.out.println("Store not found with the entered information");
									break;
								}
						}
						System.out.println("");
						if(multipleResults)
						{
							System.out.println("STORE INFO FOUND");
							storeNotLookedUp=false;
						}
						else
							{
								System.out.println("STORE INFO NOT FOUND");
							}
		
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			//now let user check inventory
			boolean validInput=false;
			String productQuery = null;
			while(!validInput)
				{
				System.out.println("Enter an 11 digit UPC or type ALL to see all products listed or type TYPE to search by product type.");
				Scanner upcOrAllScanner = new Scanner(System.in);
				String upcOrAll = upcOrAllScanner.nextLine().toLowerCase().trim();
				if(upcOrAll.length()==11)
				{
					//used a upc
					validInput=true;
					if(usedStoreNum)
					{
						productQuery="select * from inventory where store_num='"+storeNum+"' and upc='"+upcOrAll+"'";
					}
					else
						productQuery="select * from inventory where upc='"+upcOrAll+"'";

				}
				else if(upcOrAll.equals("all"))
				{
					//wants to see all products
					validInput=true;
					if(usedStoreNum)
					{
						productQuery="select * from inventory where store_num='"+storeNum+"' order by store_num";
					}
					else
						productQuery="select * from inventory order by store_num";

				}
				else if(upcOrAll.equals("type"))
				{
					validInput=true;
					System.out.println("Please enter in the product_type");
					Scanner pt= new Scanner(System.in);
					String productType=pt.nextLine().toLowerCase().trim();
					if(usedStoreNum)
					{
						productQuery="select store_num, upc, qoh, selling_price, product_type from inventory natural join product where store_num='"+storeNum+"' and product_type='"+productType+"'";

					}
					else
						productQuery="select store_num, upc, qoh, selling_price, product_type from inventory natural join product where product_type='"+productType+"'";

				}
				else
				{
					System.out.println("invalid input");
				}
			}
			try {
				s=con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ciResults=s.executeQuery(productQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				for(int i=1;i<=ciResults.getMetaData().getColumnCount();i++)
				{
					System.out.print(ciResults.getMetaData().getColumnName(i).toUpperCase()+"       ");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("");
			try {
				boolean hasResults=false;
				while(ciResults.next())
				{
					hasResults=true;
					for(int i=1;i<=ciResults.getMetaData().getColumnCount();i++)
					{
						if(i%4==0)
						{
							System.out.print("$");
						}
						System.out.print(ciResults.getString(i)+"       ");
					}
					System.out.println("");
				}
				if(!hasResults)
				{
					System.out.println("NO RESULTS FOUND");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Type RESTART to begin this interface from the beginning or type anything else to exit");
			Scanner tempScan=new Scanner(System.in);
			String decision = tempScan.nextLine().toLowerCase().trim();
			if(!decision.equals("restart"))
			{
				continueMethod=false;
			}
		}
		System.out.println("Ending STORE INVENTORY interface.");
	}
	
	
	public static boolean validBrand(String brand)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select brand_name from brand where brand_name='"+brand+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean validState(String state)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select state from store where state='"+state+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static String validVendor(String vendorName)
	{
		Statement s=null;
		ResultSet r=null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r=s.executeQuery("select ven_num from vendor where name='"+vendorName+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return r.getString("ven_num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	//used to convert partial state names to the full name and format the text correctly
	public static String stateConverter(String inputState)
	{		
		String formattedState=null;
		if(inputState.length()==2)
		{
			formattedState=inputState.toUpperCase().trim();
		}
		else
		{
			formattedState=inputState.substring(0,1).toUpperCase().trim()+inputState.substring(1,inputState.length());
		}
		String[] states = new String[100];
		states[0] = "Alabama";
		states[1] = "Alaska";
		states[2] = "Arizona";
		states[3] = "Arkansas";
		states[4] = "California";
		states[5] = "Colorado";
		states[6] = "Connecticut";
		states[7] = "Delaware";
		states[8] = "Florida";
		states[9] = "Georgia";
		states[10] = "Hawaii";
		states[11] = "Idaho";
		states[12] = "Illinois";
		states[13] = "Indiana";
		states[14] = "Iowa";
		states[15] = "Kansas";
		states[16] = "Kentucky";
		states[17] = "Louisiana";
		states[18] = "Maine";
		states[19] = "Maryland";
		states[20] = "Massachusetts";
		states[21] = "Michigan";
		states[22] = "Minnesota";
		states[23] = "Mississippi";
		states[24] = "Missouri";
		states[25] = "Montana";
		states[26] = "Nebraska";
		states[27] = "Nevada";
		states[28] = "New Hampshire";
		states[29] = "New Jersey";
		states[30] = "New Mexico";
		states[31] = "New York";
		states[32] = "North Carolina";
		states[33] = "North Dakota";
		states[34] = "Ohio";
		states[35] = "Oklahoma";
		states[36] = "Oregon";
		states[37] = "Pennsylvania";
		states[38] = "Rhode Island";
		states[39] = "South Carolina";
		states[40] = "South Dakota";
		states[41] = "Tennessee";
		states[42] = "Texas";
		states[43] = "Utah";
		states[44] = "Vermont";
		states[45] = "Virginia";
		states[46] = "Washington";
		states[47] = "West Virginia";
		states[48] = "Wisconsin";
		states[49] = "Wyoming";
		states[50] = "AL";
		states[51] = "AK";
		states[52] = "AZ";
		states[53] = "AR";
		states[54] = "CA";
		states[55] = "CO";
		states[56] = "CT";
		states[57] = "DE";
		states[58] = "FL";
		states[59] = "GA";
		states[60] = "HI";
		states[61] = "ID";
		states[62] = "IL";
		states[63] = "IN";
		states[64] = "IA";
		states[65] = "KS";
		states[66] = "KY";
		states[67] = "LA";
		states[68] = "ME";
		states[69] = "MD";
		states[70] = "MA";
		states[71] = "MI";
		states[72] = "MN";
		states[73] = "MS";
		states[74] = "MO";
		states[75] = "MT";
		states[76] = "NE";
		states[77] = "NV";
		states[78] = "NH";
		states[79] = "NJ";
		states[80] = "NM";
		states[81] = "NY";
		states[82] = "NC";
		states[83] = "ND";
		states[84] = "OH";
		states[85] = "OK";
		states[86] = "OR";
		states[87] = "PA";
		states[88] = "RI";
		states[89] = "SC";
		states[90] = "SD";
		states[91] = "TN";
		states[92] = "TX";
		states[93] = "UT";
		states[94] = "VT";
		states[95] = "VA";
		states[96] = "WA";
		states[97] = "WV";
		states[98] = "WI";
		states[99] = "WY";
		
		for(int i=0;i<states.length;i++)
		{
			if(states[i].equals(formattedState))
			{
				if(i>=50)
				{
					return states[i-50];
				}
				else
					return states[i];
			}
		}
		
		return null;
	}
	
	//this method is is the interface where a user would update their own info on the futuristic checkout keypad
	public static void updateCustomerInfo()
	{
		boolean finishMethod =false;
		while(finishMethod==false)
		{
		Statement s =  null;
		System.out.println("UPDATING CUSTOMER INFORMATION");
		try {
			s = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("LOOKING UP CUSTOMER: Search by entering in your first and last name and phone number.");
		Scanner scan = new Scanner(System.in);
		String input="";
		String fname = null;
		String lname = null;
		String phone = null;
		String email=null;
		boolean validInput=false;
		while(!validInput)
		{
			scan.reset();
			 fname=scan.next().trim();
			 lname=scan.next().trim();
			 phone=scan.next().trim();
			if(fname!=null&&lname!=null&& phone!= null & phone.length()==10)
			{
				validInput=true;
			}
			else
				System.out.println("Invalid user input. Enter again.");
		}
	
		String fetchCustomers = "select * from customer where fname = '"+fname+"'"+" and lname='"+lname+"'"+" and phone='"+phone+"'";
		ResultSet names = null;
		ResultSetMetaData rsmd=null;
		try {
			 names = s.executeQuery(fetchCustomers);
			 rsmd=names.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet duplicateOfNames=names;
		int tuples=0;
		try {
			if(duplicateOfNames.next())
			{
				if(duplicateOfNames.next())
				{
					tuples=2;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int numCols=0;
		if(tuples>1)
		{
			System.out.println("We need some more lookup information.");
			System.out.println("Enter your email address");
			scan.reset();
		    input = scan.next().trim().toLowerCase();
		    email=input;
			try {
				if(input.equals(names.getString("email")))
				{
					System.out.println("RECOGNIZED CUSTOMER INFORMATION");
					 numCols=0;
					try {
					 numCols=rsmd.getColumnCount();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 tuples=0;
						for(int i=1;i<=numCols;i++)
							{
								try {
									System.out.print(names.getString(i)+"    ");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("");
				}
				else
				{
					System.out.println("We do not have this customer registered with our database.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			//print out customer info and begin update process
			System.out.println("RECOGNIZED CUSTOMER INFORMATION");
			try {
				 numCols=0;
				try {
				 numCols=rsmd.getColumnCount();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 tuples=0;
				while(names.next())
				{
					tuples++;
					for(int i=1;i<=numCols;i++)
					{
						try {
							System.out.print(names.getString(i)+"    ");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//begin update process
		boolean updatedAttribute=false;
		while(!updatedAttribute)
		{
			ResultSet rs=null;
			try {
				 rs = s.executeQuery(fetchCustomers);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				rsmd=rs.getMetaData();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Which attribute do you want to edit?");
			for(int i=1;i<=numCols;i++)
			{
				try {
					 rsmd=names.getMetaData();
					System.out.print(rsmd.getColumnName(i).toUpperCase()+"    ");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			input=scan.reset().next();
			String attribute=input;
			System.out.println("What do you want to change this customers "+input+" value to?");
			input=scan.reset().next();
			String newValue=input;
			String updateCustomerQuery;
			if(email==null)
			{
			 updateCustomerQuery="update customer set "+attribute+"='"+newValue+"' where fname='"+fname+"' and lname='"+lname+"' and phone='"+phone+"' and email='"+email+"'";
			}
			else
			{
				updateCustomerQuery="update customer set "+attribute+"='"+newValue+"' where fname='"+fname+"' and lname='"+lname+"' and phone='"+phone+"'";
			}
			try {
				ResultSet updatedCustomer = s.executeQuery(updateCustomerQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("INFORMATION SUCCESSFULLY UPDATED");
			System.out.println("Would you like to edit another attribute? Type YES for yes and anything else for no");
			input=scan.next().trim().toLowerCase();
			if(!input.equals("yes"))
			{
				updatedAttribute=true;
			}
		}
		
		
		
		
		
		
		/*for(int j=1;j<=numCols;j++)
		{
			try {
				System.out.print(rsmd.getColumnName(j)+"   ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		System.out.println("");
		
	   
		System.out.println("");
	
		System.out.println("Type REGISTER to register as a new customer or LOOKUP to search for a customer or type anything else to quit.");
		scan.reset();
		scan=new Scanner(System.in);
		input = scan.nextLine().trim().toLowerCase();
		if(input.equals("register"))
		{
			finishMethod=true;
			newCustomer();
		}
		else if(input.equalsIgnoreCase("lookup"))
		{
			finishMethod=false;
		}
		else
		{
			finishMethod=true;
		}
	}
		System.out.println("Ending Update Customer Info Interface.");
		System.out.println("");
	}
	
	
	public static String newCustomer()
	{
		Statement s =  null;
		System.out.println("NEW CUSTOMER REGISTRATION");
		try {
			s = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		String input=null;
		System.out.println("Input First name:");
		String fname=scan.nextLine().trim();
		while(fname.matches(".*\\d+.*")||fname.length()>15||fname.length()==0)
		{
			if( fname.matches(".*\\d+.*"))
			{
				System.out.println("Firt Name contains a numeric value.");
			}
			if(fname.length()>15)
			{
				System.out.println("First Name longer than 15 characters.");
			}
			if(fname.length()==0)
			{
				System.out.println("Last name was not inputted");
			}
			System.out.println("Re-enter First Name");
			scan.reset();
			fname=scan.nextLine().trim();
		}
		System.out.println("Input Last name:");
		scan.reset();
		String lname=scan.nextLine().trim();
		while(lname.matches(".*\\d+.*")||lname.length()>20||lname.length()==0)
		{
			if( lname.matches(".*\\d+.*"))
			{
				System.out.println("Last Name contains a numeric value.");
			}
			if(lname.length()>20)
			{
				System.out.println("Last Name longer than 20 characters.");
			}
			if(lname.length()==0)
			{
				System.out.println("Last name was not inputted");
			}
			System.out.println("Re-enter Last Name");
			scan.reset();
			lname=scan.nextLine().trim();
		}
		boolean validEmail=false;
		String email=null;
		while(!validEmail)
		{
			System.out.println("Input email address");
			scan.reset();
		    email=scan.nextLine().trim();
			while(email.length()>50||email.length()==0||!email.contains("@"))
			{
				if(email.length()>50)
				{
					System.out.println("Email longer than 50 characters.");
				}
				if(email.length()==0)
				{
					System.out.println("Email not inputted");
				}
				if(!email.contains("@"))
				{
					System.out.println("Email address did not contain \"@\" character.");
				}
				System.out.println("Re-enter Email");
				scan.reset();
				email=scan.nextLine().trim();
			}
			if(isDuplicateCustomer(email))
			{
				System.out.println("This email is already registered with GolfSmith");
			}
			else
			{
				validEmail=true;
			}
		}
		System.out.println("Input 10 digit phone number:");
		scan.reset();
		String phone = scan.nextLine().trim();
		boolean validPhoneNum=true;
		for(int i=0;i<phone.length();i++)
		{
			if(!Character.isDigit(phone.charAt(i)))
			{
				validPhoneNum=false;
			}
		}
		while(phone.length()!=10||validPhoneNum==false)
		{
			if(phone.length()!=10)
			{
				System.out.println("Phone number entered was not 10 digits.");
			}
			if(validPhoneNum==false)
			{
				System.out.println("Phone number did not contain all digits");
			}
			System.out.println("Re-enter phone number");
			scan.reset();
			phone=scan.nextLine().trim();
			validPhoneNum=true;
			for(int i=0;i<phone.length();i++)
			{
				if(!Character.isDigit(phone.charAt(i)))
				{
					validPhoneNum=false;
				}
			}
		}
		System.out.println("Input street address:");
		scan.reset();
		String street = scan.nextLine().trim();
		boolean validStreetAddress=false;
		for(int i=0;i<street.length();i++)
		{
			if(Character.isDigit(street.charAt(i)))
			{
				validStreetAddress=true;
			}
		}
		while(street.length()>40||validStreetAddress==false)
		{
			if(street.length()>40)
			{
				System.out.println("Street longer than 50 characters.");
			}
			if(validStreetAddress==false)
			{
				System.out.println("Street address did not contain a house/apt number.");
			}
			System.out.println("Re-enter Street Address");
			scan.reset();
			street=scan.nextLine().trim();
			 validStreetAddress=false;
			for(int i=0;i<street.length();i++)
			{
				if(Character.isDigit(street.charAt(i)))
				{
					validStreetAddress=true;
				}
			}
		}
		System.out.println("Input city:");
		scan.reset();
		String city = scan.nextLine().trim();
		while(city.matches(".*\\d+.*")||city.length()>25||city.length()==0)
		{
			if( city.matches(".*\\d+.*"))
			{
				System.out.println("City entered contains a numeric value.");
			}
			if(city.length()>15)
			{
				System.out.println("City entered longer than 25 characters.");
			}
			if(city.length()==0)
			{
				System.out.println("City was not inputted");
			}
			System.out.println("Re-enter City");
			scan.reset();
			city=scan.nextLine().trim();
		}
		
		System.out.println("Input state:");
		scan.reset();
		String state = scan.nextLine().trim();
		
		String[] states = new String[100];
		states[0] = "Alabama";
		states[1] = "Alaska";
		states[2] = "Arizona";
		states[3] = "Arkansas";
		states[4] = "California";
		states[5] = "Colorado";
		states[6] = "Connecticut";
		states[7] = "Delaware";
		states[8] = "Florida";
		states[9] = "Georgia";
		states[10] = "Hawaii";
		states[11] = "Idaho";
		states[12] = "Illinois";
		states[13] = "Indiana";
		states[14] = "Iowa";
		states[15] = "Kansas";
		states[16] = "Kentucky";
		states[17] = "Louisiana";
		states[18] = "Maine";
		states[19] = "Maryland";
		states[20] = "Massachusetts";
		states[21] = "Michigan";
		states[22] = "Minnesota";
		states[23] = "Mississippi";
		states[24] = "Missouri";
		states[25] = "Montana";
		states[26] = "Nebraska";
		states[27] = "Nevada";
		states[28] = "New Hampshire";
		states[29] = "New Jersey";
		states[30] = "New Mexico";
		states[31] = "New York";
		states[32] = "North Carolina";
		states[33] = "North Dakota";
		states[34] = "Ohio";
		states[35] = "Oklahoma";
		states[36] = "Oregon";
		states[37] = "Pennsylvania";
		states[38] = "Rhode Island";
		states[39] = "South Carolina";
		states[40] = "South Dakota";
		states[41] = "Tennessee";
		states[42] = "Texas";
		states[43] = "Utah";
		states[44] = "Vermont";
		states[45] = "Virginia";
		states[46] = "Washington";
		states[47] = "West Virginia";
		states[48] = "Wisconsin";
		states[49] = "Wyoming";
		states[50] = "AL";
		states[51] = "AK";
		states[52] = "AZ";
		states[53] = "AR";
		states[54] = "CA";
		states[55] = "CO";
		states[56] = "CT";
		states[57] = "DE";
		states[58] = "FL";
		states[59] = "GA";
		states[60] = "HI";
		states[61] = "ID";
		states[62] = "IL";
		states[63] = "IN";
		states[64] = "IA";
		states[65] = "KS";
		states[66] = "KY";
		states[67] = "LA";
		states[68] = "ME";
		states[69] = "MD";
		states[70] = "MA";
		states[71] = "MI";
		states[72] = "MN";
		states[73] = "MS";
		states[74] = "MO";
		states[75] = "MT";
		states[76] = "NE";
		states[77] = "NV";
		states[78] = "NH";
		states[79] = "NJ";
		states[80] = "NM";
		states[81] = "NY";
		states[82] = "NC";
		states[83] = "ND";
		states[84] = "OH";
		states[85] = "OK";
		states[86] = "OR";
		states[87] = "PA";
		states[88] = "RI";
		states[89] = "SC";
		states[90] = "SD";
		states[91] = "TN";
		states[92] = "TX";
		states[93] = "UT";
		states[94] = "VT";
		states[95] = "VA";
		states[96] = "WA";
		states[97] = "WV";
		states[98] = "WI";
		states[99] = "WY";
		
		boolean validState=false;
		for(int i=0;i<states.length;i++)
		{	state=state.toLowerCase();
			if(state.equals(states[i].toLowerCase().trim()))
			{
				validState=true;
			}
		}
		
		while(state.matches(".*\\d+.*")||state.length()>15||state.length()==0||validState==false)
		{
			if( state.matches(".*\\d+.*"))
			{
				System.out.println("State contains a numeric value.");
			}
			if(state.length()>15)
			{
				System.out.println("State longer than 15 characters.");
			}
			if(state.length()==0)
			{
				System.out.println("State was not inputted");
			}
			if(validState==false)
			{
				System.out.println("Invald State or state abbreviation entered");
			}
			System.out.println("Re-enter State");
			scan.reset();
			state=scan.nextLine().trim();
		    validState=false;
			for(int i=0;i<state.length();i++)
			{	state=state.toLowerCase();
				if(state.equals(states[i].toLowerCase().trim()))
				{
					validState=true;
				}
			}
		}
		System.out.println("Input digit zipcode:");
		scan.reset();
		String zip = scan.nextLine().trim();
		scan.reset();

		boolean validZip=true;
		for(int i=0;i<zip.length();i++)
		{
			if(!Character.isDigit(zip.charAt(i)))
			{
				validZip=false;
			}
		}
		while(zip.length()!=5||validZip==false)
		{
			if(zip.length()!=10)
			{
				System.out.println("zipcode entered was not 5 digits.");
			}
			if(validZip==false)
			{
				System.out.println("zipcode did not contain all digits");
			}
			System.out.println("Re-enter zipcode");
			scan.reset();
			zip=scan.nextLine().trim();
			validZip=true;
			for(int i=0;i<zip.length();i++)
			{
				if(!Character.isDigit(zip.charAt(i)))
				{
					validZip=false;
				}
			}
		}
		
		String getStartingUPC = "select customer_num from customer";
		ResultSet upcResults = null;
		try {
			 upcResults = s.executeQuery(getStartingUPC);
		} catch (SQLException e) {
			System.out.println("Error with running query");
			e.printStackTrace();
		}
		//loop through all the rows to find the biggest customer_num
		int biggestUPC=1;
		try {
			while(upcResults.next())
			{
				if(biggestUPC<Integer.parseInt(upcResults.getString("customer_num")))
				{
					biggestUPC=Integer.parseInt(upcResults.getString("customer_num"));
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String startUPC=Integer.toString(biggestUPC+1);
		String query="insert into customer (customer_num,fname,lname,email,phone,street,state,city,zipcode) values ('"+startUPC+"','"+fname+"','"+lname+"','"+email+"','"+phone+"','"+street+"','"+state+"','"+city+"','"+zip+"')";
		ResultSet results=null;
		try
		{
			results = s.executeQuery(query);
		}
		 catch(SQLException x)
	      {
	    	  System.out.println ("Executing query error");
	    	  System.out.println(x.getMessage());
	      }
		
		System.out.println("CUSTOMER SUCCESSFULLY ADDED.");
		System.out.println("----------------------------");
		System.out.println("");
		System.out.println("CUSTOMER NUMBER:"+startUPC);
		System.out.println("CUSTOMER FIRST NAME:"+fname);
		System.out.println("CUSTOMER LAST NAME:"+lname);
		System.out.println("CUSTOMER EMAIL:"+email);
		System.out.println("CUSTOMER PHONE:"+phone);
		System.out.println("CUSTOMER STREET:"+street);
		System.out.println("CUSTOMER CITY:"+city);
		System.out.println("CUSTOMER STATE:"+state);
		System.out.println("CUSTOMER ZIPCODE:"+zip);
		System.out.println("");
		return email;
	}
	public static boolean isDuplicateCustomer(String email)
	{
		Statement s = null;
		ResultSet results = null;
		try {
			s=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			results=s.executeQuery("select email from customer where email='"+email+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(results.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
