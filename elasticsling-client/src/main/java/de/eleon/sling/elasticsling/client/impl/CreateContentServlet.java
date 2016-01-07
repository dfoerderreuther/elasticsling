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
import java.io.PrintWriter;

@SlingServlet(paths = {"/create"})
public class CreateContentServlet extends SlingSafeMethodsServlet {

    @Activate
    public void activate() {
        System.out.println(this.getClass().getName() + " STARTED");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Create Content</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<a href=\"/create?sling:authRequestLogin=1\">auth</a>");
        out.println("<form method=\"POST\" action=\"/content/\" enctype=\"multipart/form-data\">\n" +
                "resourceType: <input type=\"text\" name=\"sling:resourceType\" value=\"foo/bar\" /><br />\n" +
                "title: <input type=\"text\" name=\"title\" value=\"\" /><br />\n" +
                "text: <textarea  name=\"text\" rows=\"4\" cols=\"30\" /></textarea><br />\n" +
                "<input type=\"submit\" value=\"submit\" />\n" +
                "</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
