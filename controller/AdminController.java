/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.Customer;
import database.Ordertable;
import database.Product;
import database.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author user
 */
public class AdminController extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "show_all_staff":
                    show_all_staff(request, response);
                    break;
                case "show_all_customer":
                    show_all_customer(request, response);
                    break;
                case "show_all_product":
                    show_all_product(request, response);
                    break;
                case "show_staff_infor":
                    show_staff_infor(request, response);
                    break;
                case "update_staff":
                    update_staff(request, response);
                    break;
                case "delete_staff_infor":
                    delete_staff_infor(request, response);
                    break;
                case "search_staff_by":
                    search_staff_by(request, response);
                    break;
                case "create_staff":
                    create_staff(request, response);
                    break;
                case "reactive_customer":
                    reactive_customer(request, response);
                    break;
                case "delete_customer":
                    delete_customer(request, response);
                    break;
                case "search_customer_by":
                    search_customer_by(request, response);
                    break;
                default:
                    // Handle invalid action
                    break;
            }
        } else {
            // Handle default behavior
        }

    }
    
//--------------------------Function Show All Start-----------------------------  
    
    private void show_all_staff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implement logic to show all staff accounts here
        HttpSession session = request.getSession();
        List<Staff> allStaff = em.createNamedQuery("Staff.findAll").getResultList();
        session.setAttribute("allStaff", allStaff);
        response.sendRedirect("staff_account_v.jsp");
    }

    public void show_all_customer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Customer> allCustomer = em.createNamedQuery("Customer.findAll").getResultList();
        session.setAttribute("allCustomer", allCustomer);
        response.sendRedirect("customer_account_v.jsp");
    }

    public void show_all_product(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> allProduct = em.createNamedQuery("Product.findAll").getResultList();
        session.setAttribute("allProduct", allProduct);
        response.sendRedirect("product_v.jsp");
    }

     public void show_all_order(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session = request.getSession(); 
        List<Ordertable> allOrder = em.createNamedQuery("Ordertable.findAll").getResultList();
        session.setAttribute("allOrder", allOrder);
        response.sendRedirect("order_v.jsp");
    }
     
//--------------------------Function Show All End-----------------------------  

    
//--------------------------Staff Function Start--------------------------------  
     
    public void show_staff_infor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String staffid = request.getParameter("staffid");
        Query query = em.createNamedQuery("Staff.findByStaffid");
        query.setParameter("staffid", staffid);
        List<Staff> staffDetail = query.getResultList();
        Staff staff = new Staff();
        staff.setEmail(staffDetail.get(0).getEmail());
        staff.setStaffid(staffDetail.get(0).getStaffid());
        staff.setName(staffDetail.get(0).getName());
        staff.setPosition(staffDetail.get(0).getPosition());
        staff.setPhoneNum(staffDetail.get(0).getPhoneNum());
        staff.setGender(staffDetail.get(0).getGender());
        staff.setStatus(staffDetail.get(0).getStatus());
        session.setAttribute("staffDetail", staff);
        response.sendRedirect("update_staff_v.jsp");
    }

    public void update_staff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String staffid = request.getParameter("staffid");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String temposition = request.getParameter("position");
        String phoneNum = request.getParameter("phone_num");
        String temgender = request.getParameter("gender");
        String status = request.getParameter("status");
        Timestamp updateDate = new Timestamp(System.currentTimeMillis());

        if(temgender == null){
            temgender = "H";
        }
        
        temposition = temposition.toUpperCase();
        temgender = temgender.toUpperCase();
        status = status.toUpperCase();
        
        if(status.charAt(0) == 'W' ){
            status = "WORKING";
        }else{
            status = "RESIGNED";
        }

        Character position = temposition.charAt(0);
        Character gender = temgender.charAt(0);

        // Retrieve the existing Staff entity using the staffid
        Staff staff = em.find(Staff.class, staffid);

        // Update the properties of the existing Staff entity with the new values
        staff.setName(name);
        staff.setEmail(email);
        staff.setPosition(position);
        staff.setPhoneNum(phoneNum);
        staff.setGender(gender);
        staff.setStatus(status);
        staff.setUpdateDate(updateDate);

        // Start a new transaction, update the Staff entity in the database, and commit the transaction
        try {
            utx.begin();
            em.merge(staff);
            utx.commit();
        } catch (Exception ex) {
             ex.printStackTrace();
        }
        
        session.setAttribute("success", "success");
        show_staff_infor(request, response);
    }
    
    public void delete_staff_infor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String staffid = request.getParameter("staffid");
        Staff staff = em.find(Staff.class, staffid);
        
        staff.setStatus("RESIGNED");
        
        try {
            utx.begin();
            em.merge(staff);
            utx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        show_all_staff(request, response);
    }
    
    public void search_staff_by(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String category = request.getParameter("field");
        String key = request.getParameter("key");
        
        if(key == null){
            show_all_staff(request, response);
        }

        List<Staff> staffList = null;

        switch (category) {
            case "id": {
                key = key.toUpperCase();
                TypedQuery<Staff> query = em.createNamedQuery("Staff.findByStaffid", Staff.class);
                query.setParameter("staffid", key);
                staffList = query.getResultList();
                break;
            }
            case "position": {
                key = key.toUpperCase();
                Character position = key.charAt(0);
                TypedQuery<Staff> query = em.createNamedQuery("Staff.findByPosition", Staff.class);
                query.setParameter("position", position);
                staffList = query.getResultList();
                break;
            }
            case "gender": {
                key = key.toUpperCase();
                Character gender = key.charAt(0);
                TypedQuery<Staff> query = em.createNamedQuery("Staff.findByGender", Staff.class);
                query.setParameter("gender", gender);
                staffList = query.getResultList();
                break;
            }
            case "name": {
                key = key.toUpperCase();
                TypedQuery<Staff> query = em.createNamedQuery("Staff.findByName", Staff.class);
                query.setParameter("name", key);
                staffList = query.getResultList();
                break;
            }
            default:
                show_all_staff(request, response);
                break;
        }

        session.setAttribute("allStaff", staffList);
        response.sendRedirect("staff_account_v.jsp");
    }

    public void create_staff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String temgender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone_num = request.getParameter("phone_num");
        Character position = 'S';
        String status = "WORKING";
        Timestamp createDate = new Timestamp(System.currentTimeMillis());
        Timestamp updateDate = new Timestamp(System.currentTimeMillis());

        if(temgender == null){
            temgender = "H";
        }
       
        temgender = temgender.toUpperCase();
        Character gender = temgender.charAt(0);
  
        List<Staff> allStaff = em.createNamedQuery("Staff.findAll").getResultList();

        for (int i = 0; i < allStaff.size(); i++) {
            if (allStaff.get(i).getEmail().equals(email) || allStaff.get(i).getPhoneNum().equals(phone_num)) {
                session.setAttribute("message", "Account exist");
                response.sendRedirect("create_staff_v.jsp");
            }
        }

        String staffid = createStaffID();
        Staff staff = new Staff();
        staff.setStaffid(staffid);
        staff.setName(name);
        staff.setPassword(password);
        staff.setGender(gender);
        staff.setEmail(email);
        staff.setPhoneNum(phone_num);
        staff.setPosition(position);
        staff.setStatus(status);
        staff.setCreateDate(createDate);
        staff.setUpdateDate(updateDate);

        try {
            utx.begin();
            em.persist(staff);
            utx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("message", "Failed to create account");
            response.sendRedirect("create_staff_v.jsp");
        }

        session.setAttribute("message", "Account Created");
        response.sendRedirect("create_staff_v.jsp");

    }
    
