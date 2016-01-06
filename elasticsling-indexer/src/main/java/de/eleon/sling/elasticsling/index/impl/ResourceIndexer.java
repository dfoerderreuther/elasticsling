package de.eleon.sling.elasticsling.index.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.*;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import org.apache.sling.api.SlingConstants;

import java.util.Map;

@Component(immediate=true, metatype=false)
@Service(EventHandler.class)
@Property(name = EventConstants.EVENT_TOPIC, value = { SlingConstants.TOPIC_RESOURCE_ADDED, SlingConstants.TOPIC_RESOURCE_CHANGED })
public class ResourceIndexer implements EventHandler {

    @Reference
    SearchService searchService;

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("handle event = [" + event + "]");
        String path = get(event, "path", "/index/me");
        String resourceType = get(event, "resourceType", "sling:Folder");
        searchService.write("/index/me", "my index words");
    }

    private <T> T get(final Event event, String name, T def) {
        if (!event.containsProperty(name) || event.getProperty(name) == null) return def;
        return (T) event.getProperty(name);
    }

}
