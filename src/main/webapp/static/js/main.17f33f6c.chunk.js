(this.webpackJsonppayment=this.webpackJsonppayment||[]).push([[0],{13:function(t,e,r){},31:function(t,e,r){},32:function(t,e,r){},42:function(t,e,r){"use strict";r.r(e);var n=r(1),a=r.n(n),c=r(14),s=r.n(c),o=(r(31),r(6)),i=r(5),u=r(3),l=r(4),d=(r(32),r(13),r(15)),h=r(7),j=r(0),b=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(){return Object(u.a)(this,r),e.apply(this,arguments)}return Object(l.a)(r,[{key:"render",value:function(){return Object(j.jsxs)("tr",{children:[Object(j.jsx)("td",{children:this.props.product}),Object(j.jsx)("td",{children:this.props.price})]})}}]),r}(a.a.Component),p=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).renderCart=function(t,e){return Object(j.jsx)(b,{product:t.name,amount:1,price:1*t.price+"$"},e)},n.state={counter:n.props.counter,cart:[]},n}return Object(l.a)(r,[{key:"componentDidMount",value:function(){var t=JSON.parse(localStorage.getItem("cart"))||[];this.setState({cart:t})}},{key:"clearCart",value:function(){localStorage.setItem("cart",JSON.stringify([])),this.state.counter.update(),this.state.counter.draw(),window.location.reload(!1)}},{key:"getCartAmount",value:function(){for(var t=0,e=0;e<this.state.cart.length;e++)t+=this.state.cart[e].price;return t}},{key:"render",value:function(){var t=this;return 0===(JSON.parse(localStorage.getItem("cart"))||[]).length?Object(j.jsx)("h5",{className:"h5-order",children:"Your cart is empty"}):Object(j.jsxs)(j.Fragment,{children:[Object(j.jsxs)("table",{className:"table table-striped",children:[Object(j.jsx)("thead",{children:Object(j.jsxs)("tr",{children:[Object(j.jsx)("th",{scope:"col",children:"Product"}),Object(j.jsx)("th",{scope:"col",children:"Price"})]})}),Object(j.jsx)("tbody",{children:this.state.cart.map(this.renderCart)})]}),Object(j.jsx)("form",{children:Object(j.jsxs)("div",{className:"row",children:[Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)(d.b,{to:{pathname:"/payment",state:{amount:this.getCartAmount()}},children:Object(j.jsx)("button",{type:"button",className:"btn btn-outline-success form-control",children:"Purchase"})})}),Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)("button",{type:"button",className:"btn btn-outline-danger form-control",onClick:function(){return t.clearCart()},children:"Clear"})})]})})]})}}]),r}(a.a.Component),m=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).addToCart=function(t){n.setState({isAddedToCart:!0});var e=JSON.parse(localStorage.getItem("cart"))||[];e.push(t),localStorage.setItem("cart",JSON.stringify(e)),n.state.counter.update(),n.state.counter.draw()},n.state={counter:n.props.counter,name:n.props.item.name,isAddedToCart:!1},n}return Object(l.a)(r,[{key:"componentDidMount",value:function(){var t=this,e=JSON.parse(localStorage.getItem("cart"))||[],r="false";e.forEach((function(e){e.name===t.props.item.name&&(r="true")}));var n="true"===r;this.setState({isAddedToCart:n})}},{key:"render",value:function(){var t=this,e=this.state.isAddedToCart;return Object(j.jsxs)("div",{className:"product-item card",children:[Object(j.jsx)("img",{src:"placeholder_vertical.png",className:"card-img-top",alt:"placeholder"}),Object(j.jsxs)("div",{className:"card-body",children:[Object(j.jsx)("h5",{className:"card-title",children:this.props.item.name}),Object(j.jsx)("p",{className:"card-text",children:this.props.item.description}),!e&&Object(j.jsx)("button",{className:"btn btn-outline-primary",onClick:function(){return t.addToCart(t.props.item)},children:"Add to cart"}),e&&Object(j.jsx)("button",{className:"btn btn-primary",disabled:!0,children:"Added"}),Object(j.jsxs)("b",{className:"price",children:[this.props.item.price,"$"]})]})]})}}]),r}(a.a.Component),O=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).renderProds=function(t,e){return Object(j.jsx)(m,{item:t,counter:n.state.counter},e)},n.state={counter:n.props.counter,isFetching:!1,products:[]},n}return Object(l.a)(r,[{key:"componentDidMount",value:function(){this.setState({isFetching:!0}),fetch("/products").then((function(t){return t.json()})).then(function(t){this.setState({products:t,isFetching:!1})}.bind(this)).catch((function(t){console.log(t)}))}},{key:"render",value:function(){return this.state.isFetching?Object(j.jsx)("div",{children:"Loading..."}):Object(j.jsx)("div",{className:"products",children:Object(j.jsx)("div",{className:"product-group",children:this.state.products.map(this.renderProds)})})}}]),r}(a.a.Component),f=r(25),v=(r(41),function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).handleInputFocus=function(t){n.setState({focus:t.target.name})},n.state={focus:"",card:{number:"",expiry:"",cvv:"",name:"Compass plus",is3dsEnrolled:!1},amount:t.location.state.amount||0},n}return Object(l.a)(r,[{key:"handleCardNumberChanged",value:function(t){var e=this.state.card;e.number=t.target.value,this.setState({card:e})}},{key:"handleExpiryChanged",value:function(t){var e=this.state.card;e.expiry=t.target.value,e.expiry=e.expiry.replace("/",""),e.expiry.length>=3&&(e.expiry="".concat(e.expiry.slice(0,2),"/").concat(e.expiry.slice(2,4))),this.setState({card:e}),document.getElementById("expiry").value=e.expiry}},{key:"handleCardCvvChanged",value:function(t){var e=this.state.card;e.cvv=t.target.value,this.setState({card:e})}},{key:"prepareCardForOrder",value:function(){var t=this.state.card;t.expiry=t.expiry.replace("/",""),t.expiry="".concat(t.expiry.slice(2,4)).concat(t.expiry.slice(0,2)),this.setState({card:t})}},{key:"createOrder",value:function(){var t=this;return new Promise((function(e,r){t.prepareCardForOrder();var n={method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({amount:100*t.state.amount,card:t.state.card})};fetch("http://localhost:8081/order/create/",n).then((function(t){if(200===t.status)return t.text();window.location.href="http://localhost:8081/error?message=Error while creating your order"})).then((function(t){localStorage.setItem("orderId",t),localStorage.setItem("cart",JSON.stringify([]))})).then((function(t){return e(t)}))}))}},{key:"check3ds",value:function(){var t=this;return new Promise((function(e,r){var n=t.state.card,a=t;fetch("http://localhost:8081/order/check3ds?orderId="+localStorage.getItem("orderId")).then((function(t){if(200===t.status)return t.text();window.location.href="http://localhost:8081/error?message=Error while checking 3DSecure of your card"})).then((function(t){"true"===t&&(n.is3dsEnrolled=!0,a.state.card.is3dsEnrolled=!0)})).then((function(t){return e(t)}))}))}},{key:"getPareq",value:function(t,e,r){var n=document.createElement("form");n.action=t,n.method="POST",n.innerHTML='<input name="MD" value="'+window.btoa(localStorage.getItem("orderId"))+'" hidden><input name="TermUrl" value="'+e+'" hidden><input name="PaReq" value="'+r+'" hidden>',document.body.append(n),n.submit()}},{key:"runPurchase",value:function(){var t=this;this.createOrder().then((function(){t.check3ds().then((function(){if(t.state.card.is3dsEnrolled)fetch("http://localhost:8081/order/getpareq?orderId="+localStorage.getItem("orderId")).then((function(t){if(200===t.status)return t.json();window.location.href="http://localhost:8081/error?message=Error while processing order"})).then((function(e){t.getPareq(e.url,"http://localhost:8081/order/after_issuer",e.pareq)}));else{fetch("http://localhost:8081/order/purchase?orderId="+localStorage.getItem("orderId"),{method:"POST"}).then((function(t){if(200===t.status)return t.json();window.location.href="http://localhost:8081/error?message=Error while purchasing"}))}}))}))}},{key:"render",value:function(){var t=this;return Object(j.jsxs)(j.Fragment,{children:[Object(j.jsxs)("h5",{className:"h5-order",children:["Order amount: ",this.state.amount]}),Object(j.jsxs)("div",{id:"PaymentForm",className:"card-preview",children:[Object(j.jsx)(f.a,{cvc:this.state.card.cvv,expiry:this.state.card.expiry,focused:this.state.focus,name:this.state.card.name,number:this.state.card.number}),Object(j.jsxs)("form",{ref:function(e){return t.form=e},children:[Object(j.jsx)("div",{className:"form-group form-inputs",children:Object(j.jsx)("input",{type:"tel",name:"number",className:"form-control",placeholder:"Card Number",maxLength:"19",onChange:this.handleCardNumberChanged.bind(this),onFocus:this.handleInputFocus})}),Object(j.jsxs)("div",{className:"row form-inputs",children:[Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)("input",{id:"expiry",type:"tel",name:"expiry",className:"form-control",placeholder:"Valid Thru",maxLength:"5",required:!0,onChange:this.handleExpiryChanged.bind(this),onFocus:this.handleInputFocus})}),Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)("input",{type:"tel",name:"cvc",className:"form-control",placeholder:"CVV",maxLength:"4",required:!0,onChange:this.handleCardCvvChanged.bind(this),onFocus:this.handleInputFocus})})]}),Object(j.jsx)("div",{children:Object(j.jsxs)("div",{className:"row",children:[Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)("button",{type:"button",className:"btn btn-outline-success form-control",onClick:function(){return t.runPurchase()},children:"Pay"})}),Object(j.jsx)("div",{className:"col-6",children:Object(j.jsx)(d.b,{to:"/catalog",children:Object(j.jsx)("button",{type:"button",className:"btn btn-outline-danger form-control",children:"Cancel"})})})]})})]})]})]})}}]),r}(a.a.Component)),g=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(){return Object(u.a)(this,r),e.apply(this,arguments)}return Object(l.a)(r,[{key:"getStatus",value:function(){fetch("/order/after_issuer?orderId="+localStorage.getItem("orderId")).then((function(t){return t.text()})).then((function(t){window.location.href="http://localhost:8081/status?status="+t}))}},{key:"render",value:function(){return Object(j.jsx)(j.Fragment,{children:this.getStatus()})}}]),r}(a.a.Component),x=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).state={status:"Wrong"},n}return Object(l.a)(r,[{key:"componentWillMount",value:function(){var t=this;fetch("http://localhost:8081/order/getStatus?orderId="+localStorage.getItem("orderId")).then((function(t){return t.text()})).then((function(e){t.state.status=e,s.a.render(Object(j.jsxs)("p",{children:["Order status: ",e," "]}),document.getElementById("status"))}))}},{key:"render",value:function(){return Object(j.jsx)(j.Fragment,{children:Object(j.jsx)("div",{id:"status"})})}}]),r}(a.a.Component),y=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(){return Object(u.a)(this,r),e.apply(this,arguments)}return Object(l.a)(r,[{key:"getMessage",value:function(){var t=new URL(window.location.href).searchParams;return t.has("message")?t.get("message"):"Unknown internal error"}},{key:"render",value:function(){return Object(j.jsx)(j.Fragment,{children:Object(j.jsxs)("p",{children:[this.getMessage()," "]})})}}]),r}(a.a.Component),S=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(){return Object(u.a)(this,r),e.apply(this,arguments)}return Object(l.a)(r,[{key:"render",value:function(){return Object(j.jsx)(j.Fragment,{children:Object(j.jsx)("div",{id:"form"})})}}]),r}(a.a.Component),C=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;return Object(u.a)(this,r),(n=e.call(this,t)).state={counter:n.props.counter},n}return Object(l.a)(r,[{key:"componentDidUpdate",value:function(){var t=JSON.parse(localStorage.getItem("cart"))||[];this.setState({cartSize:t.length})}},{key:"getCartSize",value:function(){return(JSON.parse(localStorage.getItem("cart"))||[]).length}},{key:"render",value:function(){return Object(j.jsx)("div",{className:"navbar navbar-light navbar-fixed-top nav",children:Object(j.jsxs)("div",{className:"container",children:[Object(j.jsx)(d.b,{to:"/catalog",className:"navbar-brand nav-p",children:"Shop"}),Object(j.jsxs)(d.b,{to:"/cart",className:"navbar-brand text-right",children:[Object(j.jsx)("b",{className:"cart-size",id:"counter",children:this.getCartSize()}),Object(j.jsx)("img",{src:"cart.png",className:"nav-img",alt:"Cart"})]})]})})}}]),r}(a.a.Component),N=r(26),k=r(9),I=new(function(){function t(){Object(u.a)(this,t),this.cartSize=0,Object(k.d)(this)}return Object(l.a)(t,[{key:"update",value:function(){var t=JSON.parse(localStorage.getItem("cart"))||[];this.cartSize=t.length}},{key:"draw",value:function(){s.a.render(Object(j.jsx)(w,{counter:this}),document.getElementById("counter"))}}]),t}()),w=Object(N.a)((function(t){var e=t.counter;return Object(j.jsx)("b",{children:e.cartSize})})),F=function(t){Object(o.a)(r,t);var e=Object(i.a)(r);function r(t){var n;Object(u.a)(this,r),n=e.call(this,t);var a=JSON.parse(localStorage.getItem("cart"))||[];return n.state={cartSize:a.length},n}return Object(l.a)(r,[{key:"render",value:function(){return Object(j.jsx)(j.Fragment,{children:Object(j.jsxs)(d.a,{children:[Object(j.jsx)(C,{counter:I}),Object(j.jsx)("div",{className:"container",children:Object(j.jsxs)(h.d,{children:[Object(j.jsx)(h.a,{exact:!0,from:"/",to:"/catalog"}),Object(j.jsx)(h.b,{path:"/cart",render:function(){return Object(j.jsx)(p,{counter:I})}}),Object(j.jsx)(h.b,{path:"/catalog",render:function(){return Object(j.jsx)(O,{counter:I})}}),Object(j.jsx)(h.b,{path:"/order/after_issuer",component:g}),Object(j.jsx)(h.b,{path:"/status",component:x}),Object(j.jsx)(h.b,{path:"/form",component:S}),Object(j.jsx)(h.b,{path:"/error",component:y}),Object(j.jsx)(h.b,{name:"payment",component:v}),Object(j.jsx)(h.a,{to:"/catalog"})]})})]})})}}]),r}(a.a.Component),P=function(t){t&&t instanceof Function&&r.e(3).then(r.bind(null,43)).then((function(e){var r=e.getCLS,n=e.getFID,a=e.getFCP,c=e.getLCP,s=e.getTTFB;r(t),n(t),a(t),c(t),s(t)}))};s.a.render(Object(j.jsx)(a.a.StrictMode,{children:Object(j.jsx)(F,{})}),document.getElementById("root")),P()}},[[42,1,2]]]);
//# sourceMappingURL=main.17f33f6c.chunk.js.map