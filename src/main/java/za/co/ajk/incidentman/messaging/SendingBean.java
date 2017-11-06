package za.co.ajk.incidentman.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class SendingBean {
    
    private MessageChannel output;
    
    @Autowired
    public SendingBean(@Qualifier("outputchannel1") MessageChannel output) {
        this.output = output;
    }
    
    public void sendMessage(String name) {
        this.output.send(MessageBuilder.withPayload(name).build());
    }
}
