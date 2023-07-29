export class OrderDetail{
    constructor(orderID,itemCode,description,qty,unitPrice) {
        this._orderID = orderID;
        this._itemCode = itemCode;
        this._description = description;
        this._qty = qty;
        this._unitPrice = unitPrice;
    }
    get orderID() {
        return this._orderID;
    }

    set orderID(value) {
        this._orderID = value;
    }

    get itemCode() {
        return this._itemCode;
    }

    set itemCode(value) {
        this._itemCode = value;
    }

    get description() {
        return this._description;
    }

    set description(value) {
        this._description = value;
    }

    get qty() {
        return this._qty;
    }

    set qty(value) {
        this._qty = value;
    }

    get unitPrice() {
        return this._unitPrice;
    }

    set unitPrice(value) {
        this._unitPrice = value;
    }



}