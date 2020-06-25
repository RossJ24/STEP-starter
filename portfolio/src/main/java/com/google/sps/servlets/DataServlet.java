
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
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.time.Instant;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.QueryResultList;


/** Servlet that handles comments through post and TODO(rossjohnson): get requests */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List comments = new ArrayList();
        int limit = Integer.parseInt(req.getParameter(ServletUtil.LIMIT_PARAMETER));
        FetchOptions options = FetchOptions.Builder.withLimit(limit);
        Query commentsQuery = new Query(ServletUtil.COMMENT_ENTITY).addSort(ServletUtil.TIMESTAMP_PROPERTY, SortDirection.DESCENDING);
        List<Entity> commentResults = ServletUtil.DATASTORE.prepare(commentsQuery).asList(options);
        //Integer to keep track of how many comments have been added to the arraylist that will be returned
        for (Entity commentEntity : commentResults) {
            //Add's the entity's comment string to the arraylist that will be returned
            comments.add(commentEntity.getProperty(ServletUtil.COMMENT_PROPERTY));
        }
        res.setContentType(ServletUtil.JSON_CONTENT_TYPE);

        res.getWriter().println(ServletUtil.PARSER.toJson(comments));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        String jsonstring = req.getParameter(ServletUtil.COMMENT_PARAMETER);
        Entity commentEntity = new Entity(ServletUtil.COMMENT_ENTITY);
        commentEntity.setProperty(ServletUtil.COMMENT_PROPERTY, jsonstring);

        long currentTimeMillis = System.currentTimeMillis();
        commentEntity.setProperty(ServletUtil.TIMESTAMP_PROPERTY, currentTimeMillis);

        commentEntity.setProperty(ServletUtil.EMAIL_PROPERTY, ServletUtil.USER_SERVICE.getCurrentUser().getEmail());
        try{
            ServletUtil.DATASTORE.put(commentEntity);
            res.sendRedirect(ServletUtil.HOME_HTML);
        } 
        catch(DatastoreFailureException e){
            System.out.println(e.toString());
            res.sendRedirect(ServletUtil.ERROR_HTML);
        }
    }
}
