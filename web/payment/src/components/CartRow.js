import React from 'react';
import '../css/index.css';

class CartRow extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.product}</td>
                <td>{this.props.price}</td>
            </tr>
        );
    }
}

export default CartRow;