import { Component  , Injectable , OnInit , OnChanges} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';



@Component({
  selector: 'DM-app',
  templateUrl: './DM.html'
})




export class DMComponent   implements OnInit {
  rForm: FormGroup;
  PRList:any;
  editForm: FormGroup;
  defManagementByGDC: FormControl;
  defTrackingTool: FormControl;
applicable: FormControl;
   
   
   
  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice) {

      



this.defManagementByGDC = new FormControl('', Validators.compose([Validators.required]));
this.defTrackingTool = new FormControl('', Validators.compose([Validators.required]));
this.applicable = new FormControl('', Validators.compose([Validators.required]));






this.rForm = this._fb.group({
              "defectTracking" : this._fb.group({
                'defManagementByGDC': this.defManagementByGDC,
                'defTrackingTool': this.defTrackingTool,
                'applicable': this.applicable
                
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



 

}

