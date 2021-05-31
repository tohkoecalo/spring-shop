import React from "react";
import Product from "../components/Product";
import '../css/index.css';

class CatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [
                {
                    name: "",
                    des: "",
                    price: 1.99
                },
                {
                    name: "",
                    des: "",
                    price: 1.19
                },
                {
                    name: "",
                    des: "",
                    price: 2.29
                },
                {
                    name: "",
                    des: "",
                    price: 0.69
                }
            ]
        }
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
        this.initProds();
        return this.state.products.map(this.renderProds);
    }
}

export default CatalogPage;
