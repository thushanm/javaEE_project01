
export class Order{
    constructor(orderID,date,time,customerID,discount,totalPrice) {

        this._orderID = orderID;
        this._date = date;
        this._time = time;
        this._customerID = customerID;
        this._discount = discount;
        this._totalPrice = totalPrice;
    }
    get discount() {
        return this._discount;
    }

    set discount(value) {
        this._discount = value;
    }

    get totalPrice() {
        return this._totalPrice;
    }

    set totalPrice(value) {
        this._totalPrice = value;
    }
    get orderID() {
        return this._orderID;
    }

    set orderID(value) {
        this._orderID = value;
    }

    get date() {
        return this._date;
    }

    set date(value) {
        this._date = value;
    }

    get time() {
        return this._time;
    }

    set time(value) {
        this._time = value;
    }

    get customerID() {
        return this._customerID;
    }

    set customerID(value) {
        this._customerID = value;
    }


}