package crawler;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    private static final String url = "tcp://artemis:61616";
    private static final String username = "artemis";
    private static final String password = "artemis";
    private static final String queueName = "MESSAGE_QUEUE";

    public static void sendMessage(String fileName) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url, username, password);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(fileName);
            producer.send(textMessage);
            logger.info("Message sent: " + fileName);

            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.error("Exception: " + e);
            throw new RuntimeException(e);
        }
    }
}
