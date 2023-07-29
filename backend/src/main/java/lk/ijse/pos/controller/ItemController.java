package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOType;
import lk.ijse.pos.bo.FactoryBO;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
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

@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet implements HandleController {
    ItemBO itemBO= (ItemBO) FactoryBO.getInstance().getBO(BOType.ITEM);
    String message="message";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");

        if(status.equals("GET") || status.equals("SEARCH")){
            switch (status) {
                case "GET":
                    List<ItemDTO> allItem = itemBO.getAllItem();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (ItemDTO itemDTO : allItem) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("code", itemDTO.getCode());
                        objectBuilder.add("description", itemDTO.getDescription());
                        objectBuilder.add("qty", itemDTO.getQty());
                        objectBuilder.add("unitPrice", itemDTO.getUnitPrice());
                        arrayBuilder.add(objectBuilder.build());
                    }
                    resp.getWriter().println(arrayBuilder.build());
                    break;
                case "SEARCH":
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    ItemDTO itemDTO = itemBO.searchItem(req.getParameter("code"));
                    objectBuilder.add("code", itemDTO.getCode());
                    objectBuilder.add("description", itemDTO.getDescription());
                    objectBuilder.add("qty", itemDTO.getQty());
                    objectBuilder.add("unitPrice", itemDTO.getUnitPrice());
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
            saveAndUpdateCustomer(req,resp,Status.SAVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            saveAndUpdateCustomer(req,resp,Status.UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (itemBO.deleteItem(req.getParameter("code"))) {
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
    public void saveAndUpdateCustomer(HttpServletRequest req, HttpServletResponse resp, Status status) throws Exception {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            resp.setContentType("application/json");
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            if (isValid(jsonObject)) {
                ItemDTO itemDTO = new ItemDTO(
                        jsonObject.getString("code"),
                        jsonObject.getString("description"),
                        jsonObject.getInt("qty"),
                        jsonObject.getJsonNumber("unitPrice").doubleValue()

                );
                switch (status) {
                    case SAVE:
                        itemBO.saveItem(itemDTO);
                        break;
                    case UPDATE:
                        itemBO.updateItem(itemDTO);
                        break;
                }
                if (itemDTO != null) {
                    objectBuilder.add("status", true);
                    objectBuilder.add(message, "success");
                }
            } else {
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
    public boolean isValid(JsonObject jsonObject) {
       return true;
    }
}
