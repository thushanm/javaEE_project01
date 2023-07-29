package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.pos.entity.SuperEntity;

public class FactoryDAO {
   private static FactoryDAO factoryDAO;
   private FactoryDAO(){

   }
   public static FactoryDAO getInstance(){
       return factoryDAO==null ? factoryDAO=new FactoryDAO():factoryDAO;
   }
   public SuperDAO getDAO(DAOType daoType){
       switch (daoType) {
           case CUSTOMER: return new CustomerDAOImpl();
           case ITEM:return new ItemDAOImpl();
           case ORDER:return new OrderDAOImpl();
           case ORDER_DETAIL:return new OrderDetailDAOImpl();
           default:return null;
       }
   }
}
