package za.co.ajk.incidentman.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerChannels {
    
    String EVENT_INPUT = "event-input";
    String BROADCASTS = "broadcasts";
    
    @Input(EVENT_INPUT)
    SubscribableChannel eventInputChannel();
    
    @Input(BROADCASTS)
    SubscribableChannel broadcastChannel();

}
