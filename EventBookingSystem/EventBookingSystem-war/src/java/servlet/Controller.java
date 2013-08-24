/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manager.EventManager;
import manager.SystemUserManager;
import session.stateless.EventSessionLocal;
import session.stateless.SystemUserSessionLocal;
import util.exception.VenueConflictException;

/**
 *
 * @author Stanley
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller", "/Controller?*"})
public class Controller extends HttpServlet {
    @EJB
    EventSessionLocal eventSessionLocal;
    @EJB
    SystemUserSessionLocal systemUserSessionLocal;
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
        try {
            SystemUserManager systemUserManager = new SystemUserManager(systemUserSessionLocal);
            EventManager eventManager = new EventManager(eventSessionLocal);
            
            String action = request.getParameter("action");
            
            if(action==null){
                action = "index";
            }
            if(action.equals("index")){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else if(action.equals("doLogin")){
                Long systemUserId = systemUserManager.doLogin(request.getParameter("userName"),
                                                              request.getParameter("password"));
                if(systemUserId == null){
                    request.setAttribute("invalidLogin","true");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else{
                    request.getSession().setAttribute("systemUserId", systemUserId);
                    request.getRequestDispatcher("/Controller?action=" + 
                                                 request.getSession().getAttribute("lastAction")
                                                ).forward(request, response);
                }
            } else{ //Not index page
                if(request.getSession().getAttribute("systemUserId") == null){
                    request.setAttribute("invalidLogin", "false");
                    request.getSession().setAttribute("lastAction", action);
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                } else{
                    if(action.equals("doLogout")){
                        request.getSession().setAttribute("systemUserId", null);
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    } else if(action.equals("viewAllEvents")){
                        request.setAttribute("events", eventManager.getAllEvents());
                        request.getRequestDispatcher("/viewAllEvents.jsp").forward(request, response);
                    } else if(action.equals("viewMyEvents")){
                        request.setAttribute("events", eventManager.getMyEvents((Long)request.getSession().getAttribute("systemUserId")));
                        request.getRequestDispatcher("/viewMyEvents.jsp").forward(request, response);
                    } else if(action.equals("addNewEvent")){
                        request.setAttribute("newEventId", new Long(0));
                        request.setAttribute("venues", eventManager.getAllVenues());
                        request.getRequestDispatcher("/addNewEvent.jsp").forward(request, response);
                    } else if(action.equals("saveNewEvent")){
                        try{
                            request.setAttribute("newEventId", eventManager.addNewEvent(request));
                            request.setAttribute("status", "success");
                            System.out.println("***Eventid: " + request.getAttribute("newEventId").toString());
                        } catch(VenueConflictException vce){
                            request.setAttribute("newEventId", new Long(0));
                            request.setAttribute("status", "error");
                            request.setAttribute("errorMessage", "Venue Conflict");
                        }
                        request.setAttribute("venues", eventManager.getAllVenues());
                        request.getRequestDispatcher("/addNewEvent.jsp").forward(request, response);
                    } else if(action.equals("addNewEventAjax")){
                        request.setAttribute("venues", eventManager.getAllVenues());
                        request.getRequestDispatcher("/addNewEventAjax.jsp").forward(request, response);
                    } else if(action.equals("saveNewEventAjax")){
                        response.setContentType("text/plain");
                        response.setHeader("Cache-Control", "no-cache");
                        PrintWriter out = response.getWriter();
                        try{
                            Long newEventId = eventManager.addNewEvent(request);
                            out.print(newEventId.toString());
                        } catch(VenueConflictException vce){
                            out.print("conflict");
                        }
                        out.close();
                    }
                }
            }
            
        } catch(Exception ex){
            ex.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } finally {         
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
}
