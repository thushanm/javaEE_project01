export class Customer {
    constructor(id, name, address, salary) {
        this._id = id;
        this._name = name;
        this._address = address;
        this._salary = salary;
    }

    set id(value) {
        this._id = value;
    }

    get id() {
        return this._id;
    }

    set name(value) {
        this._name = value;
    }

    get name() {
        return this._name;
    }

    set address(value) {
        this._address = value;
    }

    get address() {
        return this._address;
    }

    set salary(value) {
        this._salary = value;
    }

    get salary() {
        return this._salary;
    }
}
