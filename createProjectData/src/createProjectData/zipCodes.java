package createProjectData;
import java.util.Random;
public class zipCodes {

	public static void main (String args[])
	{
		Random r = new Random();
		for(int i=0;i<52;i++)
		{
			double zipcode=r.nextDouble()*99999;
			if(zipcode<10000)
			{
				zipcode=zipcode*10-1;
			}
			int z= (int)zipcode;
			System.out.println(z);
			r=new Random();
		}
	}
}
