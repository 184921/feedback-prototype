package com.example.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/viewResults")
public class ViewResultsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lectureIdStr = request.getParameter("forelesningId");
        response.setContentType("text/html;charset=UTF-8");

        if (lectureIdStr == null || lectureIdStr.isBlank()) {
            response.getWriter().println("<h3>Forelesning ID må fylles ut.</h3>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
            return;
        }

        String sql = """
                SELECT farge, COUNT(*) AS antall
                FROM tilbakemelding
                WHERE forelesning_id = ?
                GROUP BY farge
                ORDER BY farge
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(lectureIdStr));

            try (ResultSet rs = ps.executeQuery()) {
                response.getWriter().println("<h2>Resultater for forelesning ID " + lectureIdStr + "</h2>");
                response.getWriter().println("<ul>");

                while (rs.next()) {
                    String farge = rs.getString("farge");
                    int antall = rs.getInt("antall");
                    response.getWriter().println("<li>" + farge + ": " + antall + "</li>");
                }

                response.getWriter().println("</ul>");
                response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
            }

        } catch (Exception e) {
            response.getWriter().println("<h3>Feil ved henting av resultater:</h3>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
        }
    }
}