import React from 'react';
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";
import ReactDOM from "react-dom"
import { makeAutoObservable } from "mobx"
import { observer } from "mobx-react-lite"

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
  const CounterView = observer(({ counter }) => <b class="cart-size">{counter.cartSize}</b>)
  //ReactDOM.render(<CounterView counter={cartCounter} />, document.getElementById('counter'))


class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cartSize: this.props.cartSize
        }
    }

    componentDidUpdate() {
        var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
        this.setState({
            cartSize: cartItems.length
        });
    }

    render() {
        return (
            <div class="navbar navbar-light navbar-fixed-top nav">
                <div class="container">
                    <Link to="/catalog" class="navbar-brand nav-p">Shop</Link>
                    <Link to="/cart" class="navbar-brand text-right"><b id='counter'></b><img src="cart.png" class="nav-img" /></Link>
                </div>
            </div>
        );
    }
}

export default Header;//<b class="cart-size">{this.state.cartSize}</b>