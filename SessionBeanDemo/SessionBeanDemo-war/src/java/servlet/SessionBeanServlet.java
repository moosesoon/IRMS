/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.StatefulBean;
import session.SingletonBean;
import session.StatelessBean;

/**
 *
 * @author Stanley
 */
public class SessionBeanServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            StatelessBean statelessBean = getStatelessBean(request);
            StatefulBean statefulBean = getStatefulBean(request);
            SingletonBean singletonBean = getSingletonBean(request);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SessionBeanServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SessionBeanServlet at " + request.getContextPath() + "</h1>");
            
            out.println("<b>Session ID</b>: " + request.getSession().getId() + "<br/><br/>");
            out.println("<b>Stateless Bean Secret Number</b>: " + statelessBean.getSecretNumber() + "<br/><br/>");
            out.println("<b>Stateful Bean Secret Number</b>: " + statefulBean.getSecretNumber() + "<br/><br/>");
            out.println("<b>Singleton Bean Secret Number</b>: " + singletonBean.getSecretNumber() + "<br/><br/>");
        
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
    
    private StatelessBean getStatelessBean(HttpServletRequest request){
        HttpSession httpSession = request.getSession(true);
        StatelessBean statelessBean = (StatelessBean) httpSession.getAttribute("statelessBean");
        
        if(statelessBean == null){
            try{
                InitialContext ic = new InitialContext();
                statelessBean = (StatelessBean) ic.lookup("java:global/SessionBeanDemo/SessionBeanDemo-ejb/StatelessBean!session.StatelessBean");
                httpSession.setAttribute("statelessBean", statelessBean);
            } catch(NamingException ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                throw new RuntimeException(ex);
            }
        }
        
        return statelessBean;
    }
    
    private StatefulBean getStatefulBean(HttpServletRequest request){
        HttpSession httpSession = request.getSession(true);
        StatefulBean statefulBean = (StatefulBean) httpSession.getAttribute("statefulBean");
        
        if(statefulBean == null){
            try{
                InitialContext ic = new InitialContext();
                statefulBean = (StatefulBean) ic.lookup("java:global/SessionBeanDemo/SessionBeanDemo-ejb/StatefulBean!session.StatefulBean");
                httpSession.setAttribute("statefulBean", statefulBean);
            } catch(NamingException ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                throw new RuntimeException(ex);
            }
        }
        
        return statefulBean;
    }
    
    private SingletonBean getSingletonBean(HttpServletRequest request){
        HttpSession httpSession = request.getSession(true);
        SingletonBean singletonBean = (SingletonBean) httpSession.getAttribute("singletonBean");
        
        if(singletonBean == null){
            try{
                InitialContext ic = new InitialContext();
                singletonBean = (SingletonBean) ic.lookup("java:global/SessionBeanDemo/SessionBeanDemo-ejb/SingletonBean!session.SingletonBean");
                httpSession.setAttribute("singletonBean", singletonBean);
            } catch(NamingException ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ex);
                throw new RuntimeException(ex);
            }
        }
        
        return singletonBean;
    }
}
