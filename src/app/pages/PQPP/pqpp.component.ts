import { Component  , Injectable , OnInit , OnChanges} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';



@Component({
  selector: 'PQPP-app',
  templateUrl: './pqpp.html'
})

 


export class PQPPComponent   implements OnInit {
  myform: FormGroup;
  Project:String ;
  Cam1: string;
  Cam2: string;
  CamMembers: SelectItem[];
  SEPGApprover : string;
  AccountName: string;
  rForm: FormGroup;
  PRList:any;

  editForm: FormGroup;
  defectMetricsGoal: FormControl;
  prodctvtyMetricsGoal: FormControl;
  qualityMetricsGoal: FormControl;
  reviewStatus: FormControl;
  testMetricsGoal: FormControl;
  Automation: FormControl;
  ReviewTaskTracking: FormControl;
  prjctImprvmntPlan: FormControl;
  spcAnalysis: FormControl;
  sqaAuditCycle: FormControl;
  wrkPrdctAudit: FormControl;
  intrnlProcessAudit: FormControl;
   
   
  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice) {
   
   this.CamMembers = [
                        {label:'Select Member', value:null},
                        {label:'Member Perf - 1', value:'Member Perf -  1'},
                        {label:'Member Perf - 2', value:'Member Perf -  2'}                        
                       ]
              
      



this.defectMetricsGoal = new FormControl('', Validators.compose([Validators.required]));
this.prodctvtyMetricsGoal = new FormControl('', Validators.compose([Validators.required]));
this.qualityMetricsGoal = new FormControl('', Validators.compose([Validators.required]));
this.reviewStatus = new FormControl('', Validators.compose([Validators.required]));
this.testMetricsGoal = new FormControl('', Validators.compose([Validators.required]));
this.prjctImprvmntPlan = new FormControl('', Validators.compose([Validators.required]));
this.spcAnalysis = new FormControl('', Validators.compose([Validators.required]));
this.sqaAuditCycle = new FormControl('', Validators.compose([Validators.required]));
this.wrkPrdctAudit = new FormControl('', Validators.compose([Validators.required]));






this.rForm = this._fb.group({
              "pqppMetricsCollection" : this._fb.group({
                'defectMetricsGoal': this.defectMetricsGoal,
                'prodctvtyMetricsGoal': this.prodctvtyMetricsGoal,
                'qualityMetricsGoal': this.qualityMetricsGoal,
                'reviewStatus': this.reviewStatus,
                'testMetricsGoal': this.testMetricsGoal
              }),
              "pqppSpcAnalysis" : this._fb.group({
                'spcAnalysis': this.spcAnalysis,
              }),
              "pqppProcessPrfmncModel" : this._fb.group({
                'prjctImprvmntPlan': this.prjctImprvmntPlan,
                'sqaAuditCycle': this.sqaAuditCycle,
                'wrkPrdctAudit': this.wrkPrdctAudit,
                'intrnlProcessAudit': this.intrnlProcessAudit,
                
              })
});







   }
    ngOnInit() { 
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.PRList = res.entity ; 
    });
  }

  onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.PRList).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
  }

  chk(event){
    console.log(event)
   
  }

}