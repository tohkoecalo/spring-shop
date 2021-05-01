import React from 'react';
import './App.css'; 

class Product extends React.Component {
  render() {
    return (
      <div className="card-inline card">
        <img src="placeholder_vertical.png" className="card-img-top" alt="image"></img>
        <div className="card-body">
          <h5 className="card-title">{this.props.name}</h5>
          <p className="card-text">{this.props.des}</p>
          <a href="#" className="btn btn-outline-primary">В корзину</a>
        </div>
      </div>
    );
  }
}

export default Product;
