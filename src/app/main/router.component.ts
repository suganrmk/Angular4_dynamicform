import { Component , ModuleWithProviders  } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {LoginComponent } from '../pages/login/login.component';

import { SCComponent } from '../pages/sc/sc.component';
import { PRComponent } from '../pages/pr/pr.component';
import { DMComponent } from '../pages/DM/DM.component';
import { PQPP_Component } from '../pages/pqpp/pqpp.component';
//import { RIDM_Component } from '../pages/RIDM/RIDM.component';
import { AD_Component } from '../pages/AD/AD.component';
import { CM_Component } from '../pages/CM/CM.component';
import { CAR_Component} from '../pages/CAR/CAR.component';
import { SRComponent } from '../pages/sr/sr.component';
import { DARComponent} from '../pages/dar/dar.component';
import { HOComponent } from '../pages/HO/HO.component';
import {APMComponent} from '../pages/apm/apm.component';

import {TCDRComponent}  from '../pages/TCDR/TCDR.component';
import {ProcessViewComponent} from '../pages/Processview/ProcessView.component'






export const router: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'PR', component: PRComponent },
  { path: 'SC', component: SCComponent },
  { path: 'DM', component: DMComponent},
  { path: 'PQPP', component: PQPP_Component  },
  //{ path: 'RIDM', component: RIDM_Component },
  { path: 'AD', component: AD_Component },
  { path: 'CAR', component: CAR_Component },
  { path: 'CM', component: CM_Component },
  { path: 'SR', component: SRComponent},
  { path: 'DM', component: DMComponent},
  { path: 'HO', component: HOComponent},
  { path: 'SR', component: SRComponent},
  { path: 'DAR', component: DARComponent},  
  { path: 'APM' , component: APMComponent },
  { path: 'TCDR' , component: TCDRComponent },
  { path: 'ProcessView' , component: ProcessViewComponent }

 

  // { path: 'PQPP', component: PQPP_Component  },

];




export const routes: ModuleWithProviders = RouterModule.forRoot(router);
