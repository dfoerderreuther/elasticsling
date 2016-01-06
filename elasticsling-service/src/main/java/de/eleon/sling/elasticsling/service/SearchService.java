package de.eleon.sling.elasticsling.service;

import java.util.List;

/**
 * Created by dominik on 06.01.16.
 */
public interface SearchService {

    String test();

    void write(String path, String text);

    List<String> find(String search);

}
