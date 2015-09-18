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

public class salesOrders {
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
		
		String[][]sos=new String[6][50];
		for(int i=0;i<50;i++)
		{
			sos[0][i]=getNextPrimaryKey("sales_order","so_num");
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
			sos[1][i]=upc;
			int custNum=(int)(Math.random()*109+1);
			sos[2][i]=Integer.toString(custNum);
			int storeNum=(int)(Math.random()*53+1);
			sos[5][i]=Integer.toString(storeNum);
			int quantityRem=quantityRemaining(Integer.toString(storeNum),upc);
			if(quantityRem<1)
			{
				sos[3][i]="0";
			}
			else if(quantityRem<4 && quantityRem>1)
			{
				sos[3][i]="1";
			}
			else
			{
				sos[3][i]=Integer.toString((int)(Math.random()*3+1));
			}
			double unitPrice=getPrice(upc,Integer.toString(storeNum));
			int q=Integer.parseInt(sos[3][i]);
			double totalPrice=q*unitPrice;
			DecimalFormat df=new DecimalFormat("####.00");
			String formatted=df.format(totalPrice);
			sos[4][i]=formatted;
			
			try {
				//System.out.println(pos[0][i]+"  "+pos[1][i]+"  "+pos[2][i]+"  "+pos[3][i]+"  "+pos[4][i]+"  "+ pos[5][i]+"  ");
				r=s.executeQuery("insert into sales_order (so_num,upc,customer_num,quantity,price,store_num) values('"+sos[0][i]+"','"+sos[1][i]+"','"+sos[2][i]+"',"+sos[3][i]+","+sos[4][i]+",'"+sos[5][i]+"')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("FINISHED");
		
	}
	
	
	public static double getPrice(String upc, String store)
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
			r=s.executeQuery("select selling_price from inventory where upc='"+upc+"' and store_num='"+store+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(r.next())
			{
				return r.getDouble("selling_price");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
