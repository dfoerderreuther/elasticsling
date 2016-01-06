package de.eleon.sling.elasticsling.index.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.*;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import org.apache.sling.api.SlingConstants;

import java.util.Map;

@Component(immediate=true, metatype=false)
@Service(EventHandler.class)
@Property(name = EventConstants.EVENT_TOPIC, value = { SlingConstants.TOPIC_RESOURCE_ADDED })
public class ResourceIndexer implements EventHandler {

    @Reference
    SearchService searchService;

    @Reference
    ResourceResolverFactory resolverFactory;
    ResourceResolver resourceResolver;

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
        try {
            resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Deactivate
    public void deactivate() {
        resourceResolver.close();
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println("handle event = [" + event + "]");
        String path = get(event, "path", "/index/me");
        String resourceType = get(event, "resourceType", "sling:Folder");
        if (resourceType.equalsIgnoreCase("foo/bar")) {
            index(path);
        }
    }

    private void index(String path) {
        Resource res = resourceResolver.getResource(path);
        final ValueMap valueMap = res.getValueMap();
        String text = valueMap.containsKey("text") ? (String)valueMap.get("text") : "";
        searchService.write(path, text);
    }

    private <T> T get(final Event event, String name, T def) {
        if (!event.containsProperty(name) || event.getProperty(name) == null) return def;
        return (T) event.getProperty(name);
    }

}
