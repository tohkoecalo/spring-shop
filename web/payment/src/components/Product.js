import React from 'react';
import '../css/index.css';
import CartPage from "../pages/cart";

class Product extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isAddedToCart: false
    }
  }


  componentDidMount() {
    const isAdded = localStorage.getItem(this.props.item.name) === 'true';
    this.setState({
      isAddedToCart: isAdded
    });
  }

  addToCard = (product) => {
    this.setState({
      isAddedToCart: true
    });

    localStorage.setItem(this.props.item.name, 'true');
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id: product.id, name: product.name, description: product.description, price: product.price })
    };
    fetch("http://localhost:8081/products", requestOptions)
    //    .then(() => window.location.reload(false));
  }

  render() {
    return (
      <div className="card-inline card">
        <img src="placeholder_vertical.png" className="card-img-top" alt="image"></img>
        <div className="card-body">
          <h5 className="card-title">{this.props.item.name}</h5>
          <p className="card-text">{this.props.item.description}</p>
          {!this.state.isAddedToCart && <button className="btn btn-outline-primary" onClick={() => this.addToCard(this.props.item)}>Add to cart</button>}
          {this.state.isAddedToCart && <button className="btn btn-primary" disabled>Added</button>}
          <b class="price">{this.props.item.price}$</b>
        </div>
      </div>
    );
  }
}

export default Product;