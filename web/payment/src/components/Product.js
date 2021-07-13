import React from 'react';
import '../css/index.css';

class Product extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      counter: this.props.counter,
      name: this.props.item.name,
      isAddedToCart: false
    }
  }

  componentDidMount() {
    var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
    var isAdded = 'false'
    cartItems.forEach(element => {
      if (element.name === this.props.item.name) {
        isAdded = 'true';
      }
    });
    const isItemAdded = isAdded === 'true'; //cringe but works
    this.setState({
      isAddedToCart: isItemAdded
    });
  }

  addToCart = (product) => {
    this.setState({
      isAddedToCart: true
    });
    var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
    cartItems.push(product);
    localStorage.setItem('cart', JSON.stringify(cartItems));
    this.state.counter.update();
    this.state.counter.draw();
  }

  render() {
    const isAdded = this.state.isAddedToCart;
    return (
      <div className="product-item card">
        <img src="placeholder_vertical.png" className="card-img-top" alt="placeholder"></img>
        <div className="card-body">
          <h5 className="card-title">{this.props.item.name}</h5>
          <p className="card-text">{this.props.item.description}</p>
          {!isAdded && <button className="btn btn-outline-primary" onClick={() => this.addToCart(this.props.item)}>Add to cart</button>}
          {isAdded && <button className="btn btn-primary" disabled>Added</button>}
          <b className="price">{this.props.item.price}$</b>
        </div>
      </div>
    );
  }
}

export default Product;