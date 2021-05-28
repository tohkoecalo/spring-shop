import React from "react";
import {Link} from "react-router-dom"

const CardPage = () =>{
    return (
        <div>
            <p>Card page</p>
            <Link to="/catalog">Go to catalog</Link>
        </div>
    );
}

export default CardPage;
