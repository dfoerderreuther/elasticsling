package de.eleon.sling.elasticsling.service.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import java.util.Map;

@Component(metatype = true, label = "elasticsearch service", description = "elasticsearch service")
@Service(SearchService.class)
public class SearchServiceImpl implements SearchService {

    private String host;

    private int port;

    @Property(value = "localhost", label = "Host", description = "Elasticsearch Hostname")
    private static final String HOST_NAME = "host.name";

    @Property(value = "9200", label = "Port", description = "Elasticsearch Port")
    private static final String HOST_PORT = "host.port";


    @Activate
    protected void activate(final Map<String, Object> props) {
        System.out.println(this.getClass().getName() + " STARTED");
        this.host = get(props, HOST_NAME, "localhost");
        port = Integer.valueOf(get(props, HOST_PORT, "9200"));
    }

    @Override
    public String test() {
        return "service avaliable " + host + " - " + port;
    }

    private <T> T get(final Map<String, Object> props, Object name, T def) {
        if (!props.containsKey(name) || props.get(name) == null) return def;
        return (T) props.get(name);
    }

}
