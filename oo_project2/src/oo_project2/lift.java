package oo_project2;

/**
 * 电梯类
 * Created by DESTRooooYER on 2016/3/8.
 */
public class lift
{
	private floor fl = new floor();
	private double clock;
	private int dt;

	public lift()
	{
		this.fl.setN(1);
		this.clock = -1;
		this.dt = 0;
	}

	public void move(request req)
	{
		//if (clock != 0)
		clock++;

		clock = req.getT() > clock ? req.getT() : clock;

		clock += (Math.abs(fl.getN() - req.getN()) * 0.5);
		dt = req.getN() - fl.getN();
		if (fl.getN() != req.getN())
		{
			this.fl.setN(req.getN());
			this.print();
		}
		this.fl.setN(req.getN());
	}

	private void print()
	{
		System.out.print("(");
		System.out.print(this.fl.getN());
		if (this.dt > 0)
			System.out.print(",UP,");
		else if (this.dt < 0)
			System.out.print(",DOWN,");
		System.out.print(clock);
		System.out.print(")\n");

	}
}
