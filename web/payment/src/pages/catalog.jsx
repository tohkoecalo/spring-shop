import React from "react";
import Product from "../components/Product";
import '../css/index.css';

class CatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            counter: this.props.counter,
            isFetching: false,
            products: []
        }
    }

    componentDidMount() {
        this.setState({ isFetching: true });
        fetch("http://localhost:8081/products")
            .then(function (response) {
                return response.json();
            })
            .then(function (json) {
                this.setState({
                    products: json,
                    isFetching: false
                });
            }.bind(this))
            .catch((error) => {
                console.log(error);
            });
    }

    renderProds = (item, index) => {
        return <Product key={index} item={item} counter={this.state.counter}/>
    }

    render() {
        if (this.state.isFetching) return <div>Loading...</div>;
        return (
            <>
                {this.state.products.map(this.renderProds)}
            </>
        )
    }
}

export default CatalogPage;
