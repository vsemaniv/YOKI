angular.module('templates', []).run(['$templateCache', function($templateCache) {$templateCache.put('html/modals/callback.html','<form name="callbackForm" class="callback-form" ng-submit="vm.send()">\n  <div class="well" ng-if="vm.success">\u0414\u044F\u043A\u0443\u0454\u043C\u043E \u0437\u0430 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F, \u043D\u0430\u0439\u0431\u043B\u0438\u0436\u0447\u0438\u043C \u0447\u0430\u0441\u043E\u043C \u043D\u0430\u0448 \u043E\u043F\u0435\u0440\u0430\u0442\u043E\u0440 \u0437\u0432\u2019\u044F\u0436\u0435\u0442\u044C\u0441\u044F \u0437 \u0412\u0430\u043C\u0438!\n  </div>\n\n  <div ng-if="!vm.success">\n    <div class="input"\n         ng-class="{\'error\': callbackForm.name.$error.required && callbackForm.name.$dirty}">\n      <input type="text" name="name" placeholder="\u0406\u043C\'\u044F"\n             ng-model="vm.form.client.name"\n             required>\n    </div>\n\n    <div class="input"\n         ng-class="{\'error\': callbackForm.phone.$error.required && callbackForm.phone.$dirty}">\n      <input type="text"\n             name="phone"\n             placeholder="\u0422\u0435\u043B\u0435\u0444\u043E\u043D"\n             ng-model="vm.form.client.phone"\n             ui-mask="(999) 999-9999"\n             ui-options="{clearOnBlur: false}"\n             ui-mask-placeholder\n             ui-mask-placeholder-char\n             required>\n    </div>\n    <button type="submit" ng-disabled="callbackForm.$invalid">\u0412\u0456\u0434\u043F\u0440\u0430\u0432\u0438\u0442\u0438\n    </button>\n  </div>\n</form>\n');
$templateCache.put('html/modals/dish.html','<div class="dish-modal">\n  <div class="modal-img">\n    <image-slider images="ngDialogData.images"></image-slider>\n  </div>\n  <div class="modal-ingredients">\n    <div class="title">\n      <span>\n        <div ng-switch="dish.type">\n          <img ng-src="img/new-lable.png" ng-switch-when="LATEST">\n          <img ng-src="img/hot-lable.png" ng-switch-when="HOT">\n          <img ng-src="img/veggie-lable.png" ng-switch-when="VEGAN">\n        </div>\n      </span>\n      <span>\n        <h2 ng-bind="ngDialogData.name"></h2>\n      </span>\n    </div>\n    <div class="description">{{ngDialogData.description}}</div>\n    <div class="modal-action">\n      <div class="modal-price">\n        <h2 ng-bind="ngDialogData.price | postfix:\'\u0413\u0440\u043D\'"></h2>\n      </div>\n      <div class="modal-button">\n        <button class="btn btn-block btn-primary" add-cart dish="ngDialogData">\n          \u0437\u0430\u043C\u043E\u0432\u0438\u0442\u0438\n        </button>\n      </div>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('html/layouts/footer.tpl.html','<footer>\n  <div class="top">\n    <div class="left">\n      <a href="">\n        <img src="img/socicon-3.png" alt="">\n      </a>\n      <a href="">\n        <img src="img/socicon-2.png" alt="">\n      </a>\n      <a href="">\n        <img src="img/socicon-1.png" alt="">\n      </a>\n    </div>\n    <div class="right">\n      <nav>\n        <a href="">\u0414\u043E\u0441\u0442\u0430\u0432\u043A\u0430</a>\n        <a href="">\u0420\u0435\u0441\u0442\u043E\u0440\u0430\u043D</a>\n        <a href="">\u0421\u043F\u0456\u0432\u043F\u0440\u0430\u0446\u044F</a>\n      </nav>\n    </div>\n  </div>\n  <div class="bottom">\n    <div class="left">\n      <a href="#" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 App Store">\n        <img src="img/app-store-white.png" class="img-responsive" alt="as-logo"/>\n      </a>\n      <a href="https://play.google.com/store/apps/details?id=ru.yoki.app" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 Google Play">\n        <img src="img/gp-store-white.png" class="img-responsive" alt="gp-logo"/>\n      </a>\n    </div>\n    <div class="right">\n        <span>\u0422\u0435\u043B\u0435\u0444\u043E\u043D: 050 159 7777</span>\n        <span>E-mail: yoki@gmail.com</span>\n    </div>\n  </div>\n</footer>\n');
$templateCache.put('html/layouts/header-index.tpl.html','<header>\n   <div class="container-fluid">\n       <div class="row">\n         <div class="header-top">\n           <div class="time-info">\n               <i class="glyphicon glyphicon-time"></i>\n               <span>\u0414\u043E\u0441\u0442\u0430\u0432\u043A\u0430 \u0441\u044C\u043E\u0433\u043E\u0434\u043D\u0456 \u0437 9:00 \u0434\u043E 23:30</span>\n           </div>\n             <div class="log-in-area">\n               <div class="wrap-pointer">\n                 <span>050 159 7777</span>\n               </div>\n                 <input type="button" value="\u0417\u0430\u0442\u0435\u043B\u0435\u0444\u043E\u043D\u0443\u0439\u0442\u0435 \u043C\u0435\u043D\u0456" ng-click="showCallback()">\n             </div>\n         </div>\n       </div>\n       <div class="row header-middle">\n           <div class="col-sm-12 col-md-4 col-lg-4 col-xs-12">\n               <div class="logo">\n                   <img src="img/logo_menu.png" alt="logo" class="index-logo-top">\n                   <p>\n                     <p>\u0412\u0441\u044F \u0410\u0437\u0456\u044F \u0432 <br> \u043E\u0434\u043D\u0456\u0439 \u0442\u0430\u0440\u0456\u043B\u0446\u0456</p>\n                   </p>\n               </div>\n               <div class="market-icons">\n                   <a href="#" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 App Store">\n                     <img src="img/app-store.png" class="img-responsive" alt="as-logo"/>\n                   </a>\n                   <a href="https://play.google.com/store/apps/details?id=ru.yoki.app" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 Google Play">\n                     <img src="img/gp-store.png" class="img-responsive" alt="gp-logo"/>\n                   </a>\n               </div>\n           </div>\n           <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 header-nav-bar">\n             <div>\n               <h2>\u0423\u041B\u042E\u0411\u041B\u0415\u041D\u0406 \u0421\u0423\u0428\u0406</h2>\n               <span>(\u0422\u0410 \u0429\u041E\u0421\u042C \u0406\u041D\u0428\u0415)</span>\n             </div>\n               <ul class="nav nav-justified">\n                   <li class="activated">\n                     <img src="img/menu-icon-w.png" alt="icon">\n                     <a href="#" class="btn btn-default">\n                       <span class="main-title">\u041C\u0435\u043D\u044E</span><br>\n                       <i class="glyphicon glyphicon-option-horizontal"></i>\n                     </a>\n                   </li>\n                   <li><img src="img/order-icon.png" alt="icon">\n                     <a href="#" class="btn btn-default">\n                       <span class="main-title">\u0417\u0430\u043C\u043E\u0432\u0438\u0442\u0438 <br> \u043E\u043D\u043B\u0430\u0439\u043D</span>\n                     </a>\n                   </li>\n                   <li><img src="img/loyal-icon.png" alt="icon"><a href="#"\n                                                                    class="btn btn-default"><span\n                           class="main-title">\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u0430 <br> \u043B\u043E\u044F\u043B\u044C\u043D\u043E\u0441\u0442\u0456</span></a></li>\n                   <li><img src="img/rest-icon.png" alt="icon"><a href="#"\n                                                                   class="btn btn-default"><span\n                           class="main-title">\u0420\u0435\u0441\u0442\u043E\u0440\u0430\u043D</span> <br> <span\n                           class="additional-title">\u041A\u043D\u044F\u0437\u044F \u0420\u043E\u043C\u0430\u043D\u0430, 18</span></a></li>\n                 <li>\n                   <img src="img/pr-icon.png" alt="icon">\n                   <a href="#" class="btn btn-default">\n                     <span class="main-title">\u0421\u043F\u0456\u0432\u043F\u0440\u0430\u0446\u044F</span><br>\n                   </a>\n                 </li>\n               </ul>\n           </div>\n       </div>\n   </div>\n</header>\n');
$templateCache.put('html/layouts/header.tpl.html','<header class="second-header">\n  <div class="container-fluid">\n    <div class="row">\n      <div class="header-top">\n        <div class="time-info">\n          <i class="glyphicon glyphicon-time"></i>\n          <span>\u0414\u043E\u0441\u0442\u0430\u0432\u043A\u0430 \u0441\u044C\u043E\u0433\u043E\u0434\u043D\u0456 \u0437 9:00 \u0434\u043E 23:30</span>\n        </div>\n        <div class="log-in-area">\n          <div class="wrap-pointer">\n            <span>050 159 7777</span>\n          </div>\n          <input type="button" value="\u0417\u0430\u0442\u0435\u043B\u0435\u0444\u043E\u043D\u0443\u0439\u0442\u0435 \u043C\u0435\u043D\u0456"\n                 ng-click="showCallback()">\n        </div>\n      </div>\n    </div>\n    <div class="row header-middle">\n      <div class="col-sm-4 col-md-4 col-lg-4">\n        <div class="logo" ui-sref="index">\n           <img src="img/logo_menu.png" alt="logo" class="second-logo-top">\n          <h2>YOKI</h2>\n        </div>\n      </div>\n      <div class="header-nav-bar">\n        <nav>\n          <a>\n            <section class="icon"></section>\n            <div>\n              <span class="main-title">\u041C\u0435\u043D\u044E</span>\n              <i class="three-dots"></i>\n            </div>\n          </a>\n          <a>\n            <section class="icon"></section>\n            <div>\n              <span class="main-title">\u0417\u0430\u043C\u043E\u0432\u0438\u0442\u0438 <br> \u043E\u043D\u043B\u0430\u0439\u043D</span>\n            </div>\n          </a>\n          <a>\n            <section class="icon"></section>\n            <div>\n              <span class="main-title">\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0430 <br> \u043B\u043E\u044F\u043B\u044C\u043D\u043E\u0441\u0442\u0456</span>\n            </div>\n          </a>\n          <a>\n            <section class="icon"></section>\n            <div>\n              <span class="main-title">\u0420\u0435\u0441\u0442\u043E\u0440\u0430\u043D</span>\n              <span class="additional-title">\u041A\u043D\u044F\u0437\u044F \u0420\u043E\u043C\u0430\u043D\u0430, 18</span>\n            </div>\n          </a>\n          <a>\n            <section class="icon"></section>\n            <div>\n              <span class="main-title">\u0421\u043F\u0456\u0432\u043F\u0440\u0430\u0446\u044F</span>\n            </div>\n          </a>\n        </nav>\n      </div>\n    </div>\n  </div>\n</header>\n');
$templateCache.put('html/layouts/main.html','<div ng-controller="MainCtrl">\n    <section ui-view="header" autoscroll="true"></section>\n    <main>\n        <div class="container-fluid">\n            <section ui-view="nav"></section>\n            <div class="row">\n                <div class="col-md-9" id="content-wrapper">\n                    <loading-spinner></loading-spinner>\n                    <section ui-view="content"></section>\n                </div>\n                <div class="col-md-3">\n                    <section ui-view="sidebar">\n                    </section>\n                </div>\n            </div>\n            <section ui-view="marketing"></section>\n        </div>\n    </main>\n    <section ui-view="footer"></section>\n</div>\n');
$templateCache.put('html/layouts/marketing.tpl.html','<div class="row marketing">\n  <div class="col-md-3">\n    <div class="marketing__first">\n      <div class="wrap-pointer">\n        <span>050 159 7777</span>\n      </div>\n      <div class="market-icons">\n          <a href="#" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 App Store">\n            <img src="img/app-store.png" class="img-responsive" alt="as-logo"/>\n          </a>\n          <a href="https://play.google.com/store/apps/details?id=ru.yoki.app" title="\u0421\u043A\u0430\u0447\u0430\u0442\u0438 \u0432 Google Play">\n            <img src="img/gp-store.png" class="img-responsive" alt="gp-logo"/>\n          </a>\n      </div>\n      <h2>\u042F\u043A \u0437\u0430\u043C\u043E\u0432\u0438\u0442\u0438?</h2>\n      <p>\n        \u0412\u0438 \u043C\u043E\u0436\u0435\u0442\u0435 \u0437\u0440\u043E\u0431\u0438\u0442\u0438 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F \u0447\u0435\u0440\u0435\u0437 \u0434\u043E\u0434\u0430\u0442\u043E\u043A \u043D\u0430 \u0432\u0430\u0448\u043E\u043C\u0443 \u0441\u043C\u0430\u0440\u0442\u0444\u043E\u043D\u0456, \u0437\u0430\u043C\u043E\u0432\u0438\u0442\u0438 \u0437 \u043E\u043D\u043B\u0430\u0439\u043D-\u0441\u0430\u0439\u0442\u0443\n        \u0447\u0438 \u043F\u0440\u043E\u0441\u0442\u043E \u0437\u0430\u0442\u0435\u043B\u0435\u0444\u043E\u043D\u0443\u0432\u0430\u0442\u0438 \u043D\u0430\u043C.\n      </p>\n    </div>\n  </div>\n  <div class="col-md-3">\n    <div class="marketing__second">\n      <h2>\u041F\u0440\u0438\u0433\u043E\u0442\u0443\u0432\u0430\u043D\u043D\u044F</h2>\n      <p>\n      \u0412\u0430\u0448\u0435 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u044F \u043E\u0434\u0440\u0430\u0437\u0443 \u0436 \u043F\u043E\u0442\u0440\u0430\u043F\u043B\u044F\u0454 \u0434\u043E \u043D\u0430\u0448\u043E\u0433\u043E \u0428\u0435\u0444-\u043F\u043E\u0432\u0430\u0440\u0430 \u043A\u043E\u0442\u0440\u0438\u0439 \u0433\u043E\u0442\u0443\u0454 \u0434\u043B\u044F \u0412\u0430\u0441 \u0437 \u043D\u0430\u0439\u043A\u0440\u0430\u0449\u0438\u0445 \u0442\u0430\n      \u043D\u0430\u0439\u0441\u0432\u0456\u0436\u0456\u0448\u0438\u0445 \u043F\u0440\u043E\u0434\u0443\u043A\u0442\u0456\u0432.\n      </p>\n    </div>\n  </div>\n  <div class="col-md-3">\n    <div class="marketing__third">\n      <h2>\u0414\u043E\u0441\u0442\u0430\u0432\u043A\u0430</h2>\n      <p>\n        \u0412\u0430\u0448\u0435 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F \u0431\u0443\u0434\u0435 \u0434\u043E\u0441\u0442\u0430\u0432\u043B\u0435\u043D\u043E \u0432 \u0443\u0437\u0433\u043E\u0434\u0436\u0435\u043D\u0438\u0439 \u0437 \u0412\u0430\u043C\u0438 \u0447\u0430\u0441.\n      </p>\n    </div>\n  </div>\n  <div class="col-md-3">\n    <div class="marketing__fourth">\n      <h2>\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u0430 \u043B\u043E\u044F\u043B\u044C\u043D\u043E\u0441\u0442\u0456</h2>\n      <p>\n      \u041D\u0430\u043A\u043E\u043F\u0438\u0447\u0443\u0439\u0442\u0435 \u0431\u043E\u043D\u0443\u0441\u0438 \u0437\u0430\u043C\u043E\u0432\u043B\u044F\u044E\u0447\u0438 \u0447\u0435\u0440\u0435\u0437 \u043C\u043E\u0431\u0456\u043B\u044C\u043D\u0438\u0439 \u0434\u043E\u0434\u0430\u0442\u043E\u043A \u0442\u0430 \u043D\u0430\u0441\u043E\u043B\u043E\u0434\u0436\u0443\u0439\u0442\u0435\u0441\u044F \u0441\u043F\u0435\u0446\u0456\u0430\u043B\u044C\u043D\u0438\u043C\u0438 \u043F\u0440\u043E\u043F\u043E\u0437\u0438\u0446\u0456\u044F\u043C\u0438.\n      </p>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('html/layouts/nav.tpl.html','<div class="row main-nav-bar">\n  <div class="col-lg-12 col-md-12 col-sm-12">\n    <ul class="nav nav-justified">\n      <li ng-repeat="category in categories" ui-sref-active="active">\n        <a class="btn" ui-sref="main.category({id: category.id})"\n           ng-bind="category.name">\n        </a>\n      </li>\n    </ul>\n  </div>\n</div>\n');
$templateCache.put('html/layouts/sidebar-order.tpl.html','<div class="step">\n  <form name="orderForm" ng-submit="sbcar.send(orderForm)" novalidate>\n    <img ng-src="img/waiter.png" alt="waiter">\n\n    <p class="top-text text-center">\u0412\u0432\u0435\u0434\u0456\u0442\u044C \u0441\u0432\u043E\u0457 \u043E\u0441\u043E\u0431\u0438\u0441\u0442\u0456 \u0434\u0430\u043D\u0456<br>\u0449\u043E\u0431 \u043E\u0444\u043E\u0440\u043C\u0438\u0442\u0438 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F\n      </p>\n\n    <div class="input"\n         ng-class="{\'error\': orderForm.name.$error.required && orderForm.name.$dirty}">\n      <input type="text" name="name" placeholder="\u0406\u043C\'\u044F"\n             ng-model="sbcar.form.client.name"\n             required>\n      <div class="required">*</div>\n    </div>\n\n    <div class="input"\n         ng-class="{\'error\': orderForm.phone.$error.required && orderForm.phone.$dirty}">\n      <input type="tel"\n             name="phone"\n             placeholder="\u0422\u0435\u043B\u0435\u0444\u043E\u043D"\n             ng-model="sbcar.form.client.phone"\n             ui-mask="(999) 999-9999"\n             ui-options="{clearOnBlur: false}"\n             ui-mask-placeholder\n             ui-mask-placeholder-char\n             required>\n      <div class="required">*</div>\n    </div>\n\n    <p align="left" class="bottom-text">\n      \u0417\u0430\u043F\u043E\u0432\u043D\u0456\u0442\u044C, \u0431\u0443\u0434\u044C \u043B\u0430\u0441\u043A\u0430, \u043F\u043E\u043B\u044F, \u0449\u043E \u043F\u043E\u0434\u0430\u043D\u0456 \u0432\u0438\u0449\u0435 \u0456 \u0434\u043E \u0412\u0430\u0441 \u0437\u0430\u0442\u0435\u043B\u0435\u0444\u043E\u043D\u0443\u044E \u043E\u043F\u0435\u0440\u0430\u0442\u043E\u0440 \u0449\u043E\u0431 \u043F\u0456\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0438 \u0412\u0430\u0448\u0435 \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F.<br><span>* \u0422\u0456\u043B\u044C\u043A\u0438 \u0440\u0435\u0430\u043B\u044C\u043D\u0456 \u0434\u0430\u043D\u0456</span>\n    </p>\n\n    <div class="text-center">\n      <button type="submit" ng-disabled="orderForm.$invalid">\n        <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>\n        <span>\u0417\u0430\u043C\u043E\u0432\u0438\u0442\u0438</span>\n      </button>\n    </div>\n  </form>\n</div>\n');
$templateCache.put('html/layouts/sidebar.tpl.html','<div class="row dishes dishes-first-row">\n  <div class="col-lg-12 col-md-12 col-sm-12">\n    <div class="total-and-legend">\n      <div class="total">\n        <p>\u0412\u0410\u0428\u0415 \u0417\u0410\u041C\u041E\u0412\u041B\u0415\u041D\u041D\u042F</p>\n\n        <div class="cart-body" ng-if="sbar.dishes.length">\n          <div class="total-cart">\n            <span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>\n                        <span>\u041D\u0430\u0440\u0430\u0437\u0456 \u0432 \u043A\u043E\u0440\u0437\u0438\u043D\u0456: <strong\n                            ng-bind="sbar.dishes.length"></strong> \u0448\u0442</span>\n          </div>\n\n          <ul>\n            <li ng-repeat="dish in sbar.dishes track by $index" class="row">\n              <div class="col-md-7 col-sm-7 col-xs-7" ng-bind="dish.name"></div>\n              <div class="col-md-5 col-sm-5 col-xs-5 cart-control">\n                <a ng-click="sbar.decCount(dish)">-</a>\n                <span class="dish-count" ng-bind="dish.count"></span>\n                <a ng-click="sbar.incCount(dish)">+</a>\n                <a class="dish-delete" ng-click="sbar.removeDish(dish)">&#215;</a>\n              </div>\n            </li>\n          </ul>\n\n          <p class="total-price">\u0417\u0430\u0433\u0430\u043B\u044C\u043D\u0430 \u0441\u0443\u043C\u0430: <strong ng-bind="sbar.priceOrder"></strong> \u0413\u0440\u043D\n          </p>\n          <a class="btn btn-block btn-primary" ui-sref="main.cart">\u041E\u0444\u043E\u0440\u043C\u0438\u0442\u0438</a>\n        </div>\n\n        <span ng-if="!sbar.dishes.length">\u0412\u0438 \u0449\u0435 \u043D\u0435 \u0437\u0430\u043C\u043E\u0432\u0438\u043B\u0438 \u0436\u043E\u0434\u043D\u043E\u0457 \u0441\u0442\u0440\u0430\u0432\u0438</span>\n      </div>\n      <div class="lable-legend">\n        <img src="img/new-lable.png" alt="new"/> <span> - \u041D\u043E\u0432\u0438\u043D\u043A\u0430</span><br>\n        <img src="img/hot-lable.png" alt="hot"/> <span> - \u0413\u043E\u0441\u0442\u0440\u0430 \u0441\u0442\u0440\u0430\u0432\u0430</span><br>\n        <img src="img/veggie-lable.png" alt="veggie"/>\n        <span> - \u0412\u0435\u0433\u0435\u0442\u0430\u0440\u0456\u0430\u043D\u0441\u044C\u043A\u0430 \u0441\u0442\u0440\u0430\u0432\u0430</span><br>\n      </div>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('html/components/cart/cart.tpl.html','<div class="cart col-md-12" ng-if="cart.dishes.length">\n  <div class="cart-info">\n    <div class="left">\n      <i class="glyphicon glyphicon-shopping-cart"></i>\n      <span>\u041D\u0430\u0440\u0430\u0437\u0456 \u0432 \u043A\u043E\u0440\u0437\u0438\u043D\u0456: <strong ng-bind="cart.orderInfo.count"></strong> \u0448\u0442 |</span>\n      <span class="red-info"> \u0417\u0430\u0433\u0430\u043B\u044C\u043D\u0430 \u0441\u0443\u043C\u0430: <strong\n          ng-bind="cart.orderInfo.price"></strong> \u0433\u0440\u043D</span>\n    </div>\n    <div class="right">\n      <span>\u043A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C</span>\n      <span>\u0446\u0456\u043D\u0430</span>\n    </div>\n  </div>\n  <div class="cart-dish" ng-repeat="dish in cart.dishes track by $index">\n    <div class="left">\n      <div class="flag">\n        <div ng-switch="dish.type">\n          <img ng-src="img/new-lable.png" ng-switch-when="LATEST">\n          <img ng-src="img/hot-lable.png" ng-switch-when="HOT">\n          <img ng-src="img/veggie-lable.png" ng-switch-when="VEGAN">\n        </div>\n      </div>\n      <div class="sm-img">\n        <img data-ng-src="{{\'data:image/png;base64,\'+dish.images[0]}}"\n             class="img-responsive"\n             ng-if="dish.images.length">\n      </div>\n      <p ng-bind="dish.name"></p>\n    </div>\n    <div class="right">\n      <div class="numerical">\n        <span ng-click="cart.decCount(dish)">-</span>\n          {{dish.count}}\n        <span ng-click="cart.incCount(dish)">+</span>\n      </div>\n      <div class="price red-info" ng-bind="dish.price * dish.count"></div>\n      <a ng-click="cart.removeDish(dish)"><i\n          class="glyphicon glyphicon-remove"></i></a>\n    </div>\n  </div>\n  <div class="cart-general">\n    <div class="left">\n      <div class="dish-clear">\n        <i class="glyphicon glyphicon-remove"></i>\n        <a ng-click="cart.clearCart()">\u041E\u0447\u0438\u0441\u0442\u0438\u0442\u0438 \u043A\u043E\u0440\u0437\u0438\u043D\u0443</a>\n      </div>\n      <span>\u0412\u0441\u044C\u043E\u0433\u043E:</span>\n    </div>\n    <div class="right">\n      <span ng-bind="cart.orderInfo.count | postfix: \'\u0448\u0442\'"></span>\n      <strong ng-bind="cart.orderInfo.price | postfix: \'\u0433\u0440\u043D\'"></strong>\n    </div>\n  </div>\n</div>\n<div ng-if="!cart.dishes.length" class="well">\u041A\u043E\u0440\u0437\u0438\u043D\u0430 \u043F\u043E\u0440\u043E\u0436\u043D\u044F</div>\n');
$templateCache.put('html/components/category/category.tpl.html','<div class="row dishes dishes-first-row" ng-repeat="row in cat.dishes">\n  <div class="col-lg-4 col-md-4 col-sm-4" ng-repeat="dish in row track by $index">\n    <div class="dish">\n\n      <image-slider images="dish.images"\n                    click="cat.showDishDetail(dish)"></image-slider>\n\n      <div class="dish-description">\n        <span class="dish-description" ng-bind="dish.name"></span>\n\n        <div ng-switch="dish.type">\n          <img ng-src="img/new-lable.png" ng-switch-when="LATEST">\n          <img ng-src="img/hot-lable.png" ng-switch-when="HOT">\n          <img ng-src="img/veggie-lable.png" ng-switch-when="VEGAN">\n        </div>\n      </div>\n      <div class="dish-price-and-rest">\n        <span class="price" ng-bind="dish.price | postfix:\'\u0433\u0440\u043D\'"></span>\n        <input type="button"\n               class="btn btn-default without-hover pull-right good-btn"\n               name="orderButton"\n               value="\u0417\u0430\u043C\u043E\u0432\u0438\u0442\u0438" add-cart dish="dish">\n      </div>\n    </div>\n  </div>\n</div>\n\n<div ng-show="!cat.dishes.length && !loadingSpinner" class="well">\u0421\u0442\u0440\u0430\u0432\u0438 \u043D\u0435\n  \u0431\u0443\u043B\u0438 \u0437\u043D\u0430\u0439\u0434\u0435\u043D\u0456</div>\n\n<div class="row showMore-button" ng-show="cat.pagination.hasEnabled()">\n  <div\n      class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-5 col-md-offset-5 col-sm-offset-5">\n    <a class="btn btn-primary" ng-click="cat.getMoreDishes()">\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u0438 \u0449\u0435</a>\n  </div>\n</div>\n');
$templateCache.put('html/components/home/home.tpl.html','<div class="row dishes dishes-first-row" ng-repeat="row in home.dishes">\n  <div class="col-lg-4 col-md-4 col-sm-4"\n       ng-repeat="dish in row track by $index">\n    <div class="dish">\n\n      <image-slider images="dish.images"\n                    click="home.showDishDetail(dish)"></image-slider>\n\n      <div class="dish-description">\n        <span class="dish-description" ng-bind="dish.name"></span>\n\n        <div ng-switch="dish.type">\n          <img ng-src="img/new-lable.png" ng-switch-when="LATEST">\n          <img ng-src="img/hot-lable.png" ng-switch-when="HOT">\n          <img ng-src="img/veggie-lable.png" ng-switch-when="VEGAN">\n        </div>\n      </div>\n      <div class="dish-price-and-rest">\n        <span class="price" ng-bind="dish.price | postfix:\'\u0433\u0440\u043D\'"></span>\n        <input type="button"\n               class="btn btn-default without-hover pull-right good-btn"\n               name="orderButton"\n               value="\u0417\u0430\u043C\u043E\u0432\u0438\u0442\u0438" add-cart dish="dish">\n      </div>\n    </div>\n  </div>\n</div>\n\n<div ng-show="!home.dishes.length && !loadingSpinner" class="well">\u0421\u0442\u0440\u0430\u0432\u0438 \u043D\u0435\n  \u0431\u0443\u043B\u0438 \u0437\u043D\u0430\u0439\u0434\u0435\u043D\u0456</div>\n\n<div class="row showMore-button" ng-show="home.pagination.hasEnabled()">\n  <div\n      class="col-lg-2 col-md-2 col-sm-2 col-lg-offset-5 col-md-offset-5 col-sm-offset-5">\n    <a class="btn btn-primary" ng-click="home.getMoreDishes()">\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u0438 \u0449\u0435</a>\n  </div>\n</div>\n');
$templateCache.put('html/components/order/order.tpl.html','<div class="step" ng-show="order.step === 1">\n  <img ng-src="img/timer.png" alt="timer">\n  <p class="top-text text-center">\u0414\u044F\u043A\u0443\u0454\u043C\u043E, <span\n      ng-bind="order.client.name"></span>, <br>\u043F\u0440\u043E\u0442\u044F\u0433\u043E\u043C 2 \u0445\u0432\u0438\u043B\u0438\u043D\n      \u0437 \u0412\u0430\u043C\u0438 \u0437\u0432\u2019\u044F\u0436\u0435\u0442\u044C\u0441\u044F <br> \u043D\u0430\u0448 \u043E\u043F\u0435\u0440\u0430\u0442\u043E\u0440  \u0434\u043B\u044F \u0443\u0442\u043E\u0447\u043D\u0435\u043D\u043D\u044F <br> \u0432\u0441\u0456\u0445 \u0434\u0435\u0442\u0430\u043B\u0435\u0439 \u0412\u0430\u0448\u043E\u0433\u043E \u0437\u0430\u043C\u043E\u0432\u043B\u0435\u043D\u043D\u044F<br></p>\n  <p align="left" class="bottom-text text-center">\n\n  </p>\n\n  <div class="text-center">\n    <button type="submit" class="green-button" ng-click="order.nextStep()">\n      <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>\n      <span>\u0417\u0430\u0442\u0430\u043B\u0435\u0444\u043E\u043D\u0443\u0439\u0442\u0435 \u043C\u0435\u043D\u0456 \u0449\u0435 \u0440\u0430\u0437</span>\n    </button>\n  </div>\n</div>\n\n<div class="step" ng-show="order.step === 2">\n  <img ng-src="img/done.png" alt="order">\n  <p class="text-center order-meta">\n  </p>\n  <div class="order-info">\n    <p>\n      \u0414\u044F\u043A\u0443\u0454\u043C\u043E, \u043E\u0447\u0456\u043A\u0443\u0439\u0442\u0435 \u0434\u0437\u0432\u0456\u043D\u043A\u0430!\n    </p>\n\n    <p class="no-margin">\u0422\u0435\u043B\u0435\u0444\u043E\u043D \u0441\u043B\u0443\u0436\u0431\u0438 \u043F\u0456\u0434\u0442\u0440\u0438\u043C\u043A\u0438<br><strong>0501597777</strong></p>\n  </div>\n\n  <div class="text-center">\n    <button type="submit" class="purple-button"\n            ui-sref="main.category({id: 1})">\n      <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>\n      <span>\u041D\u0430 \u0433\u043E\u043B\u043E\u0432\u043D\u0443</span>\n    </button>\n  </div>\n</div>\n');
$templateCache.put('html/layouts/core/spinner.html','<div class="text-center positive middle-align fade-in-out" ng-show="loadingSpinner">\n    <div class="loader">\n        <div id="page-loading"></div>\n    </div>\n</div>\n');
$templateCache.put('html/blocks/directive/image-slider/slider.html','<div class="image-slider">\n  <ul class="slides">\n    <li class="slide" ng-repeat="image in images track by $index"\n        ng-hide="!isCurrentIndex($index)">\n      <img ng-click="click()"\n           ng-src="{{\'data:image/png;base64,\'+image}}">\n    </li>\n  </ul>\n\n  <div ng-show="images.length > 1">\n    <a class="btn btn-default pull-right right-nav" ng-click="nextSlide()">\n      <i class="glyphicon glyphicon-chevron-right"></i>\n    </a>\n\n    <a class="btn btn-default pull-left left-nav" ng-click="prevSlide()">\n      <i class="glyphicon glyphicon-chevron-left"></i>\n    </a>\n  </div>\n</div>\n');}]);