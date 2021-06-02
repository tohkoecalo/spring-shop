import React from "react";
import Product from "../components/Product";
import '../css/index.css';

class CatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            products: []
        }
    }

    componentDidMount() {
        fetch("http://localhost:8081/products")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        products: result.items
                    });
                },
                (error) => {
                    this.setState({
                        products: []
                    });
                }
            )
    }

    initProds() {
        for (var i = 0; i < this.state.products.length; i++) {
            this.state.products[i].name = "Product #" + (i + 1);
            this.state.products[i].des = "Description of Product #" + (i + 1);
        }
    }

    renderProds = (item, index) => {
        return <Product item={item} />
    }

    render() {
        return this.state.products.map(this.renderProds);
    }
}

export default CatalogPage;
