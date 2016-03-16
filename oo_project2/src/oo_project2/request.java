package oo_project2;

/**
 * 请求类
 * Created by DESTRooooYER on 2016/3/8.
 */
public class request
{
	private int type, n, t, dt;

	public int getN()
	{
		return this.n;
	}

	public int getType()    //FR:1/ER:-1
	{
		return this.type;
	}

	public int getT()
	{
		return this.t;
	}

	public int getDt()
	{
		return this.dt;
	}

	public request(String str)
	{
		String str_tmp[] = str.split(",");
		int j = 0;
		for (int i = 0; i < str_tmp.length; i++)
		{
			if (!str_tmp[i].equals(""))
			{
				if (j == 0)
				{
					if (str_tmp[i].equals("FR"))        //楼层内请求
						this.type = 1;
					else if (str_tmp[i].equals("ER"))   //电梯内请求
						this.type = -1;
					else                        //以防万一
						exph.err(-1);
				}
				else
				{
					if (this.type == 1)            //楼层内请求
					{
						if (j == 1)
						{
							try
							{
								this.n = Integer.parseInt(str_tmp[i]);
							} catch (java.lang.NumberFormatException e)
							{
								//exph.err(1);
								this.n = 0;
							}
						}
						else if (j == 2)
						{
							if (str_tmp[i].equals("UP"))            //楼层内请求
								this.dt = 1;
							else if (str_tmp[i].equals("DOWN"))     //电梯内请求
								this.dt = -1;
							else                            //以防万一
								exph.err(-1);
						}
						else if (j == 3)
						{
							try
							{
								this.t = Integer.parseInt(str_tmp[i]);
							} catch (java.lang.NumberFormatException e)
							{
								exph.err(1);
							}
						}
					}
					else if (this.type == -1)          //电梯内请求
					{
						if (j == 1)
						{
							try
							{
								this.n = Integer.parseInt(str_tmp[i]);
							} catch (java.lang.NumberFormatException e)
							{
								//exph.err(1);
								this.n = 0;
							}
						}
						else if (j == 2)
						{
							try
							{
								this.t = Integer.parseInt(str_tmp[i]);
							} catch (java.lang.NumberFormatException e)
							{
								exph.err(1);
							}
						}
					}
				}


				j++;
			}
		}
	}

	public void print()
	{
		if (this.type == 1)
		{
			System.out.print("FR/");
			System.out.print(this.n);
			System.out.print("/");
			System.out.print(this.dt);
			System.out.print("/");
			System.out.print(this.t);
			System.out.println();
		}
		else if (this.type == -1)
		{
			System.out.print("ER/");
			System.out.print(this.n);
			System.out.print("/");
			System.out.print(this.t);
			System.out.println();
		}
	}
}
