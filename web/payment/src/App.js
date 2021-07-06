import './App.css';
import './css/index.css';
import React from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";

import CartPage from "./pages/cart";
import CatalogPage from "./pages/catalog";
import PaymentPage from "./pages/payment";
import AfterIssuerPage from "./pages/after_issuer";
import OrderStatus from "./pages/status";
import ErrorPage from "./pages/error";
import Header from "./components/Header"
import ReactDOM from "react-dom"
import { observer } from "mobx-react-lite"
import { makeAutoObservable } from "mobx"

class Counter {
  cartSize = 0

  constructor() {
    makeAutoObservable(this)
  }

  update() {
    var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
    this.cartSize = cartItems.length;
  }
}

const cartCounter = new Counter()
const CounterView = observer(({ counter }) => <b className="cart-size">{counter.cartSize}</b>)
ReactDOM.render(<CounterView counter={cartCounter} />, document.getElementById("counter"))

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
        <Router>
          <Header counter={cartCounter} />
          <div className="container">
            <Switch>
              <Route
                path="/cart"
                render={() => (
                  <CartPage counter={cartCounter} />
                )}
              ></Route>
              <Route
                path="/catalog"
                render={() => (
                  <CatalogPage counter={cartCounter} />
                )}
              ></Route>
              <Route path="/issuer_redirect" component={AfterIssuerPage}></Route>
              <Route path="/status" component={OrderStatus}></Route>
              <Route path="/error" component={ErrorPage}></Route>
              <Route name="payment" component={PaymentPage}></Route>
              <Redirect to="/catalog"></Redirect>
            </Switch>
          </div>
        </Router>
      </>
    )
  };
}

export default App;