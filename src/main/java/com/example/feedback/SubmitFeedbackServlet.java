package com.example.feedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/submitFeedback")
public class SubmitFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String lectureIdStr = request.getParameter("forelesningId");
        String studentId = request.getParameter("studentId");
        String farge = request.getParameter("farge");

        response.setContentType("text/html;charset=UTF-8");

        if (lectureIdStr == null || studentId == null || farge == null ||
                lectureIdStr.isBlank() || studentId.isBlank() || farge.isBlank()) {
            response.getWriter().println("<h3>Alle felt må fylles ut.</h3>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
            return;
        }

        String sql = "INSERT INTO tilbakemelding (forelesning_id, student_id, farge) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int lectureId = Integer.parseInt(lectureIdStr);

            ps.setInt(1, lectureId);
            ps.setString(2, studentId);
            ps.setString(3, farge);

            ps.executeUpdate();

            response.getWriter().println("<h3>Tilbakemelding lagret.</h3>");
            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");

        } catch (Exception e) {
            String message = e.getMessage() == null ? "" : e.getMessage().toLowerCase();

            if (message.contains("unique") || message.contains("duplicate")) {
                response.getWriter().println("<h3>Denne studenten har allerede gitt tilbakemelding på denne forelesningen.</h3>");
            } else {
                response.getWriter().println("<h3>Feil ved lagring av tilbakemelding:</h3>");
                response.getWriter().println("<p>" + e.getMessage() + "</p>");
            }

            response.getWriter().println("<a href='index.jsp'>Tilbake</a>");
        }
    }
}