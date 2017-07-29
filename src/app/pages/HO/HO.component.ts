import { Component  , Injectable , OnInit , OnChanges} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';



@Component({
  selector: 'HO-app',
  templateUrl: './HO.html'
})




export class HOComponent   implements OnInit {
  rForm: FormGroup;
  PRList:any;
  manualHelpDoc: FormControl;
  hoResponsibilityGDC: FormControl;
  knowledgeTransferMode: FormControl;
  resourcePlanningBacklog: FormControl;
  applicable: FormControl;


 constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice , private http: Http) {

  this.manualHelpDoc            = new FormControl('', Validators.compose([Validators.required]));
  this.hoResponsibilityGDC = new FormControl('', Validators.compose([Validators.required]));
  this.knowledgeTransferMode            = new FormControl('', Validators.compose([Validators.required]));
  this.resourcePlanningBacklog         = new FormControl('', Validators.compose([Validators.required]));
  this.applicable         = new FormControl('', Validators.compose([Validators.required]));
  





  this.rForm = this._fb.group({
          "handOffEntity" : this._fb.group({
              'hoResponsibilityGDC'           : this.hoResponsibilityGDC,
              'manualHelpDoc': this.manualHelpDoc,
              'applicable'          : this.applicable
          }),
          "knowledgeTransferEntity"      : this._fb.group({
              'knowledgeTransferMode'  : this.knowledgeTransferMode,
              'resourcePlanningBacklog'      : this.resourcePlanningBacklog,
              'applicable'          : this.applicable
              
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

