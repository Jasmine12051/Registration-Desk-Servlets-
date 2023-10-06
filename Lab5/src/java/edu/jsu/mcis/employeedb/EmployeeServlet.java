package edu.jsu.mcis.employeedb;

import edu.jsu.mcis.employeedb.dao.DAOException;
import edu.jsu.mcis.employeedb.dao.DAOFactory;
import edu.jsu.mcis.employeedb.dao.EmployeeDAO;
import java.io.PrintWriter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.github.cliftonlabs.json_simple.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;

public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        // Acquire DAOFactory
        
        DAOFactory daoFactory;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory)context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");
        
        try ( PrintWriter out = response.getWriter()) {
                       
            String p_id = request.getParameter("id");            
            if (p_id == null || "".equals(p_id)) {
                EmployeeDAO dao = daoFactory.getEmployeeDAO();
                out.println(dao.list());  
            }
            
            else{
                int id = Integer.parseInt(request.getParameter("id"));
                EmployeeDAO dao = daoFactory.getEmployeeDAO();
                out.println(dao.find(id));
            
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        DAOFactory daoFactory;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory)context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");        

        JsonObject json = new JsonObject();
        try ( PrintWriter out = response.getWriter()) {
            EmployeeDAO dao = daoFactory.getEmployeeDAO();
            json.put("name", request.getParameter("name"));
            json.put("age", request.getParameter("age"));
            json.put("salary", request.getParameter("salary"));
            
            out.println(dao.create(json));
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

        DAOFactory daoFactory;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory)context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");        

        JsonObject json = new JsonObject();
        try ( PrintWriter out = response.getWriter()) {
            EmployeeDAO dao = daoFactory.getEmployeeDAO();
            int id = Integer.parseInt(request.getParameter("id"));
            out.println(dao.find(id));
            
            json.put("id", request.getParameter("id"));
            json.put("name", request.getParameter("name"));
            json.put("age", request.getParameter("age"));
            json.put("salary", request.getParameter("salary"));
            
            out.println(dao.update(json));
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {

        /* INSERT YOUR CODE HERE */
        
    }

    @Override
    public String getServletInfo() {
        return "Employee Servlet";
    }

}