//-----------------------------Staff Function End-------------------------------   
    
//-------------------------Customer Function Start------------------------------
    
    public void reactive_customer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String customerid = request.getParameter("customerid");
        Customer customer = em.find(Customer.class, customerid);
        
        customer.setStatus('A');
        
        try {
            utx.begin();
            em.merge(customer);
            utx.commit();
            session.setAttribute("message", "Update Successful");
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("message", "Update Failed");
        }

        show_all_customer(request, response);
    }
    
    public void delete_customer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String customerid =request.getParameter("customerid");
        Customer customer = em.find(Customer.class, customerid);
        
        customer.setStatus('I');
        
        try {
            utx.begin();
            em.merge(customer);
            utx.commit();
            session.setAttribute("message", "Delete Successful");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("message", "Delete Failed");
        }

        show_all_customer(request, response);
    }
    
    public void search_customer_by(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String category = request.getParameter("cfield");
        String key = request.getParameter("ckey");
        
        if(key == null){
            show_all_customer(request, response);
        }

        List<Customer> customerList = null;

        switch (category) {
            case "id": {
                TypedQuery<Customer> query = em.createNamedQuery("Customer.findByCustomerid", Customer.class);
                query.setParameter("customerid", key);
                customerList = query.getResultList();
                break;
            }
            case "email": {
                TypedQuery<Customer> query = em.createNamedQuery("Customer.findByEmail", Customer.class);
                query.setParameter("email", key);
                customerList = query.getResultList();
                break;
            }
            case "gender": {
                key = key.toUpperCase();
                Character gender = key.charAt(0);
                TypedQuery<Customer> query = em.createNamedQuery("Customer.findByGender", Customer.class);
                query.setParameter("gender", gender);
                customerList = query.getResultList();
                break;
            }
            case "name": {
                key = key.toUpperCase();
                TypedQuery<Customer> query = em.createNamedQuery("Customer.findByUsername", Customer.class);
                query.setParameter("username", key);
                customerList = query.getResultList();
                break;
            }
            default:
                show_all_customer(request, response);
                break;
        }

        session.setAttribute("allCustomer", customerList);
        response.sendRedirect("customer_account_v.jsp");
    }
    
//-------------------------Customer Function End--------------------------------
    
    
    
    
    
//---------------------------Generate StaffId Start-----------------------------
    
    public String createStaffID() {
        
        List<Customer> tempCustomerList = em.createNamedQuery("Customer.findAll").getResultList();
        //if the list is not empty then can create customer id 
        if (!tempCustomerList.isEmpty()) {
            
            char firstIndex = tempCustomerList.get(tempCustomerList.size() - 1).getCustomerid().charAt(1);
            char secondIndex = tempCustomerList.get(tempCustomerList.size() - 1).getCustomerid().charAt(2);
            char thirdIndex = tempCustomerList.get(tempCustomerList.size() - 1).getCustomerid().charAt(3);

            String tempCustomerID = String.valueOf(firstIndex) + String.valueOf(secondIndex) + String.valueOf(thirdIndex);
            int nextCustomerID = Integer.parseInt(tempCustomerID) + 1;
            return "S" + String.format("%03d", nextCustomerID);
        } else {
            return "S" + String.format("%03d", 1);
        }
    }
    
//---------------------------Generate StaffId End-------------------------------

    
    
//--------------------------------Back-up Code----------------------------------    
//    public void delete_staff_infor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        int staffid = Integer.parseInt(request.getParameter("staffid"));
//        Staff staff = em.find(Staff.class, staffid);
//
//        try {
//            utx.begin();
//            if (!em.contains(staff)) {
//                staff = em.merge(staff);
//            }
//            em.remove(staff);
//            utx.commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        show_all_staff(request, response);
//    }

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
