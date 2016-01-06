package de.eleon.sling.elasticsling.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.*;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component(metatype = true, label = "elasticsearch service", description = "elasticsearch service")
@Service(SearchService.class)
public class SearchServiceImpl implements SearchService {

    private String host;

    private int port;

    private String indexName = "sling";

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
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            if (!indexExist()) {
                createIndex();
                System.out.println("index " + indexName + " created");
            } else {
                System.out.println("index " + indexName + " already exists");
            }
            System.out.println("connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Deactivate
    protected void deactivate() {
        this.client.close();
        System.out.println("closed");
    }

    @Override
    public String test() {
        return "service avaliable " + host + " - " + port;
    }

    @Override
    public void write(String path, String text) {
        System.out.println("Write to index: path = [" + path + "], text = [" + text + "]");
        try {
            final IndexResponse indexResponse = client.prepareIndex(indexName, "doc")
                    .setSource(ImmutableMap.<String, String>builder()
                            .put("path", path)
                            .put("text", text)
                            .build())
                    .execute().get();
            System.out.print("created element with id: " + indexResponse.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> find(String search) {
        return ImmutableList.<String>builder()
                .add("/find/path/a")
                .add("/find/path/b")
                .build();
    }

    private <T> T get(final Map<String, Object> props, Object name, T def) {
        if (!props.containsKey(name) || props.get(name) == null) return def;
        return (T) props.get(name);
    }


    private boolean indexExist() {
        return client.admin().indices()
                .exists(new IndicesExistsRequest(indexName))
                .actionGet()
                .isExists();
    }

    private boolean createIndex() {
        return client.admin().indices()
                .create(new CreateIndexRequest(indexName))
                .actionGet()
                .isAcknowledged();
    }



}
