package oo_project2;

/**
 * Created by DESTR on 2016/3/15.
 */

interface _lift
{
	public int move(req_que req);

	public String toString();
}

public class lift_2 extends lift implements _lift
{
	private floor fl = new floor();
	private double clock;
	private int dt;
	private String tostr;
	private request[] req_q = new request[10000];
	private int front;
	private int rear;
	private boolean[] exc_req_q = new boolean[10000];


	public lift_2()
	{
		this.fl.setN(1);
		this.clock = 0;
		this.dt = 0;
		this.front = 0;
		this.rear = 0;
		for (int i = 0; i < 10000; i++)
			this.exc_req_q[i] = true;
	}

	public int move(req_que req)
	{

		this.tostr = "停靠信息：";

		int fl_begin = this.fl.getN();

		//先检查执行中的请求队列是否为空，不为空的话从中取出主请求
		//
		if (front < rear)
		{

		}

		//请求序列为空，从req中取请求，加入执行中的请求队列
		// ac
		else if (front == rear)
		{
			for (int i = 0; i < req.getN_of_request(); i++)
			{
				if (req.getBo(i) == true)  //取出第一个有效请求，req_q
				{
					req_q[rear++] = req.getRequest(i);
					if (this.clock < req.getRequest(i).getT())
						this.clock = req.getRequest(i).getT();
					req.setBo(i);
					break;
				}
			}
		}

		//若执行中的请求队列依然为空，则说明所有请求已经执行完毕
		// ac
		if (!(front < rear))
			return 0;   //return 0 结束DISP_2的循环

		//设定dt
		if (req_q[front].getN() > this.fl.getN())
			this.dt = 1;
		else if (req_q[front].getN() < this.fl.getN())
			this.dt = -1;
		else
		{
			if (req_q[front].getType() == 1)
				this.dt = req_q[front].getDt();
			else
				this.dt = 0;
		}

		/*
		//已有主请求，静止时检查捎带请求
		//ac
		for (int i = 0; i < req.getN_of_request(); i++)
		{
			if (req.getBo(i) == true)
			{
				if (this.dt == 1)   //向上
				{
					//ER
					if (req.getRequest(i).getType() == -1)
					{
						if (req.getRequest(i).getT() <= this.clock &&           //时间
								req.getRequest(i).getN() >= this.fl.getN())     //目标楼层>=当前楼层
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
					//FR
					else if (req.getRequest(i).getType() == 1)
					{
						if (req.getRequest(i).getT() <= this.clock &&               //时间
								req.getRequest(i).getN() >= this.fl.getN() &&       //目标楼层>=当前楼层
								req.getRequest(i).getN() <= req_q[front].getN() &&  //目标楼层<=主请求目标楼层
								req.getRequest(i).getDt() == this.dt)               //方向相同
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
				}
				else if (this.dt == -1)    //向下
				{
					//ER
					if (req.getRequest(i).getType() == -1)
					{
						if (req.getRequest(i).getT() <= this.clock &&           //时间
								req.getRequest(i).getN() <= this.fl.getN())     //目标楼层<=当前楼层
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
					//FR
					else if (req.getRequest(i).getType() == 1)
					{
						if (req.getRequest(i).getT() <= this.clock &&               //时间
								req.getRequest(i).getN() <= this.fl.getN() &&       //目标楼层<=当前楼层
								req.getRequest(i).getN() >= req_q[front].getN() &&  //目标楼层>=主请求目标楼层
								req.getRequest(i).getDt() == this.dt)               //方向相同
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
				}
				else if (this.dt == 0)
				{
					//ER
					if (req.getRequest(i).getType() == -1)
					{
						if (req.getRequest(i).getT() <= this.clock &&           //时间
								req.getRequest(i).getN() == this.fl.getN())     //目标楼层<=当前楼层
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
				}
			}
		}
		*/

		//执行主请求，移动中检查捎带请求
		//ac
		//向上
		if (this.dt == 1)
		{
			int mov_flag = 0;
			for (int i = fl_begin; i <= req_q[front].getN(); )
			{
				int door_flag = 0;


				//检查 请求出队
				for (int j = front; j < rear; j++)
				{
					if (exc_req_q[j] == true && req_q[j].getN() == i)  //到达则下车
					{
						door_flag = 1;
						exc_req_q[j] = false;
					}
				}

				if (door_flag == 1) //捎带请求出队，开关门
				{
					if (mov_flag == 1)
					{
						this.tostr += "(" + String.valueOf(i);
						this.tostr += ",UP,";
						this.tostr += String.valueOf(this.clock) + ")";
					}
					this.clock++;
					mov_flag = 0;
				}

				//检查 捎带请求入队
				if (i == fl_begin || door_flag == 1) //静止情况的检测
				{
					for (int j = 0; j < req.getN_of_request(); j++)
					{
						if (req.getBo(j) == true)
						{

							if(i==req_q[front].getN())
							{
								//ER
								if (req.getRequest(j).getType() == -1)
								{
									if (req.getRequest(j).getT() <= this.clock-1 &&           //时间
											req.getRequest(j).getN() >= this.fl.getN())     //目标楼层>=当前楼层
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);

										door_flag = 1;    //一楼的入队了的话还是0就不行了
									}
								}
								//FR
								else if (req.getRequest(j).getType() == 1)
								{
									if (req.getRequest(j).getT() <= this.clock-1 &&               //时间
											req.getRequest(j).getN() >= this.fl.getN() &&       //目标楼层>=当前楼层
											req.getRequest(j).getN() <= req_q[front].getN() &&  //目标楼层<=主请求目标楼层
											req.getRequest(j).getDt() == this.dt)               //方向相同
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);

										door_flag = 1;    //一楼的入队了的话还是0就不行了
									}
								}
							}

							else
							{
								//ER
								if (req.getRequest(j).getType() == -1)
								{
									if (req.getRequest(j).getT() <= this.clock &&           //时间
											req.getRequest(j).getN() >= this.fl.getN())     //目标楼层>=当前楼层
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);

										door_flag = 1;    //一楼的入队了的话还是0就不行了
									}
								}
								//FR
								else if (req.getRequest(j).getType() == 1)
								{
									if (req.getRequest(j).getT() <= this.clock &&               //时间
											req.getRequest(j).getN() >= this.fl.getN() &&       //目标楼层>=当前楼层
											req.getRequest(j).getN() <= req_q[front].getN() &&  //目标楼层<=主请求目标楼层
											req.getRequest(j).getDt() == this.dt)               //方向相同
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);

										door_flag = 1;    //一楼的入队了的话还是0就不行了
									}
								}
							}
						}
					}
				}
				else    //运动情况的检测
				{
					for (int j = 0; j < req.getN_of_request(); j++)
					{
						if (req.getBo(j) == true)
						{
							//ER
							if (req.getRequest(j).getType() == -1)
							{
								if (req.getRequest(j).getT() <= this.clock &&           //时间
										req.getRequest(j).getN() > this.fl.getN())     //目标楼层>当前楼层
								{
									req_q[rear++] = req.getRequest(j);
									req.setBo(j);

									door_flag = 1;    //一楼的入队了的话还是0就不行了
								}
							}
							//FR
							else if (req.getRequest(j).getType() == 1)
							{
								if (req.getRequest(j).getT() <= this.clock &&               //时间
										req.getRequest(j).getN() > this.fl.getN() &&       //目标楼层>当前楼层
										req.getRequest(j).getN() <= req_q[front].getN() &&  //目标楼层<=主请求目标楼层
										req.getRequest(j).getDt() == this.dt)               //方向相同
								{
									req_q[rear++] = req.getRequest(j);
									req.setBo(j);

									door_flag = 1;    //一楼的入队了的话还是0就不行了
								}
							}


						}
					}
				}

				if (door_flag == 0)
				{
					if (i < req_q[front].getN())
					{
						clock += 0.5;
						this.fl.setN(i + 1);
					}
					mov_flag = 1;
					i++;

				}   //开门时为静止判断，可能有同楼层入队，楼层数不能加
			}
		}


		//向下
		else if (this.dt == -1)
		{
			int mov_flag = 0;
			for (int i = fl_begin; i >= req_q[front].getN(); )
			{
				int door_flag = 0;


				//检查 捎带请求出队
				for (int j = front; j < rear; j++)
				{
					if (exc_req_q[j] == true && req_q[j].getN() == i)  //到达则下车
					{
						door_flag = 1;
						exc_req_q[j] = false;

					}
				}

				if (door_flag == 1) //捎带请求出队，开关门
				{
					if (mov_flag == 1)
					{
						this.tostr += "(" + String.valueOf(i);
						this.tostr += ",DOWN,";
						this.tostr += String.valueOf(this.clock) + ")";
					}
					this.clock++;
					mov_flag = 0;
				}

				//检查 捎带请求入队
				if (i == fl_begin || door_flag == 1)  //静止情况的检测
				{
					for (int j = 0; j < req.getN_of_request(); j++)
					{
						if (req.getBo(j) == true)
						{
							if(req_q[front].getN()==i)
							{
								//ER
								if (req.getRequest(j).getType() == -1)
								{
									if (req.getRequest(j).getT() <= this.clock-1 &&           //时间
											req.getRequest(j).getN() <= this.fl.getN())     //目标楼层<=当前楼层
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);
									}
								}
								//FR
								else if (req.getRequest(j).getType() == 1)
								{
									if (req.getRequest(j).getT() <= this.clock-1 &&               //时间
											req.getRequest(j).getN() <= this.fl.getN() &&       //目标楼层<=当前楼层
											req.getRequest(j).getN() >= req_q[front].getN() &&  //目标楼层>=主请求目标楼层
											req.getRequest(j).getDt() == this.dt)               //方向相同
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);
									}
								}
							}
							else
							{
								//ER
								if (req.getRequest(j).getType() == -1)
								{
									if (req.getRequest(j).getT() <= this.clock &&           //时间
											req.getRequest(j).getN() <= this.fl.getN())     //目标楼层<=当前楼层
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);
									}
								}
								//FR
								else if (req.getRequest(j).getType() == 1)
								{
									if (req.getRequest(j).getT() <= this.clock &&               //时间
											req.getRequest(j).getN() <= this.fl.getN() &&       //目标楼层<=当前楼层
											req.getRequest(j).getN() >= req_q[front].getN() &&  //目标楼层>=主请求目标楼层
											req.getRequest(j).getDt() == this.dt)               //方向相同
									{
										req_q[rear++] = req.getRequest(j);
										req.setBo(j);
									}
								}
							}
						}
					}
				}
				else    //运动情况的检测
				{
					for (int j = 0; j < req.getN_of_request(); j++)
					{
						if (req.getBo(j) == true)
						{
							//ER
							if (req.getRequest(j).getType() == -1)
							{
								if (req.getRequest(j).getT() <= this.clock &&           //时间
										req.getRequest(j).getN() < this.fl.getN())     //目标楼层<当前楼层
								{
									req_q[rear++] = req.getRequest(j);
									req.setBo(j);
								}
							}
							//FR
							else if (req.getRequest(j).getType() == 1)
							{
								if (req.getRequest(j).getT() <= this.clock &&               //时间
										req.getRequest(j).getN() < this.fl.getN() &&       //目标楼层<当前楼层
										req.getRequest(j).getN() >= req_q[front].getN() &&  //目标楼层>=主请求目标楼层
										req.getRequest(j).getDt() == this.dt)               //方向相同
								{
									req_q[rear++] = req.getRequest(j);
									req.setBo(j);
								}
							}

						}
					}
				}

				if (door_flag == 0)
				{
					if (i > req_q[front].getN())
					{
						clock += 0.5;
						this.fl.setN(i - 1);
					}
					mov_flag = 1;
					i--;
				}   //开门时为静止判断，可能有同楼层入队，楼层数不能加
			}
		}
		else if (this.dt == 0)
		{
			for (int i = 0; i < req.getN_of_request(); i++)
			{
				if (req.getBo(i) == true)
				{
					//ER
					if (req.getRequest(i).getType() == -1)
					{
						if (req.getRequest(i).getT() <= this.clock &&           //时间
								req.getRequest(i).getN() <= this.fl.getN())     //目标楼层<=当前楼层
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
						}
					}
					//FR
					else if (req.getRequest(i).getType() == 1)
					{
						if (req.getRequest(i).getT() <= this.clock &&               //时间
								req.getRequest(i).getN() <= this.fl.getN() &&       //目标楼层<=当前楼层
								req.getRequest(i).getN() >= req_q[front].getN() &&  //目标楼层>=主请求目标楼层
								(req.getRequest(i).getDt() == this.dt ||
										this.dt == 0))                   //方向相同or无方向
						{
							req_q[rear++] = req.getRequest(i);
							req.setBo(i);
							this.dt = req.getRequest(i).getDt();
						}
					}

				}
			}
			for (int i = front; i < rear; i++)
			{
				if (exc_req_q[i] == true && req_q[i].getN() == req_q[front].getN())  //到达则下车
				{
					exc_req_q[i] = false;
				}
			}
		}

		//到达主请求目标楼层，结束主请求，准备tostr
		//
