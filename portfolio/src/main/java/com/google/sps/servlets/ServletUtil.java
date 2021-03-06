package com.google.sps.servlets;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public final class ServletUtil{
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String COMMENT_PROPERTY = "comment";
    public static final String TIMESTAMP_PROPERTY = "timestamp";
    public static final String COMMENT_ENTITY = "comment";
    public static final String COMMENT_PARAMETER = "comment";
    public static final String HOME_HTML = "/index.html";
    public static final String ERROR_HTML = "/error.html";
    public static final String COMMENTS_HTML = "/comments.html";
    public static final String EMAIL_PROPERTY = "email";
    public static final DatastoreService DATASTORE = DatastoreServiceFactory.getDatastoreService();
    public static final Gson PARSER = new Gson();
    public static final UserService USER_SERVICE = UserServiceFactory.getUserService();
    public static final String LIMIT_PARAMETER = "limit";
}
