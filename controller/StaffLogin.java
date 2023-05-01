/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.Product;
import database.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class StaffLogin extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    private boolean accFound = false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        try{
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            List<Staff> StaffList = getAllAccounts();
            Staff staff = new Staff();
            
            List<Product> AllProduct = getAllproduct();
                
            //loop for found customer from customer list
            for (int i = 0; i < StaffList.size(); i++) {
                //if username and password match with one of the customer on list
                if (StaffList.get(i).getEmail().equalsIgnoreCase(email) && StaffList.get(i).getPassword().equalsIgnoreCase(password)) {
                    
                    staff.setEmail(email);
                    staff.setStaffid(StaffList.get(i).getStaffid());
                    staff.setName(StaffList.get(i).getName());
                    staff.setPosition(StaffList.get(i).getPosition());
                    staff.setPassword(password);
                    staff.setPhoneNum(StaffList.get(i).getPhoneNum());
                    staff.setGender(StaffList.get(i).getGender());
                                        
                    accFound = true;
                    break;
                }else{
                    accFound = false;
                }
            }
            
            if(accFound == true){
                session.setAttribute("StaffInfor", staff);
                session.setAttribute("AllProduct", AllProduct);
                
                response.sendRedirect("admin_v.jsp");
            }else{
                response.sendRedirect("index.html");
            }     
                
            }catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "SQL Error", "DB ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        public List<Staff> getAllAccounts() {
            List accountList = em.createNamedQuery("Staff.findAll").getResultList();
            return accountList;
        }

        public List<Product> getAllproduct(){
            List allProduct = em.createNamedQuery("Product.findAll").getResultList();
            return allProduct;
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
