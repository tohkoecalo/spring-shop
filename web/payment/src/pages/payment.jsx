import React from "react";
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

class PaymentPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            card: {
                number : "",
                expMonth: "",
                expYear: "",
                cvv: ""
            },
            amount: props.location.state.amount
        }
    }

    handleCardNumberChanged(event) {
        var card = this.state.card;
        card.number = event.target.value;
    
        this.setState({ card: card });
      }

      handleCardMonthChanged(event) {
        var card = this.state.card;
        card.expMonth = event.target.value;
    
        this.setState({ card: card });
      }

      handleCardYearChanged(event) {
        var card = this.state.card;
        card.expYear = event.target.value;
    
        this.setState({ card: card });
      }

      handleCardCvvChanged(event) {
        var card = this.state.card;
        card.cvv = event.target.value;
    
        this.setState({ card: card });
      }

    sendCardInfo() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount: this.state.amount, card: this.state.card })/* card: {number: "1", expMonth: "12", expYear: "123", cvv: "1233"} */
          };
          fetch("http://localhost:8081/order/create", requestOptions)
    }

    render() {
        return (
            <>
                <br/>
                <p>Amount: {this.state.amount}</p>
                <div class="input-group mb-3">
                    <span class="input-group-text">Card number</span>
                    <input type="number" class="form-control" onChange={this.handleCardNumberChanged.bind(this)}/>
                </div>

                <div class="input-group mb-3">
                    <span class="input-group-text">MM</span>
                    <input type="number" class="form-control" onChange={this.handleCardMonthChanged.bind(this)}/>
                    <span class="input-group-text">YY</span>
                    <input type="number" class="form-control" onChange={this.handleCardYearChanged.bind(this)}/>
                    <span class="input-group-text">CVV</span>
                    <input type="number" class="form-control" onChange={this.handleCardCvvChanged.bind(this)}/>
                </div>
                <button type="button" class="btn btn-outline-success cart-button" onClick={() => this.sendCardInfo()}>Pay</button>
                <Link to="/catalog" class="navbar-brand text-right"><img src="cart.png" class="nav-img"/><button type="button" class="btn btn-outline-danger cart-button">Cancel</button></Link>
            </>
        );
    }
}

export default PaymentPage;