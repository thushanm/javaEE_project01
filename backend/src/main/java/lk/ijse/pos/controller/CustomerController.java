package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOType;
import lk.ijse.pos.bo.FactoryBO;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.util.HandleController;
import lk.ijse.pos.util.Status;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet implements HandleController {
    CustomerBO customerBO = (CustomerBO) FactoryBO.getInstance().getBO(BOType.CUSTOMER);
    String message = "massage";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");

        if(status.equals("GET") || status.equals("SEARCH")){
            switch (status) {
                case "GET":
                    List<CustomerDTO> allCustomer = customerBO.getAllCustomer();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (CustomerDTO customerDTO : allCustomer) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", customerDTO.getCustomerID());
                        objectBuilder.add("name", customerDTO.getName());
                        objectBuilder.add("address", customerDTO.getAddress());
                        objectBuilder.add("salary", customerDTO.getSalary());
                        arrayBuilder.add(objectBuilder.build());
                    }
                    resp.getWriter().println(arrayBuilder.build());
                    break;
                case "SEARCH":
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    CustomerDTO customerDTO = customerBO.searchCustomer(req.getParameter("id"));
                    objectBuilder.add("id", customerDTO.getCustomerID());
                    objectBuilder.add("name", customerDTO.getName());
                    objectBuilder.add("address", customerDTO.getAddress());
                    objectBuilder.add("salary", customerDTO.getSalary());
                    resp.getWriter().println(objectBuilder.build());
            }


        }else{
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status",false);
            objectBuilder.add(message,"Invalid Status");
            resp.getWriter().println(objectBuilder.build());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            saveAndUpdateCustomer(req, resp, Status.SAVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            saveAndUpdateCustomer(req, resp, Status.UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void saveAndUpdateCustomer(HttpServletRequest req, HttpServletResponse resp, Status status) throws Exception {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            resp.setContentType("application/json");
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            if (isValid(jsonObject)) {
                CustomerDTO customerDTO = new CustomerDTO(
                        jsonObject.getString("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("address"),
                        jsonObject.getJsonNumber("salary").doubleValue()
                );
                switch (status) {
                    case SAVE:
                        customerBO.saveCustomer(customerDTO);
                        break;
                    case UPDATE:
                        customerBO.updateCustomer(customerDTO);
                        break;
                }
                if (customerDTO != null) {
                    objectBuilder.add("status", true);
                    objectBuilder.add(message, "success");
                }
            } else {
                objectBuilder.add(message, "invalid input");
                throw new RuntimeException("invalid input....");
            }
        } catch (Exception e) {
            resp.setStatus(200);
            objectBuilder.add("status", false);
            objectBuilder.add(message, e.toString());
            e.printStackTrace();
        } finally {
            resp.getWriter().println(objectBuilder.build());

        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (customerBO.deleteCustomer(req.getParameter("id"))) {
                objectBuilder.add("status", true);
                objectBuilder.add(message, "success");
            }
        } catch (Exception e) {
            resp.setStatus(200);
            objectBuilder.add("status", false);
            objectBuilder.add(message, e.getMessage());
            e.printStackTrace();
        } finally {
            resp.getWriter().println(objectBuilder.build());
        }
    }

    @Override
    public boolean isValid(JsonObject jsonObject) {
        if (jsonObject.getString("id") == null) {
            return false;
        } else {
            if (jsonObject.getString("name") == null) {
                return false;
            } else {
                return jsonObject.getString("address") != null;
            }
        }
    }


}
