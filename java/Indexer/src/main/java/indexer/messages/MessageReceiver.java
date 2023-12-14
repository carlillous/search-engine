package indexer.messages;


import static indexer.messages.artemis.Config.password;
import static indexer.messages.artemis.Config.queueName;
import static indexer.messages.artemis.Config.url;
import static indexer.messages.artemis.Config.username;

import indexer.impl.Indexer;
import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);


    private final Indexer indexer;

    public MessageReceiver(Indexer indexer) {
        this.indexer = indexer;
    }

    public void start() {
        try(ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url, username, password)) {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            logger.info("Connection to ActiveMQ established.");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(queue);

            while(true) {
                Message messageReceived = consumer.receive();
                onMessage(messageReceived);
            }
        } catch (JMSException e) {
            logger.error("Exception: " + e);
            throw new RuntimeException(e);
        }
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String bookFilename = textMessage.getText();
                logger.info("Message received: " + bookFilename);
                indexer.index(bookFilename);
            }
        } catch (JMSException e) {
            logger.error("Exception: " + e);
            throw new RuntimeException(e);
        }
    }
}
