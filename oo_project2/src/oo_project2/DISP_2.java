package oo_project2;

/**
 * Created by DESTR on 2016/3/15.
 */
public class DISP_2 extends DISP
{
	private req_que req;
	private int clock;

	public DISP_2(req_que req)
	{
		super(req);
		this.req = req;
		this.clock = 0;
	}

	public void ALS_Schedule()
	{
		lift_2 lift=new lift_2();

		while (lift.move(req)==1)
		{
			System.out.println(lift.toString());
		}
	}
}
