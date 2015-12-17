package cn.yesway.demo.thread;

import java.util.concurrent.TimeUnit;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月15日 上午11:32:49 
 * 
 */
public class VolatileUsage {
		private static 	boolean stop;
		
		public static void main(String[] args) throws Exception {
			Thread task = new Thread(new Runnable() {
				@Override
				public void run() {
					int i=0;
					while(!stop){
						i++;
						System.out.println(i);
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			task.start();
			TimeUnit.SECONDS.sleep(3);
			stop = true;
		}
}
