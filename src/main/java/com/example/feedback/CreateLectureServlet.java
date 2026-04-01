package com.example.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/createLecture")
public class CreateLectureServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String navn = request.getParameter("navn");
        String tidspunkt = request.getParameter("tidspunkt");
        String sted = request.getParameter("sted");

        response.setContentType("text/html;charset=UTF-8");

        if (navn == null || navn.trim().isEmpty()) {
            response.getWriter().println("<h3>Forelesningsnavn må fylles ut.</h3>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
            return;
        }

        String sql = "INSERT INTO forelesning (navn, tidspunkt, sted) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, navn);

            if (tidspunkt == null || tidspunkt.isBlank()) {
                ps.setTimestamp(2, null);
            } else {
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(tidspunkt.replace("T", " ") + ":00"));
            }

            ps.setString(3, sted);
            ps.executeUpdate();

            response.getWriter().println("<h3>Forelesning lagret.</h3>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");

        } catch (Exception e) {
            response.getWriter().println("<h3>Feil ved lagring av forelesning:</h3>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
        }
    }
}