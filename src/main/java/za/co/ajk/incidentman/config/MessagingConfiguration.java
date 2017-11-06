package za.co.ajk.incidentman.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

import za.co.ajk.incidentman.messaging.ConsumerChannels;
import za.co.ajk.incidentman.messaging.ProducerChannels;

/**
 * Configures Spring Cloud Stream support.
 *
 * See http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/
 * for more information.
 */
@EnableBinding({ConsumerChannels.class, ProducerChannels.class})
public class MessagingConfiguration {

}
