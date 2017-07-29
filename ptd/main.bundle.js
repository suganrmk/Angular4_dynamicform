webpackJsonp([2,5],{

/***/ 103:
/***/ (function(module, exports) {

function webpackEmptyContext(req) {
	throw new Error("Cannot find module '" + req + "'.");
}
webpackEmptyContext.keys = function() { return []; };
webpackEmptyContext.resolve = webpackEmptyContext;
module.exports = webpackEmptyContext;
webpackEmptyContext.id = 103;


/***/ }),

/***/ 104:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__(115);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__(118);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__(120);




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["enableProdMode"])();
}
__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 117:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = (function () {
    function AppComponent() {
        this.title = 'app works!';
    }
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-root',
        template: __webpack_require__(251),
        styles: [__webpack_require__(178)]
    })
], AppComponent);

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ 118:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__(21);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__(69);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_platform_browser_animations__ = __webpack_require__(116);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_component__ = __webpack_require__(117);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__ = __webpack_require__(248);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_primeng_primeng___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_primeng_primeng__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_router__ = __webpack_require__(8);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__pages_home_home_component__ = __webpack_require__(70);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__pages_pr_pr_component__ = __webpack_require__(71);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__main_router_component__ = __webpack_require__(119);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__providers_commonServices__ = __webpack_require__(44);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};












var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["NgModule"])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_5__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_8__pages_home_home_component__["a" /* HomeComponent */],
            __WEBPACK_IMPORTED_MODULE_9__pages_pr_pr_component__["a" /* PRComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["BrowserModule"],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["FormsModule"],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["ReactiveFormsModule"],
            __WEBPACK_IMPORTED_MODULE_7__angular_router__["RouterModule"],
            __WEBPACK_IMPORTED_MODULE_10__main_router_component__["a" /* routes */],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["MultiSelectModule"],
            __WEBPACK_IMPORTED_MODULE_4__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["DialogModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["InputTextModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["ButtonModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["DataTableModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["SharedModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["CalendarModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["CheckboxModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["DropdownModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["InputSwitchModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["InputTextareaModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["RatingModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["RadioButtonModule"],
            __WEBPACK_IMPORTED_MODULE_6_primeng_primeng__["AccordionModule"],
            __WEBPACK_IMPORTED_MODULE_7__angular_router__["RouterModule"].forRoot(__WEBPACK_IMPORTED_MODULE_10__main_router_component__["b" /* router */], { useHash: false })
        ],
        providers: [__WEBPACK_IMPORTED_MODULE_11__providers_commonServices__["a" /* EmployeeService */]],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_5__app_component__["a" /* AppComponent */]]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 119:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__(8);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__pages_home_home_component__ = __webpack_require__(70);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__pages_pr_pr_component__ = __webpack_require__(71);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return router; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return routes; });



var router = [
    { path: '', component: __WEBPACK_IMPORTED_MODULE_2__pages_pr_pr_component__["a" /* PRComponent */] },
    { path: 'PR', component: __WEBPACK_IMPORTED_MODULE_2__pages_pr_pr_component__["a" /* PRComponent */] },
    { path: 'Page2', component: __WEBPACK_IMPORTED_MODULE_1__pages_home_home_component__["a" /* HomeComponent */] }
];
var routes = __WEBPACK_IMPORTED_MODULE_0__angular_router__["RouterModule"].forRoot(router);
//# sourceMappingURL=router.component.js.map

/***/ }),

/***/ 120:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
// The file contents for the current environment will overwrite these during build.
var environment = {
    production: false
};
//# sourceMappingURL=environment.js.map

/***/ }),

