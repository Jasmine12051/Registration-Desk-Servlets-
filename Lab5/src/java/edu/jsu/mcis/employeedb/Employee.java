package edu.jsu.mcis.employeedb;

import java.util.Map;
import com.github.cliftonlabs.json_simple.*;

public class Employee {
    
    private final Integer id, salary, age;
    private final String name;
    
    public Employee(Integer id, Map p) {
        this.id = id;
        this.salary = Integer.parseInt(p.get("salary").toString());
        this.age = Integer.parseInt(p.get("age").toString());
        this.name = p.get("name").toString();
    }

    public Integer getId() {
        return id;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
    
    public JsonObject getProperties() {
        
        JsonObject json = new JsonObject();
        
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("age", this.age);
        json.put("salary", this.salary);
        
        return json;
        
    }
    
    @Override
    public String toString() {
        
        JsonObject json = this.getProperties();
        
        return Jsoner.serialize(json);
        
    }
    
}
