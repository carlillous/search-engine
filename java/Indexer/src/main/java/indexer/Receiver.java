package indexer;

import datalake.DataLake;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Receiver {
    private static final String url = "tcp://artemis:61616";
    public static final String username = "artemis";
    public static final String password = "artemis";
    private static final String queueName = "MESSAGE_QUEUE";

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final Indexer indexer;
    private final DataLake dataLake;

    public Receiver(DataLake dataLake) {
        this.dataLake = dataLake;
        this.indexer = new Indexer(dataLake);
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
                indexer.indexOne(dataLake.getDataLakePath()+bookFilename);
            }
        } catch (JMSException e) {
            logger.error("Exception: " + e);
            throw new RuntimeException(e);
        }
    }
}