/***/ 178:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(24)();
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 251:
/***/ (function(module, exports) {

module.exports = "<div class=\"wrapper\">\n   <header class=\"main-header\">\n      <a href=\"index2.html\" class=\"logo\">\n      <span class=\"logo-mini\">SQA</span>\n    \n      </a>\n\n      <nav class=\"navbar\">\n       <span class=\"pageTitle\"><i class=\"fa fa-file-text-o\"></i>Peer Review Plan</span>\n        <ul class=\"rightMenu\">\n          <li><a href=\"#\"><i class=\"fa fa-home\"></i>Home</a></li>\n          <li><a href=\"#\"><i class=\"fa fa-logout\"></i>Logout</a></li>\n          \n        </ul>\n      </nav>\n     \n   </header>\n   <aside class=\"main-sidebar\">\n      <section class=\"sidebar\">\n         <ul class=\"sidebar-menu\">\n            <br>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>APM</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>RIDM</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>SR</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>TCDR</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>SC</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>AD</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a [routerLink]=\"['/PR']\">\n               <label>PR</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a [routerLink]=\"['/Page2']\" >\n               <label>AST</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>APM</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>DM</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>PQPP</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>HO</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>DAR</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>CAR</label> \n               </a>\n            </li>\n            <li class=\"treeview\">\n               <a href=\"#\">\n               <label>RS</label> \n               </a>\n            </li>\n         </ul>\n      </section>\n   </aside>\n   <div class=\"content-wrapper\">\n     \n      <!-- Main content -->\n     \n    <router-outlet></router-outlet>\n      <!-- /.content -->\n   </div>\n</div>"

/***/ }),

/***/ 252:
/***/ (function(module, exports) {

module.exports = " <!-- Main content -->\r\n\r\n      <section class=\"content\">\r\n         <div class=\"TopSection\">\r\n       <pre>    {{rForm.value.Frequency}}</pre>\r\n        \r\n               <form [formGroup]=\"myform\">\r\n                  <div class=\"row\">\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"Project\">Project</label>\r\n                          <p-dropdown [options]=\"CamMembers\" formControlName=\"Project\" [filter]=\"true\"></p-dropdown>\r\n                           \r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"CAM 1\">CAM 1</label>\r\n                          <p-dropdown [options]=\"CamMembers\" formControlName=\"Cam1\" [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                    <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"CAM 2\">CAM 2</label>\r\n                           <p-dropdown [options]=\"CamMembers\" formControlName=\"Cam2\"  [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"formGroupExampleInput2\">SEPG Approver</label>\r\n                           <p-dropdown [options]=\"CamMembers\" formControlName=\"SEPGApprover\"  [filter]=\"true\"></p-dropdown>\r\n                           \r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"formGroupExampleInput2\">Account</label>\r\n                             <p-dropdown [options]=\"CamMembers\" formControlName=\"AccountName\"  [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                  </div>\r\n               </form>\r\n          \r\n         </div>\r\n    \r\n    <div class=\"formContainer\">\r\n         <form [formGroup]=\"rForm\" (ngSubmit)=\"onSubmit(rForm.value)\">\r\n          \r\n                  <p-accordion>\r\n                     <p-accordionTab header=\"Change Request\" [selected]=\"true\">\r\n                        <div class=\"row\">\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Scope Change Tracking</label>\r\n                                 <input type=\"text\" class=\"form-control\"   pInputText formControlName=\"Frequency\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Owner</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"TeamReviewpeerReview\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Change Approver</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"GDCReview\"  >\r\n                              </div>\r\n                           </div>\r\n\r\n                           \r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Backlog Update</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"EnableReview\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Change  during middle of the sprint</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewComentTracking\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Impact will include</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"Automation\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                            <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Scope change managed by GDC</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"Automation\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                        </div>\r\n                        \r\n                     </p-accordionTab>\r\n                     <p-accordionTab [selected]=\"true\" header=\"Change Log\">\r\n                        <div class=\"row\">\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Change Log Management:</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewTaskTracking\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Change Log Owner:</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewCriteria\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Change Log Approver</label>\r\n                                 <input type=\"text\" class=\"form-control\" pInputText formControlName=\"ReviewPlanTracking\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                        </div>\r\n                        <div class=\"row\">\r\n                        <div class=\"col-md-12 text-right\">\r\n                           <input type=\"submit\" class=\"btn btn-success\"  value=\"Save\" >\r\n                          <button class=\"btn btn-default\">Cancel</button>\r\n                          \r\n                        </div>\r\n                        </div>\r\n\r\n                     </p-accordionTab>\r\n                  </p-accordion>\r\n              \r\n         </form>\r\n         </div>\r\n         <!--<br>\r\n      <div class=\"col-md-12\"> <pre>ssssss{{rForm.value | json}}</pre></div>-->\r\n      </section>\r\n      <!-- /.content -->"

/***/ }),

