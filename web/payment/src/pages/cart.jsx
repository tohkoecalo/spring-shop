import React from "react";
import CartRow from "../components/CartRow"

class CartPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            arr: [
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

    renderCard = (item, key) => {
        return <CartRow product={item.product} amount={item.amount} price={item.amount * item.price + "$"} />
    }

    render() {
        return (
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Product</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Price</th>
                    </tr>
                </thead>
                <tbody>
                    {this.state.arr.map(this.renderCard)}
                </tbody>
            </table>
        );
    }
}

export default CartPage;
