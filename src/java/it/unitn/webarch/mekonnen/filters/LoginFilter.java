/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.webarch.mekonnen.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ephrem
 */
public class LoginFilter implements Filter {

   
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        // this.filterConfig = filterConfig;
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        // Pass the request along the filter chain if session exists
        if (session == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet"))) {

            this.context.log("Unauthorized access request");
            res.sendRedirect("login.html");
        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

}
