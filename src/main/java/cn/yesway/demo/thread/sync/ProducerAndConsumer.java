package cn.yesway.demo.thread.sync;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @date 2017/8/28 10:02
 * @desc
 * 实现一个生产者消费者模式，生产者一次生产10个，消费者一次消费3个，
 * 如果仓库中多余500个，停止生产，通知消费者消费；
 * 当仓库少于100个，停止消费，通知生产者生产；
 */
public class ProducerAndConsumer {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        new Thread(new MyProducer(warehouse),"生产者").start();
        new Thread(new MyProducer(warehouse),"生产者").start();
        new Thread(new Consumer(warehouse),"消费者").start();
        new Thread(new Consumer(warehouse),"消费者").start();
        new Thread(new Consumer(warehouse),"消费者").start();
        new Thread(new Consumer(warehouse),"消费者").start();
        new Thread(new Consumer(warehouse),"消费者").start();
    }
}

//仓库
class Warehouse{
    static Integer MAX_CAPACITY = 100;

    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 已0长的byte数组作为锁比较省空间
     */
    private  byte[] lock = new byte[0];

    private LinkedList<Object> list = new LinkedList<Object>();

    public  Warehouse(){
    }

    public void add(Integer num){
        synchronized (lock){

            if(count.intValue()+num>MAX_CAPACITY){
                try {
                    System.out.println(Thread.currentThread().getName()+"--库存过剩，等待消费者消费");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                count.addAndGet(num);
            }
            lock.notifyAll();
        }
    }
    public void sub(Integer num) {
        synchronized (lock){
            if(count.intValue()-num<0){
                try {
                    System.out.println(Thread.currentThread().getName()+"--库存不足，等待生产");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                for(int i=0;i<num;i++){
                    count.decrementAndGet();
                }
            }
            lock.notifyAll();
        }
    }

    public int getCount() {
        return count.intValue();
    }
}
//生产者类
class MyProducer implements Runnable{
    private Warehouse warehouse;
    public MyProducer(Warehouse warehouse){
        this.warehouse = warehouse;
    }
    /**
     * 生产
     */
    public void produce(Integer num){
            warehouse.add(num);
            System.out.println("生产线程："+Thread.currentThread().getName()+"生产了"+num+"个产品,当前仓库库存："+warehouse.getCount());
    }

    public void run() {
        for(;;){
            produce(10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
//消费者类
class Consumer implements Runnable{
    private Warehouse warehouse;
    public Consumer(Warehouse warehouse){
        this.warehouse = warehouse;
    }
    /**
     * 消费
     */
    public void consume(Integer num){
            warehouse.sub(num);
            System.out.println("消费线程："+Thread.currentThread().getName()+"消费了"+num+"个产品,当前仓库库存："+warehouse.getCount());
    }

    public void run() {
        for(;;){
            consume(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}