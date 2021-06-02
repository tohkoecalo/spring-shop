import React from 'react';
import '../css/index.css';
import CartPage from "../pages/cart";

class Product extends React.Component {

  addToCard = (product) => {
    //
  }

  render() {
    return (
      <div className="card-inline card">
        <img src="placeholder_vertical.png" className="card-img-top" alt="image"></img>
        <div className="card-body">
          <h5 className="card-title">{this.props.item.name}</h5>
          <p className="card-text">{this.props.item.des}</p>
          <button className="btn btn-outline-primary" onClick={this.addToCard(this.props.item)}>Add to cart</button>
          <b class="price">{this.props.item.price}$</b>
        </div>
      </div>
    );
  }
}

export default Product;