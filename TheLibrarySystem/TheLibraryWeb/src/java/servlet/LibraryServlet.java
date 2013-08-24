/*
 * Author:  Stanley Soon Jin Shen
 * Subject: Project Assignmentt IS2104 EJB
 */

package servlet;

import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import tlb.MemberManagerRemote;
import tlb.BookManagerRemote;
import tlb.ReservationManagerRemote;
import appHelper.MemberState;
import appHelper.TitleState;
import appHelper.BookState;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LibraryServlet extends HttpServlet {
   @EJB
   private MemberManagerRemote mmr;
   @EJB
   private BookManagerRemote bmr;
   @EJB
   private ReservationManagerRemote rmr;
   private ArrayList data = null;

    public void init(){
        System.out.println("LibraryServlet:init()");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        System.out.println("LibraryServlet:processRequest()");
        try {
            RequestDispatcher dispatcher;
            ServletContext servletContext = getServletContext();
            String page = request.getPathInfo();
            page = page.substring(1);

            if(page.equals("home")){ //Try to login
                data = login(request);
                request.setAttribute("data", data);
            } else if(page.equals("view-books")){
                data = searchTitles(request);
                request.setAttribute("data", data);
            } else if(page.equals("view-moreBooks")){
                data = searchBooks(request);
                request.setAttribute("data", data);
            } else if(page.equals("view-loans")){
                data = searchLoans(request);
                request.setAttribute("data", data);
            } else if(page.equals("view-loans-success")){
                updateDue(request);
            } else if(page.equals("view-reservation")){
                data = viewReservation(request);
                request.setAttribute("data", data);
            } else if(page.equals("view-reservation-success")){
                delReservation(request);
            }
            dispatcher = servletContext.getNamedDispatcher(page);
            if(dispatcher == null){
                dispatcher = servletContext.getNamedDispatcher(page);
            }
            dispatcher.forward(request, response);
        } catch(Exception e){
            log("Exception in LibraryServlet.processRequest()");
        } finally { 
        }
    }

    public void delReservation(HttpServletRequest request){
        String[] reservationID = request.getParameterValues("del");
        Long rid;
        if(reservationID != null){
            for(int i = 0; i < reservationID.length; i++){
                rid = new Long(reservationID[i]);
                rmr.delReservation(rid);
            }
        }
    }

    public ArrayList viewReservation(HttpServletRequest request) throws NumberFormatException{
        ArrayList reserveState = new ArrayList();
        String temp = request.getParameter("mid");
        Calendar c = Calendar.getInstance();
        Date reserveDate = c.getTime();
        Long mid, bid;

        mid = new Long(temp);
        temp = request.getParameter("bid");
        if(temp == null){
            reserveState = (ArrayList) rmr.getReservation(mid);
            return reserveState;
        } else{
            if(temp.equals("")){
                reserveState = (ArrayList) rmr.getReservation(mid);
                return reserveState;
            }
            bid = new Long(temp);
            rmr.addReservation(mid, bid, reserveDate);
            reserveState = (ArrayList) rmr.getReservation(mid);
            return reserveState;
        }
    }

    public void updateDue(HttpServletRequest request){
        String[] bookID = request.getParameterValues("extend");
        Long bid;
        if(bookID != null){
            for(int i = 0; i < bookID.length; i++){
                bid = new Long(bookID[i]);
                mmr.updateDue(bid);
            }
        }
    }

    public ArrayList searchLoans(HttpServletRequest request){
        ArrayList<BookState> books = new ArrayList<BookState>();
        String temp = request.getParameter("mid");
        Long mid = new Long(temp);
        /*
        try{
            if(!temp.equals("")){
                mid = Long.parseLong(temp);
            } else{
                return books;
            }
        } catch(NumberFormatException nfe){
            log("Exception in searchLoans");
        }
         * */
            mmr.setMember(mid);
            books = (ArrayList) mmr.getMemberLoan();
        
        return books;
    }

    public ArrayList searchBooks(HttpServletRequest request){
        ArrayList<BookState> books = new ArrayList<BookState>();
        String isbn;

        isbn = request.getParameter("isbn");

        books = (ArrayList) bmr.getBooks(isbn);

        return books;
    }

    public ArrayList searchTitles(HttpServletRequest request){
        ArrayList<TitleState> titles = new ArrayList<TitleState>();
        String type, query;
        query = request.getParameter("query");
        type = request.getParameter("type");
        if(query == null){
            query = "";
        }
        if(type == null){
            type = "title";
        }
        query = "%" + query + "%";

        titles = (ArrayList) bmr.getTitleBy(query, type);

        return titles;
    }

    /*Login based on name and password. Might have dupe. But probability of dupes are very low
     *
     * */
    public ArrayList login(HttpServletRequest request){
        MemberState ms;
        ArrayList m = new ArrayList(); //Use to store the return string (0) is id, (1) is name, (2) is type
        String username = request.getParameter("name");
        String password = request.getParameter("pass");

        //Do Login
        ms = mmr.login(username, password);

        if(ms != null){ //Is correct
            m.add(ms.getMid());
            m.add(ms.getName());
            m.add(ms.getType());
        }

        return m;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            processRequest(request, response);
            System.out.println("LibraryServlet:doGet()");
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("LibraryServlet:doPost()");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void destroy(){
        System.out.println("LibraryServlet:doDestroy()");
    }
}
