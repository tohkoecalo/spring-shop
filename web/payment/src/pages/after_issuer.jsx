import React from "react";
import '../css/index.css';

class AfterIssuerPage extends React.Component {
    constructor(props) {
        super(props);
    }

    getStatus() {
        fetch("http://localhost:8081/order/after_issuer")
            .then(function(response) {
                return response.text();
            }) 
            .then(function(text) {
                window.location.href = "http://localhost:3000/status?status=" + text;
            })
    }

    render() {
        return (
            <>
                {this.getStatus()}  
            </>
        )
    }
}

export default AfterIssuerPage;
