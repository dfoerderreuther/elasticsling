package de.eleon.sling.elasticsling.client.impl;

import de.eleon.sling.elasticsling.service.SearchService;
import de.eleon.sling.elasticsling.service.impl.SearchServiceImpl;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@SlingServlet(paths = {"/search"})
public class SlingSearchServlet extends SlingSafeMethodsServlet {

    @Reference
    SearchService searchService;

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        final RequestParameter search = request.getRequestParameter("search");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Search</h1>");

        if (search == null) {
            out.println("<a href=\"/search?search=sling\">Search for \"sling\"</a>");
        } else {
            out.println("<h2>Results for \"" + search.getString() + "\"</h2>");
            out.println("<ul>");
            for (String result : searchService.find(search.getString())) {
                out.println("<li>");
                out.println(result);
                out.println("</li>");
            }
            out.println("</ul>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
