package za.co.ajk.incidentman.messaging;

import org.springframework.stereotype.Component;

@Component
public class InboundMessage {
    
    private String eventType;
    private String sourceDestination;
    private String targetDestination;
    private String payload;
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public String getSourceDestination() {
        return sourceDestination;
    }
    
    public void setSourceDestination(String sourceDestination) {
        this.sourceDestination = sourceDestination;
    }
    
    public String getTargetDestination() {
        return targetDestination;
    }
    
    public void setTargetDestination(String targetDestination) {
        this.targetDestination = targetDestination;
    }
    
    public String getPayload() {
        return payload;
    }
    
    public void setPayload(String payload) {
        this.payload = payload;
    }
    
    @Override
    public String toString() {
        return "InboundMessage{" +
            "eventType='" + eventType + '\'' +
            ", sourceDestination='" + sourceDestination + '\'' +
            ", targetDestination='" + targetDestination + '\'' +
            ", payload='" + payload + '\'' +
            '}';
    }
}

