package de.eleon.sling.elasticsling.service.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Component(metatype = true, label = "elasticsearch service", description = "elasticsearch service")
@Service(SearchService.class)
public class SearchServiceImpl implements SearchService {

    private String host;

    private int port;

    @Property(value = "localhost", label = "Host", description = "Elasticsearch Hostname")
    private static final String HOST_NAME = "host.name";

    @Property(value = "9300", label = "Port", description = "Elasticsearch Port")
    private static final String HOST_PORT = "host.port";

    private Client client;

    @Activate
    protected void activate(final Map<String, Object> props) {
        System.out.println(this.getClass().getName() + " STARTED!");
        this.host = get(props, HOST_NAME, "localhost");
        this.port = Integer.valueOf(get(props, HOST_PORT, "9300"));
        try {
            System.out.println("connect");
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            System.out.println("connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        /*JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();*/
    }


    @Deactivate
    protected void deatcivate() {
        //this.client.close();
        System.out.println("closed");
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
