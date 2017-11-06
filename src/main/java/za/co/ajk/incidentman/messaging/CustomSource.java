package za.co.ajk.incidentman.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomSource {
    
    @Output("outputchannel1")
    MessageChannel output1();
    
    @Output("outputchannel2")
    MessageChannel output2();
}
