import { Component  , Injectable , OnInit , OnChanges , Input , Output , EventEmitter} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';
import{ValidationService , ControlMessagesComponent} from '../../providers/validationService'


@Component({
  selector: 'PR-app',
  templateUrl: './pr.html'
})




export class PRComponent   implements OnInit{
  @Input() pageTitle   : string = "Peer Review2";
  @Input() test: any;
  rForm                : FormGroup;
  PRList               : any;
  continuosCodeReview  : any;
  frequency            : FormControl;
  TeamReviewpeerReview : FormControl;
  GDCReview            : FormControl;
  EnableReview         : FormControl;
  ReviewComentTracking : FormControl;
  Automation           : FormControl;
  ReviewTaskTracking   : FormControl;
  ReviewCriteria       : FormControl;
  ReviewPlanTracking   : FormControl;
  applicable           : FormControl;
  applicable2          : FormControl;
  subprocess1          : any;
  subprocess2          : any;
  
display: boolean

 constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice , private http: Http) {
  

  this.frequency            = new FormControl('', Validators.compose([Validators.required]));
  this.TeamReviewpeerReview = new FormControl('', Validators.compose([Validators.required]));
  this.GDCReview            = new FormControl('', Validators.compose([Validators.required]));
  this.EnableReview         = new FormControl('', Validators.compose([Validators.required]));
  this.ReviewComentTracking = new FormControl('', Validators.compose([Validators.required]));
  this.Automation           = new FormControl('', Validators.compose([Validators.required]));
  this.ReviewTaskTracking   = new FormControl('', Validators.compose([Validators.required]));
  this.ReviewCriteria       = new FormControl('', Validators.compose([Validators.required]));
  this.ReviewPlanTracking   = new FormControl('', Validators.compose([Validators.required]));
  this.applicable           = new FormControl('', Validators.compose([Validators.required]));
  this.applicable2          = new FormControl('', Validators.compose([Validators.required]));
  





  this.rForm = this._fb.group({
          "continuosCodeReview" : this._fb.group({
              'frequency'           : this.frequency,
              'TeamReviewpeerReview': this.TeamReviewpeerReview,
              'GDCReview'           : this.GDCReview,
              'EnableReview'        : this.EnableReview,
              'ReviewComentTracking': this.ReviewComentTracking,
              'Automation'          : this.Automation,
              'applicable'          : this.applicable
          }),
          "peerReviewPlan"      : this._fb.group({
              'ReviewTaskTracking'  : this.ReviewTaskTracking,
              'ReviewCriteria'      : this.ReviewCriteria,
              'ReviewPlanTracking'  : this.ReviewPlanTracking,
              'applicable'          : this.applicable
          })
  });
    
 }




  ngOnInit() { 
    console.log(this.test)

    this.commonAPIservice.get().subscribe(  (res) => {
    this.PRList = res.entity ; 
     
     if(this.PRList){
     this.subprocess1 = [
          {
            fieldvalue:this.PRList.continuosCodeReview.frequency,
            fieldtext: 'frequency',
            control:'frequency'
          },
          {
            fieldvalue:this.PRList.continuosCodeReview.teamPeerReview,
            fieldtext: 'Team Review/Peer Review',
            control:'TeamReviewpeerReview'
          },
          {
            fieldvalue:this.PRList.continuosCodeReview.reviewFromGDC,
            fieldtext: 'Reviewer from GDC',
            control:'GDCReview'
          },
          {
            fieldvalue:this.PRList.continuosCodeReview.enableLatestReviewTemplate,
            fieldtext: 'Enable 2.0 Review Template',
            control:'EnableReview'
          },
          {
            fieldvalue:this.PRList.continuosCodeReview.reviewCommentTracking,
            fieldtext: 'Review Comment Tracking',
            control:'ReviewComentTracking'
          },
          {
            fieldvalue:this.PRList.continuosCodeReview.automated,
            fieldtext: 'Manual/Automated',
            control:'Automation'
          }
      ];
      
      this.subprocess2 = [
              {
              fieldvalue:this.PRList.peerReviewPlan.reviewTimeTracking,
              fieldtext: 'Review Task Tracking',
              control:'ReviewTaskTracking'
            },
            {
              fieldvalue:this.PRList.peerReviewPlan.reviewCriteriaDefinedBy,
              fieldtext: 'Review Criteria Defined by',
              control:'ReviewCriteria'
            },
            {
              fieldvalue:this.PRList.peerReviewPlan.reviewPalnTrackingSystem,
              fieldtext: 'Review Plan Tracking System',
              control:'ReviewPlanTracking'
            }
        ]
    }
    });
  }

  onSubmit(event , a , form){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.PRList).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
  }

}