/***/ 253:
/***/ (function(module, exports) {

module.exports = " <!-- Main content -->\r\n      <section class=\"content\">\r\n         <div class=\"TopSection\">\r\n        \r\n               <form [formGroup]=\"myform\">\r\n                  <div class=\"row\">\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"Project\">Project</label>\r\n                          <p-dropdown [options]=\"CamMembers\" formControlName=\"Project\" [filter]=\"true\"></p-dropdown>\r\n                           \r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"CAM 1\">CAM 1</label>\r\n                          <p-dropdown [options]=\"CamMembers\" formControlName=\"Cam1\" [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                    <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"CAM 2\">CAM 2</label>\r\n                           <p-dropdown [options]=\"CamMembers\" formControlName=\"Cam2\"  [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"formGroupExampleInput2\">SEPG Approver</label>\r\n                           <p-dropdown [options]=\"CamMembers\" formControlName=\"SEPGApprover\"  [filter]=\"true\"></p-dropdown>\r\n                           \r\n                        </div>\r\n                     </div>\r\n                     <div class=\"col-md-15\">\r\n                        <div class=\"form-group\">\r\n                           <label for=\"formGroupExampleInput2\">Account</label>\r\n                             <p-dropdown [options]=\"CamMembers\" formControlName=\"AccountName\"  [filter]=\"true\"></p-dropdown>\r\n                        </div>\r\n                     </div>\r\n                  </div>\r\n               </form>\r\n          \r\n         </div>\r\n    \r\n    <div class=\"formContainer\">\r\n         <form [formGroup]=\"rForm\" (ngSubmit)=\"onSubmit(rForm.value)\">\r\n          \r\n                  <p-accordion>\r\n                     <p-accordionTab header=\"Continues Code Review\" [selected]=\"true\">\r\n                        <div class=\"row\">\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Frequency</label>\r\n                                 <input type=\"text\" class=\"form-control\"   pInputText formControlName=\"Frequency\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Team Review/Peer Review</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"TeamReviewpeerReview\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Reviewer from GDC</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"GDCReview\"  >\r\n                              </div>\r\n                           </div>\r\n\r\n                           \r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Enable 2.0 Review Template</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"EnableReview\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Review Comment Tracking</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewComentTracking\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Manual/Automated</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"Automation\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                        </div>\r\n                        \r\n                     </p-accordionTab>\r\n                     <p-accordionTab [selected]=\"true\" header=\"Peer Code Review\">\r\n                        <div class=\"row\">\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Review Task Tracking:</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewTaskTracking\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Review Criteria Defined by:</label>\r\n                                 <input type=\"text\" class=\"form-control\"  pInputText formControlName=\"ReviewCriteria\" placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                           <div class=\"col-md-4\">\r\n                              <div class=\"form-group\">\r\n                                 <label for=\"formGroupExampleInput\">Review Plan Tracking System</label>\r\n                                 <input type=\"text\" class=\"form-control\" pInputText formControlName=\"ReviewPlanTracking\"  placeholder=\"\">\r\n                              </div>\r\n                           </div>\r\n                        </div>\r\n                        <div class=\"row\">\r\n                        <div class=\"col-md-12 text-right\">\r\n                           <input type=\"submit\" class=\"btn btn-success\"  value=\"Save\" >\r\n                          <button class=\"btn btn-default\">Cancel</button>\r\n                          \r\n                        </div>\r\n                        </div>\r\n\r\n                     </p-accordionTab>\r\n                  </p-accordion>\r\n              \r\n         </form>\r\n         </div>\r\n         <!--<br>\r\n      <div class=\"col-md-12\"> <pre>ssssss{{rForm.value | json}}</pre></div>-->\r\n      </section>\r\n      <!-- /.content -->"

/***/ }),

