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

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Stack;

import org.json.simple.JSONObject;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comment")
public class CommentDataServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = convertAllCommentsToJson();
    response.setContentType("application/json");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = request.getParameter("newComment");

    UserService userService = UserServiceFactory.getUserService();
    String text = request.getParameter("text");
    String email = userService.getCurrentUser().getEmail();

    // Write comment to comment.txt file
    FileWriter fw = new FileWriter("/Users/isabella/Desktop/SPS/software-product-sprint/portfolio/src/main/java/com/google/sps/servlets/comment.txt", true);
    fw.write(comment);
    fw.write(" ");
    fw.write(email);
    fw.write("\n");
    fw.close();

    response.sendRedirect("/success.html");
  }

  private String convertAllCommentsToJson() {
      JSONObject jsonObj = new JSONObject();
      Stack<String> allComments = new Stack<String>();

      // Read All Comment from comment.txt
      try {
        BufferedReader br = new BufferedReader(new FileReader("/Users/isabella/Desktop/SPS/software-product-sprint/portfolio/src/main/java/com/google/sps/servlets/comment.txt"));
        String line = "";
        while((line = br.readLine()) != null) {
            allComments.push(line);
        }
        br.close();
      } catch(FileNotFoundException e) {
          e.printStackTrace();
      } catch(IOException e) {
          e.printStackTrace();
    }

      int i = 0;
      while(!allComments.empty()) {
        jsonObj.put("comment" + Integer.toString(i), allComments.pop());
        ++i;
      }
      return jsonObj.toString();
  }
}
