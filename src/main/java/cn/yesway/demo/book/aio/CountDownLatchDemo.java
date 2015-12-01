package cn.yesway.demo.book.aio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;


/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月23日 上午10:10:30 
 * 
 */
public class CountDownLatchDemo {
	
	private static SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(2);//两个人协同工作
		Worker worker1 = new  Worker("zhangshan",8000,countDownLatch);
		Worker worker2 = new  Worker("lisi",5000,countDownLatch);
		worker1.start();
		worker2.start();
		countDownLatch.await();//等待所有工人完成工作  
		 System.out.println("all work done at "+df.format(new Date()));  
	}
}

class Worker extends  Thread{
	private static SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String name;
	private int times;
	private CountDownLatch countDownLatch;
	public Worker(String name, int times, CountDownLatch countDownLatch) {
		this.name =name;
		this.times = times;
		this.countDownLatch = countDownLatch;
	}
	
	public void work(){
		 try {  
             Thread.sleep(times);  
         } catch (InterruptedException e) {  
             e.printStackTrace();  
         } 
	}

	@Override
	public void run() {
		System.out.println("Worker "+name+" do work begin at "+df.format(new Date()));
		work();
		countDownLatch.countDown();
		System.out.println("Worker "+name+" do work complete  at "+df.format(new Date()));
	}
	
}
