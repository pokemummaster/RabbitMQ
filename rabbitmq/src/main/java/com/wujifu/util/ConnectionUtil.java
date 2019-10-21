package com.wujifu.util;
import java.io.IOException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection() throws IOException{
    	//定义连接工厂
    	ConnectionFactory factory = new ConnectionFactory();
    	//定义连接地址
    	factory.setHost("192.168.13.132");
    	//定义端口
    	factory.setPort(5672);
    	//设置账号信息，用户名、密码、vhost
    	factory.setVirtualHost("/vhose_1");
    	factory.setUsername("wujifu");
    	factory.setPassword("123456");
    	// 通过工厂获取连接
    	Connection connection = factory.newConnection();
    	return connection;
    }
}