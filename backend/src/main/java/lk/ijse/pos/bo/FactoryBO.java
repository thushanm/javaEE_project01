package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.CustomerBOImpl;
import lk.ijse.pos.bo.custom.impl.ItemBOImpl;
import lk.ijse.pos.bo.custom.impl.PurchaseOrderBOImpl;

public class FactoryBO {
    private static FactoryBO factoryBO;
    private FactoryBO() {}
 public static FactoryBO getInstance(){
        return factoryBO==null ? new FactoryBO():factoryBO;
 }
 public SuperBO getBO(BOType boType){
     switch (boType) {
         case CUSTOMER:return new CustomerBOImpl();
         case ITEM:return new ItemBOImpl();
         case PURCHASE_ORDER:return new PurchaseOrderBOImpl();
         default:return null;
     }
     }

}
