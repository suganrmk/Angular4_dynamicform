import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';

@Component({
  selector: 'app-AD',
  templateUrl: './AD.html'
})

export class AD_Component implements OnInit {
  rForm: FormGroup;
  AD_List:any;
  reusableComponents: FormControl;
  alternativeDesignEvaluation: FormControl;
  designTool: FormControl;
  designReviewOutcome: FormControl;
  designRepository: FormControl;
  lowLevelDesign: FormControl;
  gdcInvolvement: FormControl;
  applicable: FormControl;




  constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice) {



    this.reusableComponents = new FormControl('', Validators.compose([Validators.required]));
    this.alternativeDesignEvaluation = new FormControl('', Validators.compose([Validators.required]));
    this.designTool = new FormControl('', Validators.compose([Validators.required]));
    this.designReviewOutcome = new FormControl('', Validators.compose([Validators.required]));
    this.designRepository = new FormControl('', Validators.compose([Validators.required]));
    this.lowLevelDesign = new FormControl('', Validators.compose([Validators.required]));
    this.gdcInvolvement = new FormControl('', Validators.compose([Validators.required]));
    this.applicable = new FormControl('', Validators.compose([Validators.required]));





    this.rForm = this._fb.group({
        "architectureDesignEntity" : this._fb.group({
            'reusableComponents': this.reusableComponents,
            'alternativeDesignEvaluation': this.alternativeDesignEvaluation,
            'designTool': this.designTool,
            'designReviewOutcome': this.designReviewOutcome,
            'designRepository': this.designRepository,
            'lowLevelDesign': this.lowLevelDesign,
            'gdcInvolvement': this.gdcInvolvement,
            'applicable': this.applicable
        })
      });
    }

  ngOnInit() {
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.AD_List = res.entity ;
    });
  }


  onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.AD_List).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
}
}
