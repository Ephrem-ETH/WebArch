/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webarch.mekonnen;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Ephrem
 */
public class ChangePasswordServlet extends HttpServlet {

    // Define datasource, get connection with the data base
    @Resource(name = "jdbc/delivery2db")
    private DataSource dataSource;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String newPassword = request.getParameter("newpwd");
        String confirPassword = request.getParameter("confirmpwd");
        HttpSession session = request.getSession(false);
        String user = (String) session.getAttribute("user");
        //Cookie[] cookies = request.getCookies();
        boolean isUpdated;
        
        if (newPassword == null || newPassword.isEmpty() || confirPassword == null || confirPassword.isEmpty()) {
            out.println("<font color='red'><b>Sorry, The input field can't be empty </b></font>");
            RequestDispatcher rd = request.getRequestDispatcher("changePassword.html");
            rd.include(request, response);

        } else {

            if (newPassword.equals(confirPassword)) {
                try {
                    Connection conn = dataSource.getConnection();
                    isUpdated = updatePassword(conn, newPassword, user);

                    if (isUpdated) {
                        out.println("<font color='green'><b>Password is successfully updated!</b></font>");

                        //sleep(3000);
                        // response.sendRedirect("login.html");
                    } else {
                        out.println("<font color='red'><b>The new password was not updated  </b></font>");
                        RequestDispatcher rd = request.getRequestDispatcher("changePassword.html");
                        rd.include(request, response);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else {
                out.println("<font color='red'><b>Sorry, Password does not match! </b></font>");
                RequestDispatcher rd = request.getRequestDispatcher("changePassword.html");
                rd.include(request, response);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
// A function in charge of updating the password of the user.

    public boolean updatePassword(Connection conn, String password, String user) throws SQLException {
        String query = "UPDATE tbl_users set Password=? WHERE UserName =?";
        if (conn != null && password != null && user != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, password);
                ps.setString(2, user);
                int row = ps.executeUpdate();
                if (row != 0) {
                    return true;
                }
            } catch (SQLException ex) {

            }

        }
        return false;
    }

}
