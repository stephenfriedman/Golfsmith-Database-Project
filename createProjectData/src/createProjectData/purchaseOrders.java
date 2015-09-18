package createProjectData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class purchaseOrders {
	static Connection con=null;

public static void main(String[]args)
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
			System.out.println("Input STEPHEN FRIEDMAN password from coursesite to login in to his Oracle databases.");
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
		ResultSet r=null;
		Statement s=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String[][]pos=new String[6][100];
		for(int i=0;i<100;i++)
		{
			pos[0][i]=getNextPrimaryKey("purchase_orders","po_num");
			int storeNum=(int)(Math.random()*53+1);
			pos[1][i]=Integer.toString(storeNum);
			int venNum=(int)(Math.random()*14+1);
			pos[2][i]=Integer.toString(venNum);
			String upc="100000000";
			int randEndUPC=(int)(Math.random()*29);
			//System.out.println(randEndUPC);
			if(randEndUPC<10)
			{
				upc=upc+"0";
				upc=upc+Integer.toString(randEndUPC);
			}
			else
			{
				upc=upc+Integer.toString(randEndUPC);
			}
			pos[3][i]=upc;
			int quantity=(int)(Math.random()*40);
			double unitPrice=Math.random()*100+1.21;
			double totalPrice=quantity*unitPrice;
			DecimalFormat df = new DecimalFormat("####.00");
			String formatted=df.format(totalPrice);
			pos[4][i]=formatted;
			pos[5][i]=Integer.toString(quantity);
			
			try {
				//System.out.println(pos[0][i]+"  "+pos[1][i]+"  "+pos[2][i]+"  "+pos[3][i]+"  "+pos[4][i]+"  "+ pos[5][i]+"  ");
				r=s.executeQuery("insert into purchase_orders (po_num,store_num,ven_num,upc,buying_price,quantity) values('"+pos[0][i]+"','"+pos[1][i]+"','"+pos[2][i]+"','"+pos[3][i]+"',"+pos[4][i]+","+pos[5][i]+")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("FINISHED");
		
}

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

}
