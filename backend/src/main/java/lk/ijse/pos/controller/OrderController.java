package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOType;
import lk.ijse.pos.bo.FactoryBO;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.util.HandleController;
import lk.ijse.pos.util.Status;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/order")
public class OrderController  extends HttpServlet implements HandleController {
    PurchaseOrderBO purchaseOrderBO=(PurchaseOrderBO) FactoryBO.getInstance().getBO(BOType.PURCHASE_ORDER);
    String message="message";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");

        if(status.equals("GET") || status.equals("SEARCH")){
            switch (status) {
                case "GET":

                    List<OrderDTO> allOrders = purchaseOrderBO.getAllOrders();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (OrderDTO orderDTO : allOrders) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("orderID", orderDTO.getOrderID());
                        objectBuilder.add("date", orderDTO.getDate().toString());
                      objectBuilder.add("time", orderDTO.getTime().toString());
                        objectBuilder.add("discount", orderDTO.getDiscount());
                        objectBuilder.add("customerID", orderDTO.getCustomer().getCustomerID());
                        // For OrderDetails
                        JsonArrayBuilder arrayBuilderOrderDetail = Json.createArrayBuilder();
                        for (OrderDetailDTO o : orderDTO.getOrderDetailList()) {
                            JsonObjectBuilder objectBuilderOrderDetail = Json.createObjectBuilder();
                            objectBuilderOrderDetail.add("orderID",o.getOrder().getOrderID());
                            objectBuilderOrderDetail.add("itemCode",o.getItem().getCode());
                            objectBuilderOrderDetail.add("discount",o.getDiscount());
                            objectBuilderOrderDetail.add("qty",o.getQty());
                            objectBuilderOrderDetail.add("unitPrice",o.getUnitPrice());
                            arrayBuilderOrderDetail.add(objectBuilderOrderDetail.build());
                            System.out.println(o.getOrder().getOrderID());
                        }
                        objectBuilder.add("orderDetail", arrayBuilderOrderDetail.build());
                        arrayBuilder.add(objectBuilder.build());
                    }
                    resp.getWriter().println(arrayBuilder.build());
                    break;
                case "SEARCH":
                    OrderDTO orderDTO = purchaseOrderBO.searchOrder(req.getParameter("orderID"));
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("orderID", orderDTO.getOrderID());
                    objectBuilder.add("date", orderDTO.getDate().toString());
                    objectBuilder.add("time", orderDTO.getTime().toString());
                    objectBuilder.add("discount", orderDTO.getDiscount());
                    objectBuilder.add("customerID", orderDTO.getCustomer().getCustomerID());
                    // For OrderDetails
                    JsonArrayBuilder arrayBuilderOrderDetail = Json.createArrayBuilder();
                    for (OrderDetailDTO o : orderDTO.getOrderDetailList()) {
                        JsonObjectBuilder objectBuilderOrderDetail = Json.createObjectBuilder();
                        objectBuilderOrderDetail.add("orderID",o.getOrder().getOrderID());
                        objectBuilderOrderDetail.add("itemCode",o.getItem().getCode());
                        objectBuilderOrderDetail.add("discount",o.getDiscount());
                        objectBuilderOrderDetail.add("qty",o.getQty());
                        objectBuilderOrderDetail.add("unitPrice",o.getUnitPrice());
                        arrayBuilderOrderDetail.add(objectBuilderOrderDetail.build());
                        System.out.println(o.getOrder().getOrderID());
                    }
                    objectBuilder.add("orderDetail", arrayBuilderOrderDetail.build());
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
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            saveAndUpdateCustomer(req,resp,Status.SAVE);
            objectBuilder.add("status",true);
            objectBuilder.add(message,"success");
        } catch (Exception e) {
            resp.setStatus(200);
            objectBuilder.add("status",false);
            objectBuilder.add(message,e.getMessage());
            e.printStackTrace();
        }finally {
            resp.getWriter().println(objectBuilder.build());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            saveAndUpdateCustomer(req,resp,Status.UPDATE);
            objectBuilder.add("status",true);
            objectBuilder.add(message,"success");
        } catch (Exception e) {
            resp.setStatus(200);
            objectBuilder.add("status",false);
            objectBuilder.add(message,e.getMessage());
            e.printStackTrace();
        }finally {
            resp.getWriter().println(objectBuilder.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        try {
            if (purchaseOrderBO.deleteOrder(req.getParameter("orderID"))) {
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
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();


            JsonObject orderJson = jsonObject.getJsonObject("order");
            JsonArray orderDetail = jsonObject.getJsonArray("orderDetail");
            OrderDTO orderDTO = new OrderDTO(
                    orderJson.getString("orderID"),
                    Date.valueOf(orderJson.getString("date")),
                    Time.valueOf(LocalTime.parse(orderJson.getString("time"))),
                    orderJson.getInt("discount"),
                    new CustomerDTO()
            );
            orderDTO.getCustomer().setCustomerID(orderJson.getString("customerID"));
            List<OrderDetailDTO> dtoList=new ArrayList<>();
            for (JsonValue jsonValue : orderDetail) {
                JsonObject jsonObject1 = jsonValue.asJsonObject();
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setCode(jsonObject1.getString("itemCode"));
                dtoList.add(new OrderDetailDTO(
                        orderDTO,
                        jsonObject1.getInt("qty"),
                        jsonObject1.getInt("discount"),
                        jsonObject1.getJsonNumber("unitPrice").doubleValue(),
                        itemDTO
                ));
            }
            orderDTO.setOrderDetailList(dtoList);
            switch (status){
                case SAVE:purchaseOrderBO.saveOrder(orderDTO);break;
                case UPDATE:purchaseOrderBO.updateOrder(orderDTO);break;
            }


    }

    @Override
    public boolean isValid(JsonObject jsonObject) {
        return false;
    }
}
