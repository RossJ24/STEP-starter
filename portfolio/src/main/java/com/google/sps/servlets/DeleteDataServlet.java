package com.google.sps.servlets;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;

/** Servlet that handles the deletion of comments through a post request */
@WebServlet("/delete-data")
public class DeleteDataServlet extends HttpServlet{
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        Query commentsQuery = new Query(ServletUtil.COMMENT_ENTITY);
        PreparedQuery commentResults = ServletUtil.DATASTORE.prepare(commentsQuery);
        for (Entity commentEntity : commentResults.asIterable()){
            try {
                ServletUtil.DATASTORE.delete(commentEntity.getKey());
            } 
            catch(DatastoreFailureException e) {
                System.out.println(e.toString());
            }
        } 
    }
}
