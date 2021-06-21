import React from "react";
import CartRow from "../components/CartRow"
import '../css/index.css';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

class CartPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isFetching: false,
            cart: []
        }
    }

    componentDidMount() {
        this.setState({ isFetching: true });
        fetch("http://localhost:8081/cart")
            .then(function (response) {
                return response.json();
            })
            .then(function (json) {
                this.setState({
                    cart: json,
                    isFetching: false
                });
            }.bind(this))
            .catch((error) => {
                console.log(error);
            });
    }

    renderCart = (item, key) => {
        return <CartRow product={item.name} amount={1} price={1 * item.price + "$"} />
    }

    clearCart() {
        const requestOptions = {
            method: 'DELETE'
        };
        fetch("http://localhost:8081/cart", requestOptions)
            .then(() => window.location.reload(false));
    }

    render() {
        if (this.state.isFetching) return <div>Loading...</div>;
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
                <div class="cart-button">
                    <Link to={{
                        pathname: "/payment",
                        state: {
                            amount: "10"
                        }
                    }} class="navbar-brand text-right"><img src="cart.png" class="nav-img"/><button type="button" class="btn btn-outline-success cart-button">Purchase</button></Link>
                    <button type="button" class="btn btn-outline-danger cart-button" onClick={() => this.clearCart()}>Clear</button>
                </div>
            </>
        );
    }
}

export default CartPage;
