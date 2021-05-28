import logo from './logo.svg';
import './App.css';
import './css/index.css';
import React from 'react';
import { BrowserRouter as Router, Route, Switch, Link, Redirect } from "react-router-dom";

import CardPage from "./pages/card";
import CatalogPage from "./pages/catalog";

class App extends React.Component {
  render() {
    return (
      <>
        <head>
          <meta charset="utf-8" />
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous" />
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <title>React App</title>
        </head>
        <body>
          <Router>
            <div class="navbar navbar-light navbar-fixed-top nav">
              <div class="container">
                <Link to="/catalog">Shop</Link> {/* need style */}
                <Link to="/card" class="text-right nav-p">Card</Link>
              </div>
            </div>
            <div class="container">
              <Switch>
                <Route path="/card" component={CardPage}></Route>
                <Route path="/catalog" component={CatalogPage}></Route>
              </Switch>
            </div>
          </Router>
        </body>
      </>
    )
  };
}

export default App;
