import {Customer} from "../model/Customer.js";
import {Item} from"../model/Item.js";
import {Order} from "../model/Order.js";
import {OrderDetail} from "../model/OrderDetail.js";

var customerList=[];
var itemList=[];
var orderList=[];
var orderDetailList=[];





$.ajax({
    url:"http://localhost:8080/simplePOS/item?status=GET",
    method:"GET",
    dataType:"json",
    async:false,
    success:function (res){
        for (let item of res) {
            itemList.push(new Item(item.code,item.description,item.qty,item.unitPrice));
        }
    },
    error:function (e){
        console.log(e);
    }
});

// orderList.push(new Order("D005",new Date().toISOString().split("T")[0],new Date().toLocaleTimeString(),"C001","0","1000.00"));

export {customerList,itemList,orderList,orderDetailList}



