package createProjectData;

import java.util.Random;

public class phoneNum {
	public static void main (String args[])
	{
		for(int j=0;j<5;j++)
		{
			String phoneNum="";
			Random r = new Random();
			for(int i=0;i<10;i++)
			{
				double num=r.nextDouble()*10;
				int n=(int)num;
				String toAdd=Integer.toString(n);
				phoneNum+=toAdd;
				r=new Random();
			}
			
			System.out.println(phoneNum);
		}
	}
}
