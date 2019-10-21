package com.wujifu.simple;

import java.io.IOException;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.wujifu.util.ConnectionUtil;

public class Recv {
	private static final String QUEUE_NAME  = "test_queue";

	public static void main(String[] args) throws Exception {

		/* 获取一个连接 */
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		//声明队列  如果能确定是哪一个队列 这边可以删掉,不去掉 这里会忽略创建
		//channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			//获取到达的消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};

		//监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	@SuppressWarnings("deprecation")
	private static void oldGet(Channel channel) throws IOException, InterruptedException {
		//定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		// 获取消息
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
		}
	}


}
