import React from "react";
import Product from "../components/Product";
import '../css/index.css';

class CatalogPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            arr: [
                {
                    name: "",
                    des: ""
                },
                {
                    name: "",
                    des: ""
                },
                {
                    name: "",
                    des: ""
                },
                {
                    name: "",
                    des: ""
                }
            ]
        }
    }

    renderArr = (item, index) => {
        return <Product name={"Product #" + ++index} des={"Description of Product #" + index} btn={"Add to card"} />
    }

    render() {
        return this.state.arr.map(this.renderArr);
    }
}

export default CatalogPage;
