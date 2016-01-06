package de.eleon.sling.elasticsling.index.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import org.apache.sling.api.SlingConstants;

@Component(immediate=true, metatype=false)
@Service(EventHandler.class)
@Property(name = EventConstants.EVENT_TOPIC, value = { SlingConstants.TOPIC_RESOURCE_ADDED, SlingConstants.TOPIC_RESOURCE_CHANGED })
public class ResourceIndexer implements EventHandler {

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("handle event = [" + event + "]");
    }

}
