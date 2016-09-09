"use strict";!function(){angular.module("app",["ngAnimate","ngResource","ngDialog","LocalStorageModule","ui.router","ui.mask","templates","app.blocks","app.core","app.layouts","app.ctrls","app.services","app.filters"]).config(["localStorageServiceProvider","$anchorScrollProvider","ngDialogProvider","$httpProvider",function(e,a,o,t){e.setPrefix("YOKI").setStorageType("localStorage").setNotify(!0,!0),o.setDefaults({showClose:!1}),t.interceptors.push("apiInterceptor"),a.disableAutoScrolling(),t.defaults.useXDomain=!0,t.defaults.headers.common["X-Requested-With"]="XMLHttpRequest",t.defaults.headers.post["Content-Type"]="application/json",t.defaults.headers.post.Accept="application/json"}]).run(["$rootScope","ENV",function(e,a){e.fullPath=function(e){return a.apiUrl+e}}]),angular.module("app.core",[]),angular.module("app.blocks",[]),angular.module("app.layouts",[]),angular.module("app.ctrls",[]),angular.module("app.services",[]),angular.module("app.filters",[])}();
"use strict";!function(){angular.module("app.services").factory("apiInterceptor",["$rootScope","$q","ENV",function(r,e,t){return{request:function(r){return r.url.indexOf("html/",0)==-1&&(r.url=t.apiUrl+r.url),r},requestError:function(r){return e.reject(r)},responseError:function(r){return e.reject(r)}}}])}();
"use strict";!function(){function t(){this.baseUrl="",this.$get=["$http",function(t){return{get:function(e){return t.get(this.baseUrl+e)},post:function(e,n){return t.post(this.baseUrl+e,n)},"delete":function(e){return t["delete"](this.baseUrl+e)},put:function(e,n){return t.put(this.baseUrl+e,n)}}}]}angular.module("app.services").factory("$api",t)}();
"use strict";!function(){function n(n,t,c,r){function e(n){var c=t.searchByKey(y,{id:n.id});c.count++}function i(n){var c=t.searchByKey(y,{id:n.id});c.count--,c.count||y.splice(y.indexOf(c),1)}function o(n){var c=t.searchByKey(y,{id:n.id});y.splice(y.indexOf(c),1)}function u(){for(;y.length;)y.pop()}function a(){var n=0;return y&&y.forEach(function(t){n+=t.count}),n}function f(){return y}function s(n){var c=t.searchByKey(y,{id:n.id});c?c.count++:(n.count=1,y.push(n))}function d(t){return function(){t.apply(this,arguments),n.setObject("cart",y)}}function p(n){return function(){n.apply(this,arguments),r.$broadcast(c.editCart)}}var y=n.getArray("cart"),h=d(p(s)),l=d(p(e)),v=d(p(i)),m=d(p(o)),g=d(p(u)),B={add:h,inc:l,dec:v,remove:m,clean:g,amount:a,list:f};return B}angular.module("app.services").factory("cart",n),n.$inject=["storage","helpers","APP_EVENTS","common"]}();
"use strict";!function(){function e(e){return e("/category/:action/:id",{},{fetchAll:{method:"GET",isArray:!0,params:{action:"getAll"}},fetchDishes:{method:"GET",isArray:!0,params:{action:"getAvailableDishesFromCategory"}}})}angular.module("app.services").factory("categories",e),e.$inject=["$resource"]}();
"use strict";!function(){function a(a){return a("/images/:action/:data",{},{fetchAll:{method:"POST",isArray:!0,params:{action:"getDishImages"}}})}angular.module("app.services").factory("images",a),a.$inject=["$resource"]}();
"use strict";!function(){function e(e){return e("/operator/:action/:request",{},{create:{method:"POST",params:{action:"createOrder"}}})}angular.module("app.services").factory("orders",e),e.$inject=["$resource"]}();
"use strict";!function(){function e(e){return{set:function(t,n){e.set(t,n)},get:function(t,n){return e.get(t)||n},remove:function(t){e.remove(t)},setObject:function(t,n){e.set(t,JSON.stringify(n))},getObject:function(t){return JSON.parse(e.get(t))||{}},getArray:function(t){return JSON.parse(e.get(t))||[]}}}angular.module("app.services").factory("storage",e),e.$inject=["localStorageService"]}();
"use strict";!function(){function n(n,e){function i(){var n=c.index+1;n>a?c.index=0:c.index++}function t(){o=n(i,5e3)}var c=this;c.index=0;var o=void 0,a=2;c.selectSlide=function(e){o&&n.cancel(o),c.index=e,t()},t(),e.$on("$destroy",function(){o&&n.cancel(o)})}angular.module("app.layouts").controller("HeaderIndexCtrl",n),n.$inject=["$interval","$scope"]}();
"use strict";!function(){function e(e,t,o){function s(){t.open({template:"html/modals/callback.html",showClose:!0,width:"25%",appendClassName:"callback-modal",controller:a,controllerAs:"vm"})}function a(e){function t(){e.create(o.form,function(){o.success=!0})}var o=this;o.form={client:{name:"",phone:0,address:"default"},dishes:[],status:"FRESH"},o.success=!1,o.send=t}e.categories=o.getArray("categories"),e.showCallback=s,a.$inject=["orders"]}angular.module("app.ctrls").controller("MainCtrl",e),e.$inject=["$rootScope","ngDialog","storage"]}();
"use strict";!function(){function t(){}angular.module("app.layouts").controller("NavCtrl",t),t.$inject=[]}();
"use strict";!function(){function n(n,r){function t(){var n=0;if(e.dishes){var r=!0,t=!1,i=void 0;try{for(var c,o=e.dishes[Symbol.iterator]();!(r=(c=o.next()).done);r=!0){var a=c.value;n+=a.price*a.count}}catch(s){t=!0,i=s}finally{try{!r&&o["return"]&&o["return"]()}finally{if(t)throw i}}}return n}var e=this;e.dishes=r.list(),e.priceOrder=t(),e.incCount=function(n){r.inc(n)},e.decCount=function(n){r.dec(n)},e.removeDish=function(n){r.remove(n)},e.clearCart=function(){r.clean()},n.$watch(function(){return e.dishes},function(){e.priceOrder=t()},!0)}function r(n,r,t){var e=this,i=[];t.list().forEach(function(n){i.push({quantity:n.count,dishId:n.id})}),e.form={client:{name:"",phone:0,address:""},dishes:i,status:"FRESH"},e.send=function(){n.create(e.form).$promise["finally"](function(){t.clean(),r.go("main.order-success",{client:e.form.client})})}}angular.module("app.layouts").controller("SideBarCtrl",n).controller("SideBarOrderCtrl",r),n.$inject=["$scope","cart"],r.$inject=["orders","$state","cart"]}();
"use strict";!function(){function t(t,n,i,e,a,s){function o(){var n=[r()];t.start(n)}function r(){return i.fetchDishes({id:s.id}).$promise.then(function(t){h.pagination=n.create(t),h.getMoreDishes()})}var h=this;h.pagination,h.dishes=[],o(),h.getMoreDishes=function(){var t=h.pagination.next();a.shapeArrayInRows(t,3).forEach(function(t){h.dishes.push(t)})},h.showDishDetail=function(t){e.open({template:"html/modals/dish.html",data:t})}}angular.module("app.ctrls").controller("CategoryCtrl",t),t.$inject=["spinner","pagination","categories","ngDialog","helpers","$stateParams"]}();
"use strict";!function(){function r(r,t){function n(){var r={price:0,count:0};if(e.dishes){var t=!0,n=!1,o=void 0;try{for(var c,i=e.dishes[Symbol.iterator]();!(t=(c=i.next()).done);t=!0){var a=c.value;r.price+=a.price*a.count,r.count+=a.count}}catch(u){n=!0,o=u}finally{try{!t&&i["return"]&&i["return"]()}finally{if(n)throw o}}}return r}var e=this;angular.extend(e,r("SideBarCtrl",{$scope:t})),e.orderInfo=n(),t.$watch(function(){return e.dishes},function(){e.orderInfo=n()},!0)}angular.module("app.ctrls").controller("CartCtrl",r),r.$inject=["$controller","$scope"]}();
"use strict";!function(){function t(t){var e=this;e.step=1,e.client=t.client,e.nextStep=function(){e.step++}}angular.module("app.ctrls").controller("OrderSuccessCtrl",t),t.$inject=["$stateParams"]}();
"use strict";!function(){function n(n,t,i,e,a){function o(){var t=[s()];n.start(t)}function s(){return i.fetchDishes({id:1}).$promise.then(function(n){r.pagination=t.create(n),r.getMoreDishes()})}var r=this;r.pagination,r.dishes=[],o(),r.getMoreDishes=function(){var n=r.pagination.next();a.shapeArrayInRows(n,3).forEach(function(n){r.dishes.push(n)})},r.showDishDetail=function(n){e.open({template:"html/modals/dish.html",data:n})}}angular.module("app.ctrls").controller("HomeCtrl",n),n.$inject=["spinner","pagination","categories","ngDialog","helpers"]}();
"use strict";!function(){function t(t,o,n){function r(){return o.$broadcast.apply(o,arguments)}function c(){return o.$emit.apply(o,arguments)}var e={$broadcast:r,$emit:c,$q:t,$timeout:n};return e}angular.module("app.core").factory("common",t),t.$inject=["$q","$rootScope","$timeout"]}();
"use strict";!function(){angular.module("app.core").constant("ENV",{apiUrl:"http://46.101.230.74:8080"}).constant("APP_EVENTS",{editCart:"edit-to-cart",serverError:"server-error"})}();
"use strict";!function(){function t(){function t(t){var l=arguments.length<=1||void 0===arguments[1]?6:arguments[1];return a.page=0,a.total=i(t,l),a.limit=l,r=t,{next:e,hasEnabled:n}}function e(){var t=[];return t=0===a.page?r.slice(a.page,a.limit):r.slice(a.page*a.limit,a.page*a.limit+a.limit),a.page++,t}function n(){return a.page<a.total}function i(t,e){return Math.ceil(t.length/e)}var a={},r=[],l={create:t};return l}angular.module("app.core").factory("pagination",t),t.$inject=[]}();
"use strict";!function(){angular.module("app.core").config(["$stateProvider","$urlRouterProvider",function(t,e){t.state("index",{url:"/",views:{"@":{templateUrl:"html/layouts/main.html",resolve:{appInit:["categories","storage",function(t,e){return t.fetchAll().$promise.then(function(t){e.setObject("categories",t)})}]}},"header@index":{templateUrl:"html/layouts/header-index.tpl.html"},"nav@index":{templateUrl:"html/layouts/nav.tpl.html",controller:"NavCtrl",controllerAs:"nav"},"sidebar@index":{templateUrl:"html/layouts/sidebar.tpl.html",controller:"SideBarCtrl",controllerAs:"sbar"},"content@index":{templateUrl:"html/components/home/home.tpl.html",controller:"HomeCtrl",controllerAs:"home"},"marketing@index":{templateUrl:"html/layouts/marketing.tpl.html"},"footer@index":{templateUrl:"html/layouts/footer.tpl.html"}}}).state("main",{"abstract":!0,url:"/",views:{"@":{templateUrl:"html/layouts/main.html",resolve:{appInit:["categories","storage",function(t,e){if(!e.getArray("categories").length)return t.fetchAll().$promise.then(function(t){e.setObject("categories",t)})}]}},"header@main":{templateUrl:"html/layouts/header.tpl.html"},"nav@main":{templateUrl:"html/layouts/nav.tpl.html",controller:"NavCtrl",controllerAs:"nav"},"sidebar@main":{templateUrl:"html/layouts/sidebar.tpl.html",controller:"SideBarCtrl",controllerAs:"sbar"},"marketing@main":{templateUrl:"html/layouts/marketing.tpl.html"},"footer@main":{templateUrl:"html/layouts/footer.tpl.html"}}}).state("main.category",{url:"category/:id",views:{"content@main":{controller:"CategoryCtrl",templateUrl:"html/components/category/category.tpl.html",controllerAs:"cat"}}}).state("main.cart",{url:"cart",views:{"content@main":{controller:"CartCtrl",templateUrl:"html/components/cart/cart.tpl.html",controllerAs:"cart"},"sidebar@main":{templateUrl:"html/layouts/sidebar-order.tpl.html",controller:"SideBarOrderCtrl",controllerAs:"sbcar"}}}).state("main.order-success",{url:"order/success",views:{"content@main":{controller:"OrderSuccessCtrl",templateUrl:"html/components/order/order.tpl.html",controllerAs:"order"}},params:{client:null}}),e.otherwise("/")}])}();
"use strict";!function(){function n(n,t){function e(e){t.loadingSpinner=!0,n.$q.all(e).then(function(){n.$timeout(function(){t.loadingSpinner=!1},400)})}var r={start:e};return r}function t(){return{restrict:"E",replace:!0,templateUrl:"html/layouts/core/spinner.html"}}angular.module("app.core").directive("loadingSpinner",t).factory("spinner",n),n.$inject=["common","$rootScope"]}();
"use strict";!function(){function t(){return function(t,n){if("string"==typeof n)return(t+" "+n).trim()}}angular.module("app.blocks").filter("postfix",t)}();
"use strict";!function(){function r(){var r={};return r.shapeArrayInRows=function(r,t){for(var e=[],n=0;n<r.length;n+=t)e.push(r.slice(n,n+t));return e},r.searchByKey=function(r,t){var e=Object.keys(t),n=!0,a=!1,o=void 0;try{for(var u,c=r[Symbol.iterator]();!(n=(u=c.next()).done);n=!0){var i=u.value;if(i[e[0]]==t[e[0]])return i}}catch(l){a=!0,o=l}finally{try{!n&&c["return"]&&c["return"]()}finally{if(a)throw o}}},r}angular.module("app.blocks").factory("helpers",r),r.$inject=[]}();
"use strict";!function(){function e(e){return{restrict:"E",replace:!0,scope:{images:"=images",click:"&"},templateUrl:"html/blocks/directive/image-slider/slider.html",link:function(i){i.index=0,i.clickImage=function(){i.onClick&&i.onClick()},i.fullPath=function(i){return e.apiUrl+i},i.isCurrentIndex=function(e){return i.index===e},i.prevSlide=function(){var e=i.images.length-1,n=i.index-1;i.index=n===-1?e:n},i.nextSlide=function(){var e=i.images.length-1,n=i.index+1;i.index=n>e?0:n}}}}angular.module("app.blocks").directive("imageSlider",e),e.$inject=["ENV"]}();
"use strict";!function(){function i(i,n){return{restrict:"A",scope:{dish:"=dish"},link:function(c,t){t.bind("click",function(){c.$apply(function(){n.closeAll(),i.add(c.dish)})})}}}angular.module("app.blocks").directive("addCart",i),i.$inject=["cart","ngDialog"]}();
//# sourceMappingURL=bundle.js.map
