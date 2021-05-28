import React from 'react';
import '../css/index.css'; 

class Product extends React.Component {
  addToCard(id){
    //alert(id)
  }

  render() {
    return (
      <div className="card-inline card">
        <img src="placeholder_vertical.png" className="card-img-top" alt="image"></img>
        <div className="card-body">
          <h5 className="card-title">{this.props.name}</h5>
          <p className="card-text">{this.props.des}</p>
          <button className="btn btn-outline-primary" onClick={this.addToCard(this.props.name)}>{this.props.btn}</button>
        </div>
      </div>
    );
  }
}

export default Product;