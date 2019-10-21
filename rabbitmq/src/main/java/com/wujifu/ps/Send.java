package com.wujifu.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wujifu.util.ConnectionUtil;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange 交换机 转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //fanout 分裂
        // 消息内容
        String message = "Hello PB";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}