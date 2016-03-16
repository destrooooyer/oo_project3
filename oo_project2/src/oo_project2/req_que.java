package oo_project2;

/**
 * 请求队列
 * Created by DESTRooooYER on 2016/3/8.
 */
public class req_que
{
	private request[] rq = new request[10000];
	private boolean[] bo = new boolean[10000];
	private int n_of_request;

	public int getN_of_request()
	{
		return n_of_request;
	}

	public boolean getBo(int x)
	{
		return bo[x];
	}

	public void setBo(int x)    //将request[x]标记为已执行
	{
		bo[x]=false;
	}

	public request getRequest(int x)
	{
		return rq[x];
	}


	public req_que(String str)
	{
		for (int i = 0; i < 10000; i++)
			this.bo[i] = true;

		this.n_of_request = 0;

		String str_tmp[] = str.split("[\\(\\)]");
		for (String str_i : str_tmp)
		{
			if (!str_i.equals(""))
			{
				this.rq[n_of_request++] = new request(str_i);

				if (n_of_request > 10000)
					exph.err(4);
			}
		}
	}

	public void check()
	{

		//排除无效数据
		for (int i = 0; i < n_of_request; i++)
		{
			//10上，1下
			if (rq[i].getType() == 1)
			{
				if (rq[i].getDt() == 1 && rq[i].getN() == 10)
					bo[i] = false;
				else if (rq[i].getDt() == -1 && rq[i].getN() == 1)
					bo[i] = false;
			}
			//不属于1~10
			if (rq[i].getN() < 1 || rq[i].getN() > 10)
				bo[i] = false;
		}

		//检查输入序列是否按从小到大
		for (int i = 0; i < n_of_request - 1; i++)
		{
			if (rq[i].getT() > rq[i + 1].getT() && bo[i] && bo[i + 1])
				exph.err(0);
		}

		//检查第一个是不是0
		for (int i = 0; i < n_of_request; i++)
		{
			if (bo[i] == true)
			{
				if (rq[i].getT() != 0)
					exph.err(0);
				else
					break;
			}
		}

	}

	public void print()
	{
		for (int i = 0; i < n_of_request; i++)
			if (bo[i])
				rq[i].print();
	}


}
