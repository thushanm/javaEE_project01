import {customerList, itemList, orderDetailList, orderList} from "../db/DB.js";
import {OrderDetail} from "../model/OrderDetail.js";
import {Order} from "../model/Order.js";

export class OrderController {


    constructor() {
        this.customerList = customerList;
        this.itemList = itemList;
        this.orderList = orderList;
        this.orderDetailList = orderDetailList;
        this.orderDetailListTM=[];

        this.initialize();
        $("#btnOrderAdd").click(this.handelItemAddToTable.bind(this));
        $("#btnPurchaseOrder").click(this.handelPlaceOrder.bind(this));
        $("#txtDiscount").on("keyup", this.handelCashAndDiscount.bind(this));

    }

    initialize() {
        let id = orderList.length + "";
        $("#lblOrderID").text("D" + id.padStart(3, "0"));

        $("#dropCustomer").html("");
        this.customerList.forEach(e => {
            $("#dropCustomer").append(`
        <li><a class="dropdown-item fw-bold" href="#">${e.id}</a></li>`);
            $("#dropCustomer>:last-child").click(() => {
                $("#orderCustomerName").val(e.name);
                $("#orderCustomerAddress").val(e.address);
                $("#orderCustomerSalary").val(e.salary);
                $("#btnDropDownCustomer").text(e.id);
            });
        });

        $("#dropItemCode").html("");
        this.itemList.forEach(e => {
            $("#dropItemCode").append(`
        <li><a class="dropdown-item fw-bold" href="#">${e.code}</a></li>`);
            $("#dropItemCode>:last-child").click(() => {
                $("#orderItemDesc").val(e.description);
                $("#orderItemUnitPrice").val(e.unitPrice);
                $("#btnDropDownItem").text(e.code);
            });
        });

    }

    handelItemAddToTable() {
        if (this.isValid()) {
            let orderID = $("#lblOrderID").text();
            let qty = Number($("#orderItemQty").val());
            let orderDetail = new OrderDetail(orderID, $("#btnDropDownItem").text(), $("#orderItemDesc").val(), qty, Number($("#orderItemUnitPrice").val()) * qty);
            if (this.orderDetailListTM.length > 0) {
                let isExist = false;
                this.orderDetailListTM.forEach(e => {
                    if ( e.itemCode === orderDetail.itemCode) {
                        e.qty = e.qty + orderDetail.qty;
                        e.unitPrice = e.unitPrice * e.qty;
                        isExist = true;
                    }

                });
                if (!isExist) {
                    this.orderDetailListTM.push(orderDetail);
                    isExist = false;
                }
                $("#tblOrderDetailBody").html("");
                this.orderDetailListTM.forEach(e => {
                    if (orderID === e.orderID) {
                    $("#tblOrderDetailBody").append(`<tr> 
                       <td>${e.itemCode}</td> 
                       <td>${e.description}</td>
                       <td>${e.qty}</td>
                        <td>${e.unitPrice}</td> 
                        <td><button class="btn btn-danger btn-sm">Delete</button></td> </tr>`);
                    this.addDeleteEvent($("#tblOrderDetailBody>:last-child button"));
                    }
                });
            } else {
                this.orderDetailListTM.push(orderDetail);
                $("#tblOrderDetailBody").append(`<tr> 
                       <td>${orderDetail.itemCode}</td> 
                       <td>${orderDetail.description}</td>
                       <td>${orderDetail.qty}</td>
                        <td>${orderDetail.unitPrice}</td> 
                        <td><button class="btn btn-danger btn-sm">Delete</button></td> </tr>`);
                this.addDeleteEvent($("#tblOrderDetailBody>:last-child button"));
            }

            this.handelCashAndDiscount();


        }
    }

    handelPlaceOrder() {
        let cash = Number($("#txtCash").val());
        let total= this.handelCashAndDiscount();
        let dis=Number($("#txtDiscount").val());
        let subTotal =total- (dis /100) *total;
        if(cash>=subTotal){
            alert("change : "+(cash-subTotal));
            this.orderList.push(new Order($("#lblOrderID").text(),new Date().toISOString().split("T")[0],new Date().toLocaleTimeString(),$("#btnDropDownCustomer").text(),dis,total));
            $("#btnDropDownCustomer").text("Customer ID");
            $("#btnDropDownItem").text("Item ID");
            $("#txtCash").val("");
            $("#txtDiscount").val("");
            $("#orderUpSection input").val("");
            $("#orderItemQty").val(1);
            $("#orderSubTotal").text("0.0");
            $("#orderTotal").text("0.0");
            $("#tblOrderDetailBody").html("");

            this.initialize();
            this.orderDetailListTM.forEach(e =>{
                this.orderDetailList.push(e);
            });
            this.orderDetailListTM=[];
        }else{
            alert("please pay the cash!!!")
        }

    }

    isValid() {
        if ($("#orderItemQty").val() === "" || $("#orderItemQty").val() <= 0) {
            alert("please Add Qty");
            return false;
        } else {
            if ($("#orderCustomerName").val() === "") {
                alert("please Select Customer");
                return false;
            } else {
                if ($("#orderItemDesc").val() === "") {
                    alert("please Select Item");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    addDeleteEvent(btn) {
        $(btn).click(() => {
            let row = $(btn).parent().parent();
            this.orderDetailListTM = this.orderDetailListTM.filter(e => e.itemCode !== row.children().eq(0).text());
            $(row).remove();
            if (this.orderDetailListTM.length > 0) {
                    this.handelCashAndDiscount();
            } else {
                $("#orderTotal").text("0.00");
                $("#orderSubTotal").text("0");
            }
        });
    }

    handelCashAndDiscount() {
        let total = 0.0;
        this.orderDetailListTM.forEach(e => {
            if(e.orderID===$("#lblOrderID").text()){
            total += e.unitPrice;
            }
        });
        $("#orderTotal").text(total);
        let dis = Number($("#txtDiscount").val());
        $("#orderSubTotal").text(total-(dis / 100) * total);
        return total;
    }
}

new OrderController();