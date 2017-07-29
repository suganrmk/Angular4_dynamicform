import { Component  , Injectable , OnInit , OnChanges , Input} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';

import{ValidationService , ControlMessagesComponent} from '../../providers/validationService'


@Component({
  selector: 'SR-app',
  templateUrl: './sr.html'
})




export class SRComponent   implements OnInit , OnChanges{
  @Input() pageTitle: string = "Status Reporting";
  rForm: FormGroup;
  SRList:any;
  statusReporting:any;
  statusReportPattern: FormControl;
  statusTracking: FormControl;
  statusCommunicationMode: FormControl;
  pmoDashboardUpdate: FormControl; 
  applicable: FormControl;

  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice , private http: Http) {


    this.statusReportPattern = new FormControl('', Validators.compose([Validators.required]));
    this.statusTracking = new FormControl('', Validators.compose([Validators.required]));
    this.statusCommunicationMode = new FormControl('', Validators.compose([Validators.required]));
    this.pmoDashboardUpdate = new FormControl('', Validators.compose([Validators.required]));
    this.applicable           = new FormControl('', Validators.compose([Validators.required]));

    


    this.rForm = this._fb.group({
      "statusReporting" : this._fb.group({
        'statusReportPattern': this.statusReportPattern,
        'statusTracking': this.statusTracking,
        'statusCommunicationMode': this.statusCommunicationMode,
        'pmoDashboardUpdate': this.pmoDashboardUpdate,
        'applicable'          : this.applicable
      })                      
    });


  }
  ngOnChanges() {
  }

  ngOnInit() {
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.SRList = res.entity ; 
      console.log(this.SRList.statusReporting); 
    });
   
  }

  onSubmit(post){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.SRList).subscribe();
  }

  chk(event){
    console.log(event);   
  }

}

