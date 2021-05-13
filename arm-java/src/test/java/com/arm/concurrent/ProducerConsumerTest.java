package com.arm.concurrent;

import com.arm.util.ArmUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 生产者消费者模式
 *
 * @author zhaolangjing
 * @since 2021-3-12 20:54
 */
@Slf4j
public class ProducerConsumerTest {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue( 5 );
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread( () -> {
                messageQueue.put( new Message( id, "消息" + id ) );
            } ).start();
        }
        new Thread( () -> {
            while (true) {
                ArmUtil.sleep( 1 ); // 模拟消费者定时去访问队列是否有消息
                messageQueue.get();
            }
        } ).start();
    }
}

/**
 * 消息队列 , java线程之间通信的类
 */
@Slf4j
class MessageQueue {
    // 双向队列实现了一进一出的模式
    private static LinkedList<Message> list = new LinkedList<>();
    // 保证队列中的数据的长度，不能无线制增长造成oom
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    //获取消息
    public Message get() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.info( "队列为空，消费者等待消息" );
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            log.info( "得到一个消息：{}", message );
            list.notifyAll(); // 当消费后，需要唤醒已满的队列
            return message; // 返回队列中的第一个元数
        }
    }

    // 放消息
    public void put(Message message) {
        synchronized (list) {
            while (list.size() >= capacity) { //队列已满，不能接收消息了
                try {
                    log.info( "队列已满，不接受生产的消息" );
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info( "往队列添加消息：{}", message );
            list.addLast( message );  // 在队列尾部添加消息
            list.notifyAll();
        }
    }
}

/**
 * 存放消息类 , 去掉set方法和加final保证了内容只能在初始化设值，消息不会被更改
 */
final class Message {
    private int id; //消息id，唯一标识
    private Object message; //消息内容
    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }
    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", message=" + message +
                '}';
    }
}
