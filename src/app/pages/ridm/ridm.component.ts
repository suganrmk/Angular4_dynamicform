import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
@Component({
  selector: 'app-RIDM',
  templateUrl: './RIDM.html'
})

export class RIDM_Component implements OnInit {
  rForm: FormGroup;
  RIDM_List:any;
  comments: FormControl;
  knownRisk: FormControl;
  orgRiskIncl: FormControl;
  riskTrkFreq: FormControl;
  criteriaforRiskPMO: FormControl;
  enable2RiskProcess: FormControl;
  riskTrkSys: FormControl;
  riskMgmtOwn: FormControl;
  applicable: FormControl;




  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice) {



    this.comments = new FormControl('', Validators.compose([Validators.required]));
    this.knownRisk = new FormControl('', Validators.compose([Validators.required]));
    this.orgRiskIncl = new FormControl('', Validators.compose([Validators.required]));
    this.riskTrkFreq = new FormControl('', Validators.compose([Validators.required]));
    this.riskTrkSys = new FormControl('', Validators.compose([Validators.required]));
    this.criteriaforRiskPMO = new FormControl('', Validators.compose([Validators.required]));
    this.enable2RiskProcess = new FormControl('', Validators.compose([Validators.required]));
    this.riskMgmtOwn = new FormControl('', Validators.compose([Validators.required]));
    this.applicable = new FormControl('', Validators.compose([Validators.required]));





    this.rForm = this._fb.group({
        "riskIssuesDependencyTracking" : this._fb.group({
            'comments': this.comments,
            'knownRisk': this.knownRisk,
            'orgRiskIncl': this.orgRiskIncl,
            'riskTrkSys': this.riskTrkSys,
            'riskTrkFreq': this.riskTrkFreq,
            'criteriaforRiskPMO': this.criteriaforRiskPMO,
            'enable2RiskProcess': this.enable2RiskProcess,
            'riskMgmtOwn': this.riskMgmtOwn,
            'applicable': this.applicable
        })
      });
    }

  ngOnInit() {
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.RIDM_List = res.entity ;
    });
  }


  onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.RIDM_List).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
}
}
