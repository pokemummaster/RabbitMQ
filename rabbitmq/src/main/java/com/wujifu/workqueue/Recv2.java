package com.wujifu.workqueue;

import com.rabbitmq.client.*;
import com.wujifu.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;

public class Recv2 {

    private final static String QUEUE_NAME  = "test_queue_work";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //定义一个消息的消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [2] Received '" + message + "'");
                try {
                        Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }

        };
        boolean autoAck = true; //
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
