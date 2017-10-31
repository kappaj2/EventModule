package za.co.ajk.incidentman.messaging.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import za.co.ajk.incidentman.messaging.CustomSource;
import za.co.ajk.incidentman.messaging.OutboundMessageHandler;

@EnableBinding(CustomSource.class)
@Service
public class OutboundMessageHandlerImpl implements OutboundMessageHandler {
    
    @Autowired
    @Output("outputchannel1")
    MessageChannel channel1;
    
    @Autowired
    @Output("outputchannel2")
    MessageChannel channel2;
    
    @Override
    public void sendMessageChannel1(){
        channel1.send(MessageBuilder.withPayload("TestChannel1").build());
    }
    
    @Override
    public void sendMessageChannel2(){
        channel2.send(MessageBuilder.withPayload("TestChannel2").build());
    }
    
}


//
//
//    OutboundMessage obm = new OutboundMessage();
//        obm.setEventType("EventType : "+eventType);
//            obm.setPayload("This is the big payload");
//            obm.setSourceDestination("SystemGateway");
//            obm.setTargetDestination("EventModule");
//
//            output.send(MessageBuilder.withPayload(obm).build());
