import React from "react";
import CartRow from "../components/CartRow"
import '../css/index.css';

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

    renderCard = (item, key) => {
        return <CartRow product={item.name} amount={1} price={1 * item.price + "$"} />
    }

    clearCart() {
        const requestOptions = {
            method: 'DELETE'
        };
        fetch("http://localhost:8081/cart", requestOptions);
        //window.location.reload(false);
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
                        {this.state.cart.map(this.renderCard)}
                    </tbody>
                </table>
                <div class="cart-button">
                    <button type="button" class="btn btn-outline-success cart-button">Purchase</button>
                    <button type="button" class="btn btn-outline-danger cart-button" onClick={() => this.clearCart()}>Clear</button>
                </div>
            </>
        );
    }
}

export default CartPage;
