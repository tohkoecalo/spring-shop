import React from "react";
import CartRow from "../components/CartRow"
import '../css/index.css';

class CartPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cart: [
                {
                    product: "test1",
                    amount: "1",
                    price: 1.99
                },
                {
                    product: "test2",
                    amount: "2",
                    price: 0.69
                }
            ]
        }
    }

    addNewItem = () => {
        let { cart, input } = this.state;
        cart.push(input);
      };

    renderCard = (item, key) => {
        return <CartRow product={item.product} amount={item.amount} price={item.amount * item.price + "$"} />
    }

    render() {
        return (
            <>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Product</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.cart.map(this.renderCard)}
                    </tbody>
                </table>
                <div class="cart-button">
                    <button type="button" class="btn btn-outline-success cart-button">Purchase</button>
                    <button type="button" class="btn btn-outline-danger cart-button">Clear</button>
                </div>
            </>
        );
    }
}

export default CartPage;
