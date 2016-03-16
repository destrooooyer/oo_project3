package oo_project2;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by DESTRooooYER on 2016/3/8.
 */
public class Main
{

	/**
	 * 检测输入是否合法
	 * @param str
	 * @return
	 */
	private static int check(String str)
	{
		Pattern pt;
		pt = Pattern.compile("^((\\(FR,[0-9]+,((UP)|(DOWN)),[0-9]+\\))|(\\(ER,[0-9]+,[0-9]+\\)))+$");

		try
		{
			if (pt.matcher(str).find())
				return 1;
			else
				return 0;
		}
		catch (java.lang.StackOverflowError e)
		{
			exph.err(3);
		}
		return 0;
	}


	public static void main(String wtf[])
	{
		String str_in;
		Scanner scanner = new Scanner(System.in);
		str_in = scanner.nextLine();

		if (check(str_in) == 1)
		{
			req_que req = new req_que(str_in);

			req.check();

			//req.print();
//			DISP disp = new DISP(req);
//
//			disp.godie();

			DISP_2 disp=new DISP_2(req);
			disp.ALS_Schedule();

		}
		else
			exph.err(0);

	}
}
