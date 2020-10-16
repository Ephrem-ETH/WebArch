package it.unitn.webarch.mekonnen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Ephrem
 */
public class LoginServlet extends HttpServlet {

    // Define datasource, the resource is defined in context.xml
    @Resource(name = "jdbc/delivery2db")
    private DataSource dataSource;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Initialize connection objects
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Get request parameters for username and password
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        PrintWriter out = response.getWriter();
        boolean isUserFound = false;
        try {
            conn = dataSource.getConnection();
            String query = " SELECT * FROM tbl_users WHERE username=? and password =?";
            // Create a SQL statement
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pwd);
            // Execute SQL query
            rs = ps.executeQuery();

            // Process the result set
            isUserFound = rs.next();

            if (isUserFound) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30*60);
//                Cookie loginCookie = new Cookie("user", user);
//                loginCookie.setMaxAge(30 * 60);
//                response.addCookie(loginCookie);
                RequestDispatcher rd = request.getRequestDispatcher("MainServlet");
                rd.forward(request, response);
               
            } else {
                out.println("<font color='red'><b>Sorry, Username or password error </b></font>");
                RequestDispatcher rd = request.getRequestDispatcher("login.html");
                rd.include(request, response);
            }

        } catch (Exception e) {
           out.println("Error:" +e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