//		for (int i = front; i < rear; i++)
//		{
//			if (exc_req_q[i] == true && req_q[i].getN() == req_q[front].getN())  //到达则下车
//			{
//				exc_req_q[i] = false;
//			}
//		}
//		this.clock++;
		this.fl.setN(req_q[front].getN());

		this.tostr += "\n捎带信息：";
		if (req_q[front].getType() == 1)
			this.tostr += "(FR,";
		else if (req_q[front].getType() == -1)
			this.tostr += "(ER,";
		else
			exph.err(-1);
		this.tostr += String.valueOf(req_q[front].getN()) + ",";
		if (req_q[front].getType() == 1)
			if (req_q[front].getDt() == 1)
				this.tostr += "UP,";
			else if (req_q[front].getDt() == -1)
				this.tostr += "DOWN,";
			else
				exph.err(-1);
		this.tostr += String.valueOf(req_q[front].getT()) + ")(";


		for (int i = front + 1; i < rear; i++)
		{
			if (req_q[i].getType() == 1)
				this.tostr += "(FR,";
			else if (req_q[i].getType() == -1)
				this.tostr += "(ER,";
			else
				exph.err(-1);
			this.tostr += String.valueOf(req_q[i].getN()) + ",";
			if (req_q[i].getType() == 1)
				if (req_q[i].getDt() == 1)
					this.tostr += "UP,";
				else if (req_q[i].getDt() == -1)
					this.tostr += "DOWN,";
				else
					exph.err(-1);
			this.tostr += String.valueOf(req_q[i].getT()) + ")";
		}
		this.tostr += ")\n";

		for (; front < rear; front++)
		{
			if (exc_req_q[front] == true)
				break;
		}

		return 1;
	}

	public String toString()
	{
		return this.tostr;
	}
}
