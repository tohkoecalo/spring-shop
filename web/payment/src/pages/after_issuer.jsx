import React from "react";
import '../css/index.css';

class AfterIssuerPage extends React.Component {
    getStatus() {
        fetch("http://localhost:8081/order/after_issuer?orderId="  + localStorage.getItem('orderId'))
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