/***/ 296:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(104);


/***/ }),

/***/ 44:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(69);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__ = __webpack_require__(258);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_toPromise__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return EmployeeService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var EmployeeService = (function () {
    function EmployeeService(http) {
        this.http = http;
    }
    EmployeeService.prototype.getCarsSmall = function () {
        return this.http.get('https://raw.githubusercontent.com/suganrmk/mean/master/employees1.json')
            .toPromise()
            .then(function (res) { return res.json(); })
            .then(function (data) { return data; });
    };
    return EmployeeService;
}());
EmployeeService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]) === "function" && _a || Object])
], EmployeeService);

var _a;
//# sourceMappingURL=commonServices.js.map

/***/ }),

/***/ 70:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__ = __webpack_require__(44);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HomeComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var HomeComponent = (function () {
    function HomeComponent(formBuilder, newService) {
        this.formBuilder = formBuilder;
        this.newService = newService;
        this.GDCReview = "checkn";
        this.CamMembers = [
            { label: 'Select Member', value: null },
            { label: 'Member Perf - 1', value: 'Member Perf -  1' },
            { label: 'Member Perf - 2', value: 'Member Perf -  2' }
        ];
    }
    HomeComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.newService.getCarsSmall().then(function (Employee) { return _this.EmployeeList = Employee; });
        this.myform = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormGroup"]({
            Project: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            Cam1: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            Cam2: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            SEPGApprover: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            AccountName: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
        });
        this.rForm = this.formBuilder.group({
            Frequency: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            TeamReviewpeerReview: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            GDCReview: [this.GDCReview, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            EnableReview: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewComentTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            Automation: [this.EmployeeList, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewTaskTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewCriteria: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewPlanTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
        });
        // this.rForm.setValue(this.EmployeeList)
    };
    HomeComponent.prototype.onSubmit = function (post) {
        console.log(post);
        console.log(this.EmployeeList);
    };
    return HomeComponent;
}());
HomeComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'app-Home',
        template: __webpack_require__(252)
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormBuilder"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormBuilder"]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__["a" /* EmployeeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__["a" /* EmployeeService */]) === "function" && _b || Object])
], HomeComponent);

var _a, _b;
//# sourceMappingURL=home.component.js.map

/***/ }),

/***/ 71:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__ = __webpack_require__(44);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PRComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var PRComponent = (function () {
    function PRComponent(formBuilder, newService) {
        this.formBuilder = formBuilder;
        this.newService = newService;
        this.GDCReview = "checkn";
        this.CamMembers = [
            { label: 'Select Member', value: null },
            { label: 'Member Perf - 1', value: 'Member Perf -  1' },
            { label: 'Member Perf - 2', value: 'Member Perf -  2' }
        ];
    }
    PRComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.newService.getCarsSmall().then(function (Employee) { return _this.EmployeeList = Employee; });
        this.myform = new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormGroup"]({
            Project: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            Cam1: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            Cam2: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            SEPGApprover: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
            AccountName: new __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormControl"]('', [__WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required]),
        });
        this.rForm = this.formBuilder.group({
            Frequency: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            TeamReviewpeerReview: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            GDCReview: [this.GDCReview, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            EnableReview: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewComentTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            Automation: [this.EmployeeList, __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewTaskTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewCriteria: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
            ReviewPlanTracking: ['', __WEBPACK_IMPORTED_MODULE_1__angular_forms__["Validators"].required],
        });
        // this.rForm.setValue(this.EmployeeList)
    };
    PRComponent.prototype.onSubmit = function (post) {
        console.log(post);
        console.log(this.EmployeeList);
    };
    return PRComponent;
}());
PRComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
        selector: 'PR-app',
        template: __webpack_require__(253)
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormBuilder"] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_forms__["FormBuilder"]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__["a" /* EmployeeService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__providers_commonServices__["a" /* EmployeeService */]) === "function" && _b || Object])
], PRComponent);

var _a, _b;
//# sourceMappingURL=pr.component.js.map

/***/ })

},[296]);
//# sourceMappingURL=main.bundle.js.map