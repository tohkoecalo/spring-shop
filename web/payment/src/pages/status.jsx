import React from "react";
import '../css/index.css';
import ReactDOM from "react-dom"

class StatusPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            status: 'Wrong'
        };

    }
    componentWillMount() {
        var _this = this;
        fetch("http://localhost:8081/order/getStatus?orderId=" + localStorage.getItem('orderId'))
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                _this.state.status = text
                ReactDOM.render(<p >Order status: { text } </p>, document.getElementById("status"))
            })
    }

    render() {
        return (
            <>
                <div id="status"/>
            </>
        )
    }
}

export default StatusPage;
