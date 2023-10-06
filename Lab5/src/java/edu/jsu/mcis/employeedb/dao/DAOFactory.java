package edu.jsu.mcis.employeedb.dao;

public final class DAOFactory {

    private final EmployeeDAO employeeDAO;

    public DAOFactory() {

        this.employeeDAO = new EmployeeDAO();

    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

}
