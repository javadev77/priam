webpackJsonp([1],[,,,,,,,,,,,,,function(t,n,s){"use strict";var e=s(62),a=s.n(e),o=s(63),i=s.n(o),r=s(66),c=s.n(r);s.d(n,"a",function(){return u});var u=[{path:"/",component:a.a},{path:"/portfolio",component:i.a},{path:"/stocks",component:c.a}]},function(t,n,s){"use strict";var e=s(2),a=s(1),o=s(29),i=s(28),r=s(27);e.a.use(a.b),n.a=new a.b.Store({actions:r,modules:{stocks:o.a,portfolio:i.a}})},function(t,n,s){s(60);var e=s(0)(s(18),s(73),null,null);t.exports=e.exports},,,function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(61),a=s.n(e);n.default={components:{appHeader:a.a},created:function(){this.$store.dispatch("initStocks")}}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(3),a=s.n(e),o=s(1);n.default={data:function(){return{isDropdownOpen:!1}},computed:{funds:function(){return this.$store.getters.funds}},methods:a()({},s.i(o.a)({randomizeStocks:"randomizeStocks",fetchData:"loadData"}),{endDay:function(){this.randomizeStocks()},saveData:function(){var t={funds:this.$store.getters.funds,stockPortfolio:this.$store.getters.stockPortfolio,stocks:this.$store.getters.stocks};this.$http.put("data.json",t)},loadData:function(){this.fetchData()}})}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default={computed:{funds:function(){return this.$store.getters.funds}}}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(3),a=s.n(e),o=s(1),i=s(64),r=s.n(i);n.default={computed:a()({},s.i(o.c)({stocks:"stockPortfolio"})),components:{appStock:r.a}}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(3),a=s.n(e),o=s(1);n.default={props:["stock"],data:function(){return{quantity:0}},computed:{insufficientQuantity:function(){return this.quantity>this.stock.quantity}},methods:a()({},s.i(o.a)({placeSellOrder:"sellStock"}),{sellStock:function(){var t={stockId:this.stock.id,stockPrice:this.stock.price,quantity:this.quantity};this.placeSellOrder(t),this.quantity=0}})}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default={props:["stock"],data:function(){return{quantity:0}},computed:{funds:function(){return this.$store.getters.funds},insufficientFunds:function(){return this.quantity*this.stock.price>this.funds}},methods:{buyStock:function(){var t={stockId:this.stock.id,stockPrice:this.stock.price,quantity:this.quantity};console.log(t),this.$store.dispatch("buyStock",t),this.quantity=0}}}},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(65),a=s.n(e);n.default={components:{appStock:a.a},computed:{stocks:function(){return this.$store.getters.stocks}}}},function(t,n,s){"use strict";n.a=[{id:1,name:"BMW",price:110},{id:2,name:"Google",price:200},{id:3,name:"Apple",price:250},{id:4,name:"Twitter",price:8}]},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(2),a=s(17),o=s(16),i=s(15),r=s.n(i),c=s(13),u=s(14);e.a.use(a.a),e.a.use(o.a),e.a.http.options.root="https://vuejs-stock-trader.firebaseio.com/",e.a.filter("currency",function(t){return"$"+t.toLocaleString()});var l=new a.a({mode:"history",routes:c.a});new e.a({el:"#app",router:l,store:u.a,render:function(t){return t(r.a)}})},function(t,n,s){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var e=s(2);s.d(n,"loadData",function(){return a});var a=function(t){var n=t.commit;e.a.http.get("data.json").then(function(t){return t.json()}).then(function(t){if(t){var s=t.stocks,e=t.funds,a=t.stockPortfolio,o={stockPortfolio:a,funds:e};n("SET_STOCKS",s),n("SET_PORTFOLIO",o)}})}},function(t,n,s){"use strict";var e={funds:1e4,stocks:[]},a={BUY_STOCK:function(t,n){var s=n.stockId,e=n.quantity,a=n.stockPrice,o=t.stocks.filter(function(t){return t.id===s}),i=o[0];i?i.quantity+=e:t.stocks.push({id:s,quantity:e}),t.funds-=a*e},SELL_STOCK:function(t,n){var s=n.stockId,e=n.quantity,a=n.stockPrice,o=t.stocks.filter(function(t){return t.id===s}),i=o[0];i.quantity>e?i.quantity-=e:t.stocks.splice(t.stocks.indexOf(i),1),t.funds+=a*e},SET_PORTFOLIO:function(t,n){t.funds=n.funds,t.stocks=n.stockPortfolio?n.stockPortfolio:[]}},o={sellStock:function(t,n){(0,t.commit)("SELL_STOCK",n)}},i={stockPortfolio:function(t,n){return t.stocks.map(function(t){var s=n.stocks.filter(function(n){return n.id===t.id}),e=s[0];return{id:t.id,quantity:t.quantity,name:e.name,price:e.price}})},funds:function(t){return t.funds}};n.a={state:e,mutations:a,actions:o,getters:i}},function(t,n,s){"use strict";var e=s(25),a={stocks:[]},o={SET_STOCKS:function(t,n){t.stocks=n},RND_STOCKS:function(t){t.stocks.forEach(function(t){t.price=Math.round(t.price*(1+Math.random()-.5))})}},i={buyStock:function(t,n){(0,t.commit)("BUY_STOCK",n)},initStocks:function(t){(0,t.commit)("SET_STOCKS",e.a)},randomizeStocks:function(t){(0,t.commit)("RND_STOCKS")}},r={stocks:function(t){return t.stocks}};n.a={state:a,mutations:o,actions:i,getters:r}},,,,,,,,,,,,,,,,,,,,,,,,,,,,,function(t,n){},function(t,n){},function(t,n){},function(t,n,s){var e=s(0)(s(19),s(70),null,null);t.exports=e.exports},function(t,n,s){var e=s(0)(s(20),s(68),null,null);t.exports=e.exports},function(t,n,s){var e=s(0)(s(21),s(69),null,null);t.exports=e.exports},function(t,n,s){s(58);var e=s(0)(s(22),s(67),"data-v-280f9bae",null);t.exports=e.exports},function(t,n,s){s(59);var e=s(0)(s(23),s(72),"data-v-ba6da956",null);t.exports=e.exports},function(t,n,s){var e=s(0)(s(24),s(71),null,null);t.exports=e.exports},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",{staticClass:"col-sm-6 col-md-4"},[s("div",{staticClass:"panel panel-info"},[s("div",{staticClass:"panel-heading"},[s("h3",{staticClass:"panel-title"},[t._v("\n                "+t._s(t.stock.name)+"\n                "),s("small",[t._v("(Price: "+t._s(t.stock.price)+" | Quantity: "+t._s(t.stock.quantity)+")")])])]),t._v(" "),s("div",{staticClass:"panel-body"},[s("div",{staticClass:"pull-left"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.quantity,expression:"quantity"}],staticClass:"form-control",class:{danger:t.insufficientQuantity},attrs:{type:"number",placeholder:"Quantity"},domProps:{value:t.quantity},on:{input:function(n){n.target.composing||(t.quantity=n.target.value)},blur:function(n){t.$forceUpdate()}}})]),t._v(" "),s("div",{staticClass:"pull-right"},[s("button",{staticClass:"btn btn-success",attrs:{disabled:t.insufficientQuantity||t.quantity<=0},on:{click:t.sellStock}},[t._v(t._s(t.insufficientQuantity?"Not enough":"Sell")+"\n                ")])])])])])},staticRenderFns:[]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",[s("h1",[t._v("Trade or View your Portfolio")]),t._v(" "),s("h6",[t._v("You may Save & Load your Data")]),t._v(" "),s("h6",[t._v("Click on 'End Day' to begin a New Day!")]),t._v(" "),s("hr"),t._v(" "),s("p",[t._v("Your Funds: "+t._s(t._f("currency")(t.funds)))]),t._v(" "),s("h1")])},staticRenderFns:[]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",[s("h1",[t._v("The Portfolio component")]),t._v(" "),t._l(t.stocks,function(t){return s("app-stock",{attrs:{stock:t}})})],2)},staticRenderFns:[]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("nav",{staticClass:"navbar navbar-default"},[s("div",{staticClass:"container-fluid"},[s("div",{staticClass:"navbar-header"},[s("router-link",{staticClass:"navbar-brand",attrs:{to:"/"}},[t._v("Stock Trader")])],1),t._v(" "),s("div",{staticClass:"collapse navbar-collapse"},[s("ul",{staticClass:"nav navbar-nav"},[s("router-link",{attrs:{to:"/portfolio",activeClass:"active",tag:"li"}},[s("a",[t._v("Portfolio")])]),t._v(" "),s("router-link",{attrs:{to:"/stocks",activeClass:"active",tag:"li"}},[s("a",[t._v("Stocks")])])],1),t._v(" "),s("strong",{staticClass:"navbar-text navbar-right"},[t._v("Funds: "+t._s(t._f("currency")(t.funds)))]),t._v(" "),s("ul",{staticClass:"nav navbar-nav navbar-right"},[s("li",[s("a",{attrs:{href:"#"},on:{click:t.endDay}},[t._v("End Day")])]),t._v(" "),s("li",{staticClass:"dropdown",class:{open:t.isDropdownOpen},on:{click:function(n){t.isDropdownOpen=!t.isDropdownOpen}}},[t._m(0),t._v(" "),s("ul",{staticClass:"dropdown-menu"},[s("li",[s("a",{attrs:{href:"#"},on:{click:t.saveData}},[t._v("Save Data")])]),t._v(" "),s("li",[s("a",{attrs:{href:"#"},on:{click:t.loadData}},[t._v("Load Data")])])])])])])])])},staticRenderFns:[function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("a",{staticClass:"dropdown-toggle",attrs:{href:"#","data-toggle":"dropdown",role:"button","aria-haspopup":"true","aria-expanded":"false"}},[t._v("Save & Load "),s("span",{staticClass:"caret"})])}]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",t._l(t.stocks,function(t){return s("app-stock",{attrs:{stock:t}})}))},staticRenderFns:[]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",{staticClass:"col-sm-6 col-md-4"},[s("div",{staticClass:"panel panel-success"},[s("div",{staticClass:"panel-heading"},[s("h3",{staticClass:"panel-title"},[t._v("\n                "+t._s(t.stock.name)+"\n                "),s("small",[t._v("(Price: "+t._s(t.stock.price)+")")])])]),t._v(" "),s("div",{staticClass:"panel-body"},[s("div",{staticClass:"pull-left"},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.quantity,expression:"quantity"}],staticClass:"form-control",class:{danger:t.insufficientFunds},attrs:{type:"number",placeholder:"Quantity"},domProps:{value:t.quantity},on:{input:function(n){n.target.composing||(t.quantity=n.target.value)},blur:function(n){t.$forceUpdate()}}})]),t._v(" "),s("div",{staticClass:"pull-right"},[s("button",{staticClass:"btn btn-success",attrs:{disabled:t.insufficientFunds||t.quantity<=0},on:{click:t.buyStock}},[t._v("\n                    "+t._s(t.insufficientFunds?"Insufficient Funds":"Buy")+"\n\n                ")])])])])])},staticRenderFns:[]}},function(t,n){t.exports={render:function(){var t=this,n=t.$createElement,s=t._self._c||n;return s("div",{staticClass:"container"},[s("app-header"),t._v(" "),s("div",{staticClass:"row"},[s("div",{staticClass:"col-xs-12"},[s("transition",{attrs:{name:"slide",mode:"out-in"}},[s("router-view")],1)],1)])],1)},staticRenderFns:[]}},,,function(t,n){}],[26]);
//# sourceMappingURL=app.b491cb1c2d97432ed1d2.js.map