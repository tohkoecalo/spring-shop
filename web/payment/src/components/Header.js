import React from 'react';
import '../css/index.css';
import { Link } from "react-router-dom";

class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            counter: this.props.counter
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
            <div className="navbar navbar-light navbar-fixed-top nav">
                <div className="container">
                    <Link to="/catalog" className="navbar-brand nav-p">Shop</Link>
                    <Link to="/cart" className="navbar-brand text-right"><div id="counter"></div><img src="cart.png" className="nav-img" alt="Cart" /></Link>
                </div>
            </div>
        );
    }
}

export default Header;//<b id='counter'></b> <b class="cart-size" id='counter'>{this.state.counter.cartSize}</b>