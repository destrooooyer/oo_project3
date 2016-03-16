package oo_project2;

/**
 * exception handler
 * Created by DESTRooooYER on 2016/3/8.
 */
public class exph
{
	public static void err(int x)
	{
		if (x == 1)
			System.out.println("数字太尼玛大了，吓得我int都要溢出了");
		else if (x == -1)
			System.out.println("Why on earth could this happen?");
		else if (x == 0)
			System.out.println("输入不符合规则，吓得我都报错了");
		else if (x == 3)
			System.out.println("输入的太尼玛长了，吓得我栈都要爆了");
		else if(x==4)
			System.out.println("输入的太尼玛长了，吓得我都要数组越界了");

		System.exit(0);
	}
}
