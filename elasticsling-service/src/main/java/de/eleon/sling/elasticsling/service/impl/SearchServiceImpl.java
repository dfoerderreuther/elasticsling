package de.eleon.sling.elasticsling.service.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Component
@Service(SearchService.class)
public class SearchServiceImpl implements SearchService {

    @Override
    public String test() {
        return "service avaliable";
    }
}
