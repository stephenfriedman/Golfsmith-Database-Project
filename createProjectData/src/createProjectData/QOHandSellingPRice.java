package createProjectData;

import java.text.DecimalFormat;
import java.util.Random;

public class QOHandSellingPRice {

	public static void main (String[]args)
	{
		for(int j=0;j<500;j++)
		{
			Random r =new Random();
			int upcraw= (int)(r.nextDouble()*100);
			System.out.println(upcraw);
		}
		System.out.println("--------------------------");
		for(int j=0;j<500;j++)
		{
			Random r =new Random();
			double price= r.nextDouble()*400;
			DecimalFormat df = new DecimalFormat("###.00");
			String formatted=df.format(price);
			System.out.println(formatted);
		}
	}
}
