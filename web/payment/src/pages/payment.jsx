import React from "react";
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

class PaymentPage extends React.Component {
    constructor(props) {
        super(props);
    }

    sendCardInfo() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount: "100", card: {number: "1", expMonth: "12", expYear: "123", cvv: "1233"} })
          };
          fetch("http://localhost:8081/order/create", requestOptions)
    }

    render() {
        return (
            <>
                <br/>
                <div class="input-group mb-3">
                    <span class="input-group-text">Card number</span>
                    <input type="text" class="form-control"/>
                </div>

                <div class="input-group mb-3">
                    <span class="input-group-text">MM</span>
                    <input type="text" class="form-control"/>
                    <span class="input-group-text">YY</span>
                    <input type="text" class="form-control"/>
                    <span class="input-group-text">CVV</span>
                    <input type="text" class="form-control"/>
                </div>
                <button type="button" class="btn btn-outline-success cart-button" onClick={() => this.sendCardInfo()}>Pay</button>
                <Link to="/catalog" class="navbar-brand text-right"><img src="cart.png" class="nav-img"/><button type="button" class="btn btn-outline-danger cart-button">Cancel</button></Link>
            </>
        );
    }
}

export default PaymentPage;