package lk.ijse.pos.util;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandleController {
    void saveAndUpdateCustomer(HttpServletRequest req, HttpServletResponse resp, Status status) throws Exception;
   boolean isValid(JsonObject jsonObject);
}
