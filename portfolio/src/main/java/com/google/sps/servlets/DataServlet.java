
// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

/** Servlet that handles comments */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Gson parser = new Gson();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Query query = new Query("comment").addSort("timestamp",SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
        ArrayList comments = new ArrayList();
        for (Entity entity : results.asIterable()){
            comments.add(entity.getProperty("comment"));
        }
        res.setContentType("application/json;");
        res.getWriter().println(parser.toJson(comments));
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String jsonstring = req.getParameter("comment");
        Entity commentEntity = new Entity("comment");
        commentEntity.setProperty("comment",jsonstring);
        long timestamp = System.currentTimeMillis();
        commentEntity.setProperty("timestamp", timestamp);
        try{
            datastore.put(commentEntity);
            res.sendRedirect("/index.html");
        } 
        catch(Exception e){
            res.sendRedirect("/error.html");
        }
    }
}
