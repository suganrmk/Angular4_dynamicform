import { Component  , Injectable , OnInit , OnChanges , Input} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';

import{ValidationService , ControlMessagesComponent} from '../../providers/validationService'


@Component({
  selector: 'DAR-app',
  templateUrl: './dar.html'
})




export class DARComponent   implements OnInit , OnChanges{
  @Input() pageTitle: string = "Decision Analysis Resolution";
  rForm: FormGroup;
  DARList:any;
  decisionAnalysisResolutionArtifacts:any;
  designOrTechSolution: FormControl;
  conflictManagement: FormControl;
  toolSelection: FormControl;
  applicable: FormControl;

  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice , private http: Http) {


    this.designOrTechSolution = new FormControl('', Validators.compose([Validators.required]));
    this.conflictManagement = new FormControl('', Validators.compose([Validators.required]));
    this.toolSelection = new FormControl('', Validators.compose([Validators.required]));
    this.applicable           = new FormControl('', Validators.compose([Validators.required]));

    


    this.rForm = this._fb.group({
      "decisionAnalysisResolutionArtifacts" : this._fb.group({
        'designOrTechSolution': this.designOrTechSolution,
        'conflictManagement': this.conflictManagement,
        'toolSelection': this.toolSelection,
        'applicable'          : this.applicable
      })                      
    });


  }
  ngOnChanges() {
  }

  ngOnInit() {
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.DARList = res.entity ; 
    });
   
  }

 onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.DARList).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
  }

  chk(event){
    console.log(event);   
  }
  
}

