import { BrowserModule } from '@angular/platform-browser';
import { NgModule,CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import {HttpModule, Http, XHRBackend, RequestOptions} from '@angular/http';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import {DataTableModule, MultiSelectModule , SharedModule , DialogModule , ButtonModule , CalendarModule ,CheckboxModule ,InputTextModule,
        InputTextareaModule,   DropdownModule, InputSwitchModule , RadioButtonModule ,
        RatingModule, AccordionModule} from 'primeng/primeng';
import { RouterModule, Routes } from '@angular/router';
import {SCComponent } from './pages/sc/sc.component';
import { PRComponent } from './pages/pr/pr.component';
import { projectSelectComponent } from './pages/projectSelect/projectSelect.component';

import { DMComponent } from './pages/DM/DM.component';
import { RIDM_Component } from './pages/RIDM/RIDM.component';
import { AD_Component } from './pages/AD/AD.component';
import { CM_Component } from './pages/CM/CM.component';
import { CAR_Component} from './pages/CAR/CAR.component';
import { HOComponent } from './pages/HO/HO.component';
import { PQPPComponent } from './pages/pqpp/pqpp.component';
import { SRComponent } from './pages/sr/sr.component';
import { DARComponent } from './pages/dar/dar.component';
import {APMComponent} from './pages/apm/apm.component';
import {TCDRComponent}  from './pages/TCDR/TCDR.component';
import{router , routes } from './main/router.component';
import{commonAPIservice} from './providers/commonServices';
import {httpFactory} from "./providers/http.factory";
import{ValidationService , ControlMessagesComponent} from './providers/validationService';
import{fieldInput} from './shared/components/formInput/formInput'
import { LoginComponent } from './pages/login/login.component';
import {Loader} from './shared/components/Loading.component';
import {ModalComponent} from './shared/components/modal.component';
import {ProcessViewComponent} from './pages/Processview/ProcessView.component';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown/angular2-multiselect-dropdown';
import {MenuComponent}  from './shared/components/menu.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SCComponent,
    projectSelectComponent,
    PRComponent,
    DMComponent,
    PQPPComponent,
    RIDM_Component,
    AD_Component,
    CM_Component,
    CAR_Component,
    HOComponent,
    TCDRComponent,
    ControlMessagesComponent,
    SRComponent,
    APMComponent,
    DARComponent,
    ProcessViewComponent,
    fieldInput,
    Loader,
    ModalComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    RouterModule,
    routes,
    MultiSelectModule,
    BrowserAnimationsModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    DataTableModule,
    SharedModule,
    CalendarModule,
    CheckboxModule,
    DropdownModule,
    InputSwitchModule,
    InputTextareaModule,
    RatingModule,
    RadioButtonModule,
    AccordionModule,
    AngularMultiSelectModule,
    RouterModule.forRoot(router, {useHash: false})
  ],
  providers: [
    commonAPIservice ,
    ValidationService,
    {
            provide: Http,
            useFactory: httpFactory,
            deps: [XHRBackend, RequestOptions]
        }
    ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
