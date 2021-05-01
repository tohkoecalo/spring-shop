import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css'; 
import Product from './Product'


class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      arr: [
        {
          name: "Product",
          des: "des"
        },
        {
          name: "Product",
          des: "des"
        },
        {
          name: "Product",
          des: "des"
        },
        {
          name: "Product",
          des: "des"
        }
      ]
    }
  }
  
  renderArr = (item, index) => {
    return <Product name={item.name + " #" + index} des={item.des}/>
  }

  render() {
    return this.state.arr.map(this.renderArr);
  }
}

export default App;
