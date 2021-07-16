import React from "react";
import '../css/index.css';

class ErrorPage extends React.Component {
    getMessage(){
        const params = new URL(window.location.href).searchParams;
        if (params.has('message')){
            return params.get('message')
        }
        return "Unknown internal error"
    }

    render() {
        return (
            <>
                <p>{ this.getMessage()} </p>
            </>
        )
    }
}

export default ErrorPage;
