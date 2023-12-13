package indexer;


import datalake.filesystem.DataLake;
import javax.jms.JMSException;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Receiver implements MessageListener {
    private static final String url = "tcp://artemis:61616";
    public static final String username = "artemis";
    public static final String password = "artemis";
    private static final String queueName = "MESSAGE_QUEUE";

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    public Receiver() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url, username, password);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            logger.info("Connection to ActiveMQ established.");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);


            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(this);

            // TODO: Receive messages
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {

                TextMessage textMessage = (TextMessage) message;
                String bookFilename = textMessage.getText();
                logger.info("Received: " + bookFilename);

                DataLake dataLake = new DataLake();
                new Indexer(dataLake).indexOne(dataLake.getDataLakePath()+bookFilename);

            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Receiver();
    }
}
