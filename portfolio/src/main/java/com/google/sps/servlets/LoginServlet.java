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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.Console;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.json.simple.JSONObject;

/** Servlet that returns some example content.*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    JSONObject jsonObj = new JSONObject();
    response.setContentType("application/json");

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
        String urlToRedirectToAfterUserLogsIn = "/loggedIn.html";
        String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
        jsonObj.put("loginUrl", loginUrl);
        response.getWriter().println(jsonObj.toString());
    } else {
        String urlToRedirectToAfterUserLogsIn = "/loggedOut.html";
        String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsIn);
        jsonObj.put("logoutUrl", logoutUrl);
        response.getWriter().println(jsonObj.toString());
    }
  }
}
