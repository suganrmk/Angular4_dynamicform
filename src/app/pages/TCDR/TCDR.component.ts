import { Component  , Injectable , OnInit , OnChanges} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';



@Component({
  selector: 'TCDR-app',
  templateUrl: './TCDR.html'
})




export class TCDRComponent   implements OnInit , OnChanges{
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
  frequency: FormControl;
  TeamReviewpeerReview: FormControl;
  GDCReview: FormControl;
  EnableReview: FormControl;
  ReviewComentTracking: FormControl;
  Automation: FormControl;
  ReviewTaskTracking: FormControl;
  ReviewCriteria: FormControl;
  ReviewPlanTracking: FormControl;

   
   
   
  constructor( private _fb: FormBuilder , private SqaService: commonAPIservice) {
   
   this.CamMembers = [
                        {label:'Select Member', value:null},
                        {label:'Member Perf - 1', value:'Member Perf -  1'},
                        {label:'Member Perf - 2', value:'Member Perf -  2'}                        
                       ]
              
      



this.frequency = new FormControl('', Validators.compose([Validators.required]));
this.TeamReviewpeerReview = new FormControl('', Validators.compose([Validators.required]));
this.GDCReview = new FormControl('', Validators.compose([Validators.required]));
this.EnableReview = new FormControl('', Validators.compose([Validators.required]));
this.ReviewComentTracking = new FormControl('', Validators.compose([Validators.required]));
this.Automation = new FormControl('', Validators.compose([Validators.required]));
this.ReviewTaskTracking = new FormControl('', Validators.compose([Validators.required]));
this.ReviewCriteria = new FormControl('', Validators.compose([Validators.required]));
this.ReviewPlanTracking = new FormControl('', Validators.compose([Validators.required]));





this.rForm = this._fb.group({
              "continuosCodeReview" : this._fb.group({
                'frequency': this.frequency,
                'TeamReviewpeerReview': this.TeamReviewpeerReview,
                'GDCReview': this.GDCReview,
                'EnableReview': this.EnableReview,
                'ReviewComentTracking': this.ReviewComentTracking,
                'Automation': this.Automation
              }),
              "peerReviewPlan" : this._fb.group({
                'ReviewTaskTracking': this.ReviewTaskTracking,
                'ReviewCriteria': this.ReviewCriteria,
                'ReviewPlanTracking': this.ReviewPlanTracking
              })
});







   }
     ngOnChanges() {
   //   this.formSet();
     }

   ngOnInit() {

     //this.SqaService.getSqa('').subscribe(  (res) => this.PRList = res);
     

     

 
    this.myform = new FormGroup({
     
     Project: new FormControl('', [ Validators.required]),
     Cam1: new FormControl('', [ Validators.required]),
     Cam2: new FormControl('', [ Validators.required]),
     SEPGApprover: new FormControl('', [ Validators.required]),
     AccountName: new FormControl('', [ Validators.required]),
          
    });



  


   }

   onSubmit(post){
   //console.log(post);
   console.log(this.PRList);

   }



 

}

