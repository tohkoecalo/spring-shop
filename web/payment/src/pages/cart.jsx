import React from "react";
import CartRow from "../components/CartRow"
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

class CartPage extends React.Component {
    constructor(props) {
        super(props);
        var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
        this.state = {
            cart: []
        }
    }

    componentDidMount() {
        var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
        this.setState({
            cart: cartItems
        });
    }

    renderCart = (item, key) => {
        return <CartRow product={item.name} amount={1} price={1 * item.price + "₽"} />
    }

    clearCart() {
        localStorage.setItem('cart', JSON.stringify([]));
        window.location.reload(false);
    }

    getCartAmount() {
        var total = 0;
        for (var i = 0; i < this.state.cart.length; i++) {
            total += this.state.cart[i].price
        }
        return total
    }

    render() {
        var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
        if (cartItems.length == 0) {
            return(<h5 className="h5-order">Your cart is empty</h5>);
        } else {
            return (
                <>
                    
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Product</th>
                                <th scope="col">Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.cart.map(this.renderCart)}
                        </tbody>
                    </table>
                    <form>
                        <div className="row">
                            <div className="col-6">
                                <Link to={{
                                    pathname: "/payment",
                                    state: {
                                        amount: this.getCartAmount()
                                    }
                                }}><button type="button" className="btn btn-outline-success form-control">Purchase</button></Link>
                            </div>
                            <div className="col-6">
                                <button type="button" className="btn btn-outline-danger form-control" onClick={() => this.clearCart()}>Clear</button>
                            </div>
                        </div>
                    </form>
                </>
            );
        }
    }
}

export default CartPage;
