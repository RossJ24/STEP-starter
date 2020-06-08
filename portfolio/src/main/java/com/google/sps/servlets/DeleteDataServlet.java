package com.google.sps.servlets;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;

/**
 * Servlet that handles the delettion of comments
*/
@WebServlet("/delete-data")
public class DeleteDataServlet extends HttpServlet{

    public static final String COMMENT_PROPERTY = "comment";
    public static final String TIMESTAMP_PROPERTY = "timestamp";
    public static final String COMMENT_ENTITY = "comment";

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        Query query = new Query(COMMENT_ENTITY);
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()){
            try {
                datastore.delete(entity.getKey());
            } 
            catch(DataStoreFailureException e) {
                System.out.println(e.toString());
            }
        } 
    }
}
