package com.wujifu.simple;
import java.io.IOException;
 import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wujifu.util.ConnectionUtil;

public class Send {
	
    private final static String QUEUE_NAME="test_queue";
    
    public static void main(String[] args) throws IOException {
		//获取到连接以及mq通道
    	Connection connection = ConnectionUtil.getConnection();
    	//从连接中创建通道
    	Channel channel = connection.createChannel();
    	
    	//声明(创建)队列
    	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    	
    	//消息内容
    	String message = "Hello World!22";
    	channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    	System.out.println("[product] Send '"+ message +"'");
    	
    	//关闭通道和连接
    	channel.close();
    	connection.close();
    }
}