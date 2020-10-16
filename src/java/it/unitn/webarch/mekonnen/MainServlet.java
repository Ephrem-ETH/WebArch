package it.unitn.webarch.mekonnen;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ephrem
 */
public class MainServlet extends HttpServlet {

  
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session =  request.getSession(false);
         String userName = (String) session.getAttribute("user");
//        Cookie [] cookies = request.getCookies();
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if(cookie.getName().equals("user"))
//                    userName= cookie.getValue();
//            }
//        }
//        if(userName == null)
//            response.sendRedirect("login.html");
            /* TODO output your page here. You may use following sample code. */
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Main Page</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Hello " + userName + ", Login successful.</h1>");
            out.println("<h1>Current time" + sdf.format(date) + "</h1>");
            out.println("<br/>");
            out.println("<form action='LogoutServlet' method='post'>");
            out.println("<input type='submit' name='action' value='Logout'>"+ "&nbsp");
            out.println("<input type='submit' name='action' value='ChangePassword'>");
            out.println("</body>");
            out.println("</html>");
        
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
