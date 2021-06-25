import React from "react";
import '../css/index.css';

class StatusPage extends React.Component {
    constructor(props) {
        super(props);
    }

    getStatus(){
        const params = new URL(window.location.href).searchParams;
        if (params.has('status')){
            return params.get('status');
        }
        return 'None'
    }

    render() {
        return (
            <>
                <p>Order status: {this.getStatus()} </p>
            </>
        )
    }
}

export default StatusPage;
