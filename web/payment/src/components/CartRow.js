import React from 'react';
import '../css/index.css';

class Cart extends React.Component {
    render() {
        return (
            <tr>
                <td scope="col">{this.props.product}</td>
                <td scope="col">{this.props.amount}</td>
                <td scope="col">{this.props.price}</td>
            </tr>
        );
    }
}

export default Cart;