import {itemList} from "../db/DB.js";
import {Item} from "../model/Item.js";

export class ItemController {

    constructor() {
        $("#itemBtnSection>:nth-child(1)").click(this.handelSaveItem.bind(this));
        $("#itemBtnSection>:nth-child(2)").click(this.handelUpdateItem.bind(this));
        $("#itemBtnSection>:nth-child(3)").click(this.handelDeleteItem.bind(this));
        $("#itemBtnSection>:nth-child(4)").click(this.handelNewItemAddTOClear.bind(this));
        $("#searchItem").on("keyup", this.handelSearchItem.bind(this));
        this.itemList = itemList;
        this.loadItemTable();
        this.initialize();
    }
    initialize() {
        let id = itemList.length+1+"";
        $("#itemCode").val("P"+id.padStart(3,"0"));

    }
    handelSaveItem() {
        for (let item of this.itemList) {
            if (item._code === $("#itemCode").val()) {
                alert("Item Exists....!!!")
                return;
            }
        }
        if (this.isValid()) {
            let code = $("#itemCode").val();
            let desc = $("#itemDesc").val();
            let qty = $("#itemQty").val();
            let unitPrice = $("#itemUnitPrice").val();
            this.itemList.push(new Item(code, desc, qty, unitPrice));
            this.loadItemTable();

        }
    }

    isValid() {
        let code = /^P([0-9]){3,3}$/;
        let qty = /^(-)?([0-9]){1,5}$/;
        let unitPrice = /^\d+(\.\d)?\d*$/;

        if (!code.test($("#itemCode").val())) {
            $("#itemCode").css("border-color", "red");
            return false;
        } else {
            if ($("#itemDesc").val() === "") {
                $("#itemDesc").css("border-color", "red");
                return false;
            } else {
                if (!qty.test($("#itemQty").val())) {
                    $("#itemQty").css("border-color", "red");
                    return false;
                } else {
                    if (!unitPrice.test($("#itemUnitPrice").val())) {
                        $("#itemUnitPrice").css("border-color", "red");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    handelUpdateItem() {
        if (this.isValid()) {
            let code = $("#itemCode").val();
            let desc = $("#itemDesc").val();
            let qty = $("#itemQty").val();
            let unitPrice = $("#itemUnitPrice").val();
            this.itemList.forEach(e =>{
                if (e.code === code) {
                    e.description=desc;
                    e.qty=qty;
                    e.unitPrice=unitPrice;
                }
            });

            this.loadItemTable();

        }
    }

    handelDeleteItem() {
        this.itemList=this.itemList.filter(e =>{ return  e.code!== $("#itemCode").val()});
        this.loadItemTable();

    }

    handelSearchItem() {
            let filter = this.itemList.filter(e => e.description.startsWith($("#searchItem").val()) || e.description.toLowerCase().startsWith($("#searchItem").val()));
        if ($("#searchItem").val() === "") {
            this.loadItemTable();

        }else{
            $("#tblItemBody").html("");
            filter.forEach( e =>{
                $("#tblItemBody").append(`<tr><td>${e.code}</td><td>${e.description}</td><td>${e.qty}</td><td>${e.unitPrice}</td></tr>`);
                $("#tblItemBody > tr").click((evt)=>{
                    this.manageRowEvent(evt);
                });
            });

        }
    }

    handelNewItemAddTOClear() {
        $("#itemBtnSection>:nth-child(1)").prop("disabled", false);
        $("#itemBtnSection>:nth-child(2)").prop("disabled", true);
        $("#itemBtnSection>:nth-child(3)").prop("disabled", true);

        $("#itemBtnSection>:nth-child(1)").removeClass("opacity-25");
        $("#itemBtnSection>:nth-child(2)").removeClass("opacity-100");
        $("#itemBtnSection>:nth-child(3)").removeClass("opacity-100");

        $("#itemBtnSection>:nth-child(1)").addClass("opacity-100");
        $("#itemBtnSection>:nth-child(2)").addClass("opacity-25");
        $("#itemBtnSection>:nth-child(3)").addClass("opacity-25");
        this.clearTextField();
    }

    loadItemTable() {
        $("#tblItemBody").html("");
        this.itemList.forEach((e) => {
            $("#tblItemBody").append(`<tr><td>${e.code}</td><td>${e.description}</td><td>${e.qty}</td><td>${e.unitPrice}</td></tr>`);
            $("#tblItemBody > tr").click((evt)=>{
                this.manageRowEvent(evt);
            });
        });
        this.clearTextField();
        this.initialize();
    }

    manageRowEvent(event) {
        let col = event.currentTarget.children;
        for (let rowKey of event.currentTarget.parentNode.children) {
            rowKey.classList.remove("bg-primary");
        }
            event.currentTarget.classList.add("bg-primary");

        $("#itemBtnSection>:nth-child(1)").prop("disabled", true);
        $("#itemBtnSection>:nth-child(2)").prop("disabled", false);
        $("#itemBtnSection>:nth-child(3)").prop("disabled", false);

        $("#itemBtnSection>:nth-child(1)").removeClass("opacity-100");
        $("#itemBtnSection>:nth-child(2)").removeClass("opacity-25");
        $("#itemBtnSection>:nth-child(3)").removeClass("opacity-25");

        $("#itemBtnSection>:nth-child(1)").addClass("opacity-25");
        $("#itemBtnSection>:nth-child(2)").addClass("opacity-100");
        $("#itemBtnSection>:nth-child(3)").addClass("opacity-100");

        $("#itemCode").val(col[0].textContent);
        $("#itemDesc").val(col[1].textContent);
        $("#itemQty").val(col[2].textContent);
        $("#itemUnitPrice").val(col[3].textContent);
    }


    clearTextField() {
        $("#itemCode").val("");
        $("#itemDesc").val("");
        $("#itemQty").val("");
        $("#itemUnitPrice").val("");
        $("#manageItemSection input").css("border-color","darkgray");
    }


}

let itemController = new ItemController();