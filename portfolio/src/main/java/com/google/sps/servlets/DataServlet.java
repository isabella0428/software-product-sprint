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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import org.json.simple.JSONObject;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comment")
public class DataServlet extends HttpServlet {
  ArrayList<String> allComments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (allComments.size() == 0) {
        allComments.add("Welcome to my mailbox :)");
        allComments.add("Hi, I am Yi Lyu. My foreign name is Isabella :D");
        allComments.add("I am looking forward to know u.");
        allComments.add("You can contact me with the info in the left side bar!");
    }
    
    String json = convertToJson();
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (allComments.size() == 0) {
        allComments.add("Welcome to my mailbox :)");
        allComments.add("Hi, I am Yi Lyu. My foreign name is Isabella :D");
        allComments.add("I am looking forward to know u.");
        allComments.add("You can contact me with the info in the left side bar!");
    }
    
    String comment = request.getParameter("newComment");
    System.out.println(allComments.size());
    allComments.add(comment);
    response.sendRedirect("/success.html");
  }

  private String convertToJson() {
      JSONObject jsonObj = new JSONObject();

      for (int i = 0; i < allComments.size(); ++i) {
          jsonObj.put("comment" + Integer.toString(i), allComments.get(i));
      }
      return jsonObj.toString();
  }
}
