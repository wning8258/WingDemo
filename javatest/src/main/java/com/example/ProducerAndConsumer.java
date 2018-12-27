package com.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {



    public static void main(String[] args){

        Person person = new Person();

        new Thread(new Producer(person)).start();
        //new Thread(new Producer(person)).start();
      //  new Thread(new Producer(person)).start();

      //  new Thread(new Consumer(person)).start();
      //  new Thread(new Consumer(person)).start();
        new Thread(new Consumer(person)).start();
    }
}

class Person{
    private static final int MAX_NUM=1;
    private int num=0;
    private ReentrantLock lock=new ReentrantLock();
    private Condition produceCondition=lock.newCondition();
 //   private Condition consumeCondition=lock.newCondition();

    public void produce(){
        try {
            lock.lock();
            while(num==MAX_NUM){
                System.out.println("produce full,wait");
                produceCondition.await(); ////使当前线程加入 await() 等待队列中，并释放当锁，当其他线程调用signal()会重新请求锁。与Object.wait()类似。
            }
            num++;
            System.out.println("produce num :"+num);
            produceCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void consume(){
        try {
            lock.lock();
            while(num==0){
                System.out.println("consume is empty,wait");
                produceCondition.await();
            }
            num--;
            System.out.println("consume num :"+num);
            produceCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class Consumer implements Runnable{
    private Person person;
    Consumer(Person person){
        this.person=person;
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++){
            person.consume();
        }
    }
}


class Producer implements Runnable{
    private Person person;
    Producer(Person person){
        this.person=person;
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++){
            person.produce();
        }
    }
}

