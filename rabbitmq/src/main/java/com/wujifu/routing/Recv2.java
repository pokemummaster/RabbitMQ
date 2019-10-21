package com.wujifu.routing;

import com.rabbitmq.client.*;
import com.wujifu.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static final String  QUEUE_NAME="test_work_queue";
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取channel
        final Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
        channel.basicQos(1);//保证一次只分发一个  


        //定义一个消费者
        Consumer consumer=new DefaultConsumer(channel){
            //消息到达 触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg=new String(body,"utf-8");
                System.out.println("[2] Recv msg:"+msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    System.out.println("[2] done ");
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        boolean autoAck=false;
        channel.basicConsume(QUEUE_NAME,autoAck , consumer);
    }

}