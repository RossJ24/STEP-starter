package com.google.sps.servlets;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public final class ServletUtil{
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String COMMENT_PROPERTY = "comment";
    public static final String TIMESTAMP_PROPERTY = "timestamp";
    public static final String COMMENT_ENTITY = "comment";
    public static final String COMMENT_PARAMETER = "comment";
    public static final String HOME_HTML = "/index.html";
    public static final String ERROR_HTML = "/error.html";
    public static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public static final Gson parser = new Gson();
}