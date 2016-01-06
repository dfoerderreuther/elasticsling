package de.eleon.sling.elasticsling.client.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@SlingServlet(paths = {"/esearch"})
public class SlingSearchServlet extends SlingSafeMethodsServlet {

    @Reference
    SearchService searchService;

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("HHHello world!\n");
        response.getWriter().write(searchService.test());
    }
}
