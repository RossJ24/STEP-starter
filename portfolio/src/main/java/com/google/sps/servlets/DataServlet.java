
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
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/** Servlet that handles comments through post and TODO: get requests */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String COMMENT_PROPERTY = "comment";
    public static final String TIMESTAMP_PROPERTY = "timestamp";
    public static final String COMMENT_ENTITY = "comment";
    private final ArrayList<String> Greetings = new ArrayList();
    private final ArrayList<String> commentRepo = new ArrayList<String>();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
  }
  @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Entity commentEntity = new Entity(COMMENT_ENTITY);

        String jsonString = req.getParameter("comment");
        commentEntity.setProperty(COMMENT_PROPERTY, jsonstring);

        long currentTimeMillis = System.currentTimeMillis();
        commentEntity.setProperty(TIMESTAMP_PROPERTY, currentTimeMillis);

        datastore.put(commentEntity);
        commentRepo.add(jsonstring);
        res.sendRedirect("/index.html");

        //TODO: Implement a backend that posts and 
        //fetches comments for the user
    }
}
