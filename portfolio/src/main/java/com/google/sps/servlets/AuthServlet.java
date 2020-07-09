package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        if(!ServletUtil.USER_SERVICE.isUserLoggedIn()){
            String urlToRedirectToAfterUserLogsIn = ServletUtil.COMMENTS_HTML;
            String loginUrl = ServletUtil.USER_SERVICE.createLoginURL(urlToRedirectToAfterUserLogsIn);
            res.getWriter().println("<center><a href=\"" + loginUrl + "\"><button><span style=\"color:white;\">Login</span></button/></a></center>");
        }
        else{
            String userEmail = ServletUtil.USER_SERVICE.getCurrentUser().getEmail();
            String urlToRedirectToAfterUserLogsOut = ServletUtil.HOME_HTML;
            String logoutUrl = ServletUtil.USER_SERVICE.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            res.setContentType(ServletUtil.JSON_CONTENT_TYPE);
            res.getWriter().println(ServletUtil.PARSER.toJson(userEmail));
        }
    }
}