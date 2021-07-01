import React from "react"
import ReactDOM from "react-dom"
import { makeAutoObservable } from "mobx"
import { observer } from "mobx-react-lite"

class Counter {
    cartSize = 0
  
    constructor() {
      makeAutoObservable(this)
    }
  
    update() {
      var cartItems = JSON.parse(localStorage.getItem('cart')) || [];
      this.cartSize = cartItems.length;
    }
  }
  
  //const cartCounter = new Counter()
  //const CounterView = observer(({ counter }) => <b class="cart-size">{counter.cartSize}</b>)
  //ReactDOM.render(<CounterView counter={cartCounter} />, document.getElementById('counter'))