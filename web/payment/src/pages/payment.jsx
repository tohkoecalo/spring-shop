import React from "react";
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';

export default class PaymentPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            focus: "",
            card: {
                number: "",
                expiry: "",
                cvv: "",
                name: "Compass plus"
            },
            amount: props.location.state.amount
        };
    }

    handleInputFocus = (e) => {
        this.setState({ focus: e.target.name });
    }

    handleCardNumberChanged(event) {
        var card = this.state.card;
        card.number = event.target.value;

        this.setState({ card: card });
    }

    handleExpiryChanged(event) {
        var card = this.state.card;
        card.expiry = event.target.value;

        this.setState({ card: card });
    }

    handleCardCvvChanged(event) {
        var card = this.state.card;
        card.cvv = event.target.value;

        this.setState({ card: card });
    }

    createOrderAndCheck3ds() { //window.location.href = text
        var is3dsEnrolled = 'fasle';
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount: this.state.amount, card: this.state.card })
        };
        fetch("http://localhost:8081/order/create", requestOptions)
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                if (text == 'true') {
                    is3dsEnrolled = true;

                }
            })
        return is3dsEnrolled;
    }

    getPareq(nextUrl, md, termUrl, pareq) {//pares!!
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8' },
            body: 'MD=' + encodeURIComponent(window.btoa(md)) + '&TermUrl=' + encodeURIComponent(termUrl) + '&Pareq=' + encodeURIComponent(pareq)
        };
        fetch(nextUrl, requestOptions)
        //    .then(function(response) {
        //        return response.text();
        //    })
        //fetch processpares


    }

    runPurchase() {
        if (this.is3dsEnrolled) {
            fetch("http://localhost:8081/order/getpareq")
                .then(function (response) {
                    return response.json();
                })
                .then(function (json) {
                    var redirectUrl = "http://localhost:3000/issuer_redirect";
                    this.getPareq(json.url, json.md, redirectUrl, json.pareq);
                })
        } else {
            fetch("http://localhost:8081/order/purchase")
                .then(function (response) {
                    return response.text()
                })
        }
    }

    render() {
        const { name, number, expiry, cvc, focused, issuer, formData } = this.state;
        return (
            <>
                <h5 className="h5-order">Order amount: {this.state.amount}</h5>
                <div id="PaymentForm" className="card-preview">
                    <Cards
                        cvc={this.state.card.cvv}
                        expiry={this.state.card.expiry}
                        focused={this.state.focus}
                        name={this.state.card.name}
                        number={this.state.card.number}
                    />
                    <form>
                        <div className="form-group form-inputs">
                            <input
                                type="tel"
                                name="number"
                                className="form-control"
                                placeholder="Card Number"
                                onChange={this.handleCardNumberChanged.bind(this)}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                        <div className="row form-inputs">
                            <div className="col-6">
                                <input
                                    type="tel"
                                    name="expiry"
                                    className="form-control"
                                    placeholder="Valid Thru"
                                    pattern="\d\d/\d\d"
                                    required
                                    onChange={this.handleExpiryChanged.bind(this)}
                                    onFocus={this.handleInputFocus}
                                />
                            </div>
                            <div className="col-6">
                                <input
                                    type="tel"
                                    name="cvc"
                                    className="form-control"
                                    placeholder="CVC"
                                    pattern="\d{3,4}"
                                    required
                                    onChange={this.handleCardCvvChanged.bind(this)}
                                    onFocus={this.handleInputFocus}
                                />
                            </div>
                        </div>
                        <form>
                            <div className="row">
                                <div className="col-6">
                                    <button type="button" className="btn btn-outline-success form-control" onClick={() => this.createOrderAndCheck3ds()}>Pay</button>
                                </div>
                                <div className="col-6">
                                    <Link to="/catalog"><button type="button" class="btn btn-outline-danger form-control">Cancel</button></Link>
                                </div>
                            </div>
                        </form>
                    </form>
                </div>
            </>
        );
    }
}


/*class PaymentPage extends React.Component {
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

        this.setState({card: card });
              }

      handleCardMonthChanged(event) {
        var card = this.state.card;
                card.expMonth = event.target.value;

        this.setState({card: card });
              }

      handleCardYearChanged(event) {
        var card = this.state.card;
                card.expYear = event.target.value;

        this.setState({card: card });
              }

      handleCardCvvChanged(event) {
        var card = this.state.card;
                card.cvv = event.target.value;

        this.setState({card: card });
              }

    createOrderAndCheck3ds() { //window.location.href = text
        var is3dsEnrolled = 'fasle';
        const requestOptions = {
                    method: 'POST',
            headers: {'Content-Type': 'application/json' },
            body: JSON.stringify({amount: this.state.amount, card: this.state.card })
              };
              fetch("http://localhost:8081/order/create", requestOptions)
            .then(function(response) {
                return response.text();
            })
            .then(function(text) {
                if (text == 'true'){
                    is3dsEnrolled = true;

            }
        })
    return is3dsEnrolled;
}

    getPareq(nextUrl, md, termUrl, pareq){//pares!!
        const requestOptions = {
                    method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8' },
                body: 'MD=' + encodeURIComponent(window.btoa(md)) + '&TermUrl=' + encodeURIComponent(termUrl) + '&Pareq=' + encodeURIComponent(pareq)
            };
            fetch(nextUrl, requestOptions)
        //    .then(function(response) {
                    //        return response.text();
                    //    })
                    //fetch processpares


                }

                runPurchase(){
        if (this.is3dsEnrolled){
                    fetch("http://localhost:8081/order/getpareq")
                        .then(function (response) {
                            return response.json();
                        })
                        .then(function (json) {
                            var redirectUrl = "http://localhost:3000/issuer_redirect";
                            this.getPareq(json.url, json.md, redirectUrl, json.pareq);
                        })
                } else {
                    fetch("http://localhost:8081/order/purchase")
                        .then(function (response) {
                            return response.text()
                        })
                }
                }

    render() {
        return (
            <>
                    <br />
                    <p>Amount: {this.state.amount}</p>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Card number</span>
                        <input type="number" class="form-control" onChange={this.handleCardNumberChanged.bind(this)} />
                    </div>

                    <div class="input-group mb-3">
                        <span class="input-group-text">MM</span>
                        <input type="number" class="form-control" onChange={this.handleCardMonthChanged.bind(this)} />
                        <span class="input-group-text">YY</span>
                        <input type="number" class="form-control" onChange={this.handleCardYearChanged.bind(this)} />
                        <span class="input-group-text">CVV</span>
                        <input type="number" class="form-control" onChange={this.handleCardCvvChanged.bind(this)} />
                    </div>
                    <button type="button" class="btn btn-outline-success cart-button" onClick={() => this.createOrderAndCheck3ds()}>Pay</button>
                    <Link to="/catalog" class="navbar-brand text-right"><img src="cart.png" class="nav-img" /><button type="button" class="btn btn-outline-danger cart-button">Cancel</button></Link>
                </>
                );
            }
        }

export default PaymentPage;*/