import React from 'react';
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

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
                    <Link to="/cart" class="navbar-brand text-right"><b class="cart-size">{this.state.cartSize}</b><img src="cart.png" class="nav-img" /></Link>
                </div>
            </div>
        );
    }
}

export default Header;