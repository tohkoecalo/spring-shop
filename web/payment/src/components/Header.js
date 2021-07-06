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

    getCartSize(){
        var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
        return  cartItems.length
    }

    render() {
        return (
            <div className="navbar navbar-light navbar-fixed-top nav">
                <div className="container">
                    <Link to="/catalog" className="navbar-brand nav-p">Shop</Link>
                    <Link to="/cart" className="navbar-brand text-right"><b className="cart-size" id="counter">{this.getCartSize()}</b><img src="cart.png" className="nav-img" alt="Cart" /></Link>
                </div>
            </div>
        );
    }
}

export default Header;