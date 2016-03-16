package oo_project2;

/**
 * 调度器
 * Created by DESTRooooYER on 2016/3/8.
 */
public class DISP
{
	private req_que req;
	private int clock;


	public DISP(req_que req)
	{
		this.req = req;
		this.clock = 0;
	}

	public void godie()
	{
		lift lf = new lift();

		for (int i = 0; i < this.req.getN_of_request(); i++)
		{
			if (this.req.getBo(i) == false)
				continue;

			lf.move(this.req.getRequest(i));

		}
	}
}
