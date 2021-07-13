import React from "react";
import '../css/index.css';
import { Link } from "react-router-dom";
import Cards from 'react-credit-cards';
import 'react-credit-cards/es/styles-compiled.css';
import ReactDOM from "react-dom"

export default class PaymentPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            focus: "",
            card: {
                number: "",
                expiry: "",
                cvv: "",
                name: "Compass plus",
                is3dsEnrolled: false
            },
            amount: props.location.state.amount || 0
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
        card.expiry = card.expiry.replace('/', '');
        if (card.expiry.length >= 3) {
            card.expiry = `${card.expiry.slice(0, 2)}/${card.expiry.slice(2, 4)}`;
        }

        this.setState({ card: card });
        document.getElementById('expiry').value = card.expiry
    }

    handleCardCvvChanged(event) {
        var card = this.state.card;
        card.cvv = event.target.value;

        this.setState({ card: card });
    }

    prepareCardForOrder() {
        var card = this.state.card;
        card.expiry = card.expiry.replace('/', '');
        card.expiry = `${card.expiry.slice(2, 4)}${card.expiry.slice(0, 2)}`;
        this.setState({ card: card });
    }

    createOrder() {
        return new Promise((resolve, reject) => {
            this.prepareCardForOrder();
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ amount: this.state.amount * 100, card: this.state.card })
            };
            fetch("http://localhost:8081/order/create/", requestOptions)
                .then(function (response) {
                    if (response.status === 200) {
                        return response.text();
                    } else {
                        window.location.href = "http://localhost:8081/error"
                    }
                })
                .then(function (text) {
                    localStorage.setItem('orderId', text);
                    localStorage.setItem('cart', JSON.stringify([]));
                })
                .then((value) => resolve(value))
        })
    }

    check3ds() {
        return new Promise((resolve, reject) => {
            var cardFor3ds = this.state.card;
            var _this = this;
            fetch("http://localhost:8081/order/check3ds?orderId=" + localStorage.getItem('orderId'))
                .then(function (response) {
                    if (response.status === 200) {
                        return response.text();
                    } else {
                        window.location.href = "http://localhost:8081/error"
                    }
                })
                .then(function (text) {
                    if (text === 'true') {
                        cardFor3ds.is3dsEnrolled = true;
                        _this.state.card.is3dsEnrolled = true;
                    }
                })
                .then((value) => resolve(value))
        })
    }

    getPareq(nextUrl, termUrl, pareq) {//send pares to server 
        let form = document.createElement('form');
        form.action = nextUrl;
        form.method = "POST";
        form.innerHTML = "<input name=\"MD\" value=\"" + window.btoa(localStorage.getItem('orderId')) + "\" hidden>" + 
            "<input name=\"TermUrl\" value=\""+ termUrl + "\" hidden>" + 
            "<input name=\"PaReq\" value=\""+ pareq + "\" hidden>";
        document.body.append(form);
        form.submit();
    }

    runPurchase() {
        var _this = this;
        this.createOrder()
            .then(function () {
                _this.check3ds()
                    .then(function () {
                        if (_this.state.card.is3dsEnrolled) {
                            fetch("http://localhost:8081/order/getpareq?orderId=" + localStorage.getItem('orderId'))
                                .then(function (response) {
                                    return response.json();
                                })
                                .then(function (json) {
                                    var redirectUrl = "http://localhost:8081/order/after_issuer";
                                    _this.getPareq(json.url, redirectUrl, json.pareq);
                                })
                        } else {
                            const requestOptions = {
                                method: 'POST'
                            };
                            fetch("http://localhost:8081/order/purchase?orderId=" + localStorage.getItem('orderId'), requestOptions)
                                .then(function (response) {
                                    return response.text()
                                })
                        }
                    });
            })
    }

    render() {
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
                    <form ref={c => (this.form = c)}>
                        <div className="form-group form-inputs">
                            <input
                                type="tel"
                                name="number"
                                className="form-control"
                                placeholder="Card Number"
                                maxLength="19"
                                onChange={this.handleCardNumberChanged.bind(this)}
                                onFocus={this.handleInputFocus}
                            />
                        </div>
                        <div className="row form-inputs">
                            <div className="col-6">
                                <input
                                    id="expiry"
                                    type="tel"
                                    name="expiry"
                                    className="form-control"
                                    placeholder="Valid Thru"
                                    maxLength="5"
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
                                    placeholder="CVV"
                                    maxLength="4"
                                    required
                                    onChange={this.handleCardCvvChanged.bind(this)}
                                    onFocus={this.handleInputFocus}
                                />
                            </div>
                        </div>
                        <div>
                            <div className="row">
                                <div className="col-6">
                                    <button type="button" className="btn btn-outline-success form-control" onClick={() => this.runPurchase()}>Pay</button>
                                </div>
                                <div className="col-6">
                                    <Link to="/catalog"><button type="button" className="btn btn-outline-danger form-control">Cancel</button></Link>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </>
        );
    }
}