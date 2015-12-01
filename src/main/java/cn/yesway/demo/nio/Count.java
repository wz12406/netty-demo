package cn.yesway.demo.nio;
/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月20日 下午1:54:48 
 * 
 */
public class Count implements Runnable{
	public volatile Integer count=0;
	public synchronized void inc(){
			count++;
			System.out.println(Thread.currentThread().getName()+"将count+1:"+count);
	}

	@Override
	public void run() {
		this.inc();
	}
	
	public static void main(String[] args) {
		Count count = new Count();
		for (int i = 0; i < 1000; i++) {
            Thread a= new Thread(count);
            try {
            	a.start();
            	a.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
		
		System.out.println("运行结果:Counter.count=" + count.count);
	}
}
