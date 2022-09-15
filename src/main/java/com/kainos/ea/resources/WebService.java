package com.kainos.ea.resources;

import com.kainos.ea.db.DbConnection;
import com.kainos.ea.db.GetEmployee;
import com.kainos.ea.db.InsertEmployee;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Path("/api")
@Api("employee")
public class WebService {

    @GET
    @Path("/getEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees() {

        List<Employee> emps = GetEmployee.getEmployees();
        return emps;

    }

//    @POST
//    @Path("/addEmployees")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void getEmployees(List<Employee> employees) {
//
//        InsertEmployee.insertEmployees(employees);
//
//    }

    @POST
    @Path("/addemployee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String enterEmployee(Employee message) {
        // Return a string for testing purposes
        System.out.println(message);

        // TODO: use the fields of 'message' to add an employee to the database
        String fname = message.getFname();
        String lname = message.getLname();
        String address = message.getAddress();
        String postcode = message.getPostcode();
        String nin = message.getNin();
        String bankAccount = message.getBankAccount();
        String startingSalary = String.valueOf(message.getStartingSalary());
        boolean isManager = true;
        String department = String.valueOf(message.getDepartment());
        System.out.println(message);


        try {
            Connection con = DbConnection.getConnection();
            System.out.println(con);

            String sql = "INSERT INTO Employee (fname, lname, postcode, address, nin," +
                    "bank_account, starting_salary, isManager, department) VALUES " +
                    "(?,?, ?, ?, ?, ?, ?, ?, ?);";
//                    "(\"" + fname + "\",\"" + lname + "\",\"" + postcode + "\",\"" +
//                    address + "\",\"" + nin + "\",\"" + bankAccount + "\",\"" + startingSalary +
//                    "\",\"" + isManager + "\",\"" + department + "\");";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, postcode);
            statement.setString(4, address);
            statement.setString(5, nin);
            statement.setString(6, bankAccount);
            statement.setString(7, startingSalary);
            statement.setBoolean(8, isManager);
            statement.setString(9, department);
            statement.execute();
            return "Data inserted successfully";
        }
        catch (SQLException e) {
            // Bad practice :)
            //TODO: actually handle this exception
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        return  "Something went wrong. Database not updated.";
    }
}
