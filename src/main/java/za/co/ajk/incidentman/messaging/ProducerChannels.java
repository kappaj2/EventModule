package za.co.ajk.incidentman.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProducerChannels {

    String PRODUCER1 = "messageChannel1";
    
    @Output
    MessageChannel messageChannel();
}
