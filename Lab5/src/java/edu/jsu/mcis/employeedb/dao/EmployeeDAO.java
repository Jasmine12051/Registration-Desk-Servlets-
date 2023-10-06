package edu.jsu.mcis.employeedb.dao;

import edu.jsu.mcis.employeedb.Employee;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.github.cliftonlabs.json_simple.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmployeeDAO {
    
    private final List<Employee> employees;
    private int employeeid;
    
    private static final String FILENAME = "data.json";

    EmployeeDAO() {
        this.employees = new ArrayList<>();
        this.employeeid = 1;
        this.init();
    }
    
    private void init() {
        
        BufferedReader reader = null;
        InputStream file = null;
        
        try {
            
            file = this.getClass().getResourceAsStream("resources" + File.separator + FILENAME);
            reader = new BufferedReader(new InputStreamReader(file));
            
            StringBuilder data = new StringBuilder();
            
            String line;
            
            while((line = reader.readLine()) != null) {
                data.append(line);
            }
            
            JsonArray json = Jsoner.deserialize(data.toString(), new JsonArray());
            
            for (int i = 0; i < json.size(); ++i) {
                JsonObject record = (JsonObject)json.get(i);
                Employee employee = new Employee((employeeid++), record);
                employees.add(employee);
            }
            
        }
        catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
        finally {
            try {
                if (reader != null) { reader.close(); }
                if (file != null) { file.close(); }
            }
            catch (Exception e) { throw new DAOException(e.getMessage()); }
        }
        
    }
    
    public JsonObject create(JsonObject parameters) {
        
        // create a new employee record from the collection of parameters given by the client
        
        JsonObject json = new JsonObject();
        
        try {
            // Create a new Employee object
            Employee newEmployee = new Employee(employeeid++, parameters);

            // Add the new Employee to the list
            employees.add(newEmployee);

            // Provide a success message in the JSON response
            json.put("success", true);
            json.put("message", "Employee created successfully.");
        }
        catch (Exception e) {
        // Handle any exceptions that may occur during the process
        json.put("success", false);
        json.put("message", "Failed to create employee. Please check your input.");
        }        

        
        return json;
        
    }
    
    public JsonObject update(JsonObject parameters) {
        
        // update an existing employee record from the collection of parameters given by the client
        
        JsonObject json = new JsonObject();
        try {
            // Create a new Employee object
            int index = (int) parameters.get("id");
            Employee updateEmployee = employees.get(index);
            // Add the new Employee to the list
            updateEmployee.getAge();

            // Provide a success message in the JSON response
            json.put("success", true);
            json.put("message", "Employee created successfully.");
        }
        catch (Exception e) {
        // Handle any exceptions that may occur during the process
        json.put("success", false);
        json.put("message", "Failed to create employee. Please check your input.");
        }        

        
        return json;
        
    }
    
    public JsonObject delete(JsonObject parameters) {
        
        // delete the employee record specified by the client
        
        JsonObject json = new JsonObject();
        
        /* INSERT YOUR CODE HERE */
        
        return json;
        
    }
    
    public String find(Integer id) {
        
        // return the individual employee record specified by the client in JSON format
        
        JsonObject json = new JsonObject();
        
        Employee foundEmployee = null;
        
        for (Employee employee : employees) {
            if (Objects.equals(employee.getId(), id)) {
                foundEmployee = employee;
            }
        }

        if (foundEmployee != null) {
            // If the employee is found, create a JSON representation of the employee
            json.put("id", foundEmployee.getId());
            json.put("name", foundEmployee.getName());
            json.put("age", foundEmployee.getAge());
            json.put("salary", foundEmployee.getSalary());
        }
        
        return Jsoner.serialize(json);   
    }
    
    public String list() {
        
        // return all employee records (in their original order) in JSON format
        
        JsonArray employeeList = new JsonArray();
        
        for (Employee employee : employees) {
            JsonObject json = new JsonObject();
            json.put("id", employee.getId());
            json.put("name", employee.getName());
            json.put("age", employee.getAge());
            json.put("salary", employee.getSalary());
            employeeList.add(json);
        }
        
        
        return Jsoner.serialize(employeeList);
        
    }
    
}