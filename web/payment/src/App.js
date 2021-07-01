import logo from './logo.svg';
import './App.css';
import './css/index.css';
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

import CartPage from "./pages/cart";
import CatalogPage from "./pages/catalog";
import PaymentPage from "./pages/payment";
import AfterIssuerPage from "./pages/after_issuer";
import OrderStatus from "./pages/status";
import Header from "./components/Header"

class App extends React.Component {
  constructor(props) {
    super(props);
    var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
    this.state = {
      cartSize: cartItems.length
    }
  }

  render() {
    return (
      <>
        <head>
          <meta charset="utf-8" />
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous" />
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <title>React App</title>
        </head>
        <body>
          <Router>
          <Header cartSize={this.state.cartSize} />
            <div class="container">
              <Switch>
                <Route path="/cart" component={CartPage}></Route>
                <Route path="/catalog" component={CatalogPage}></Route>
                <Route path="/issuer_redirect" component={AfterIssuerPage}></Route>
                <Route path="/status" component={OrderStatus}></Route>
                <Route name="payment" component={PaymentPage}></Route>
                <Redirect to="/catalog"></Redirect>
              </Switch>
            </div>
          </Router>
        </body>
      </>
    )
  };
}

export default App;
