import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';

@Component({
  selector: 'app-CAR',
  templateUrl: './CAR.html'
})

export class CAR_Component   implements OnInit {
  rForm: FormGroup;
  CAR_List:any;
  carArtifacts: FormControl;
  applicable: FormControl;




  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice) {





this.carArtifacts = new FormControl('', Validators.compose([Validators.required]));
this.applicable = new FormControl('', Validators.compose([Validators.required]));





  this.rForm = this._fb.group({
                "carArtifactsEntity" : this._fb.group({
                  'carArtifacts': this.carArtifacts,
                  'applicable': this.applicable
                })
  });
  }

  ngOnInit() {
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.CAR_List = res.entity ;
    });
  }

  onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.CAR_List).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
  }





}
