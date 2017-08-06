import { Component  , Injectable , OnInit , OnChanges} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';

import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';

 

@Component({
  selector: 'APM-app',
  templateUrl: './APM.html'
})

export class APMComponent   implements OnInit{
  rForm: FormGroup;
  APMList:any;

  projectPlanningTaskTracking:any;
  projectPlanning: FormControl;
  highLevelProjectPlanning: FormControl;
  sprintFrequency: FormControl;
  projectEstimationMethod: FormControl;
  sprintPlanning: FormControl;
  taskUpdateFreq: FormControl;
  onsiteOffsiteOverlap: FormControl;
  releaseFrequency: FormControl;
  releaseplanning: FormControl;
  projectProcess: FormControl;
  dailyScrum: FormControl;
  sprintBacklogManagement: FormControl;
  estimationTrackingTool: FormControl;
  projectDocumentRepository: FormControl;
  meetingMinutes: FormControl;
  tdcInvolvement: FormControl;
  infrastructureRequestManagement: FormControl;
  sprintBaselineCritera: FormControl;
  supportTtaskManagement: FormControl;
  isProjectPlanningTaskTrackingapplicable: FormControl;

  iterationDemo: any;
  demoFrequency: FormControl;
  demoMode: FormControl;
  demoParticipants: FormControl;
  demoFeedbackRecording: FormControl;
  isIterationDemoapplicable: FormControl;

  iterationRetrospective: any;
  retrospectiveDuration: FormControl;
  retrospectiveFrequency: FormControl;
  retrospectiveActionItemTracking: FormControl;
  retrospectiveOutcomeDocumentation: FormControl;
  retrospectiveOwner: FormControl;
  stakeholderInvolvement: FormControl;
  retrospectiveFocusOnAll: FormControl;
  isIterationRetrospectiveapplicable: FormControl;
  
  


 constructor( private _fb: FormBuilder , private commonAPIservice: commonAPIservice , private http: Http) {

  this.projectPlanning            = new FormControl('', Validators.compose([Validators.required]));
  this.highLevelProjectPlanning = new FormControl('', Validators.compose([Validators.required]));
  this.sprintFrequency            = new FormControl('', Validators.compose([Validators.required]));
  this.projectEstimationMethod         = new FormControl('', Validators.compose([Validators.required]));
  this.sprintPlanning = new FormControl('', Validators.compose([Validators.required]));
  this.taskUpdateFreq           = new FormControl('', Validators.compose([Validators.required]));
  this.onsiteOffsiteOverlap   = new FormControl('', Validators.compose([Validators.required]));
  this.releaseFrequency       = new FormControl('', Validators.compose([Validators.required]));
  this.releaseplanning   = new FormControl('', Validators.compose([Validators.required]));  
  this.projectProcess          = new FormControl('', Validators.compose([Validators.required]));
  this.dailyScrum = new FormControl('', Validators.compose([Validators.required]));
  this.sprintBacklogManagement           = new FormControl('', Validators.compose([Validators.required]));
  this.estimationTrackingTool   = new FormControl('', Validators.compose([Validators.required]));
  this.projectDocumentRepository       = new FormControl('', Validators.compose([Validators.required]));
  this.meetingMinutes   = new FormControl('', Validators.compose([Validators.required]));
  this.tdcInvolvement           = new FormControl('', Validators.compose([Validators.required]));
  this.infrastructureRequestManagement          = new FormControl('', Validators.compose([Validators.required]));
  this.sprintBaselineCritera   = new FormControl('', Validators.compose([Validators.required]));
  this.supportTtaskManagement           = new FormControl('', Validators.compose([Validators.required]));
  this.isProjectPlanningTaskTrackingapplicable           = new FormControl('', Validators.compose([Validators.required]));
  this.demoFrequency                      = new FormControl('', Validators.compose([Validators.required]));
  this.demoMode                           = new FormControl('', Validators.compose([Validators.required]));
  this.demoParticipants                   = new FormControl('', Validators.compose([Validators.required]));
  this.demoFeedbackRecording              = new FormControl('', Validators.compose([Validators.required]));
  this.isIterationDemoapplicable          = new FormControl('', Validators.compose([Validators.required]));                    
  this.retrospectiveDuration              = new FormControl('', Validators.compose([Validators.required]));
  this.retrospectiveFrequency             = new FormControl('', Validators.compose([Validators.required]));
  this.retrospectiveActionItemTracking    = new FormControl('', Validators.compose([Validators.required]));
  this.retrospectiveOutcomeDocumentation  = new FormControl('', Validators.compose([Validators.required]));
  this.retrospectiveOwner                 = new FormControl('', Validators.compose([Validators.required]));
  this.stakeholderInvolvement             = new FormControl('', Validators.compose([Validators.required]));
  this.retrospectiveFocusOnAll            = new FormControl('', Validators.compose([Validators.required]));
  this.isIterationRetrospectiveapplicable = new FormControl('', Validators.compose([Validators.required]));




  this.rForm = this._fb.group({
          "projectPlanningTaskTracking" : this._fb.group({
              'projectPlanning'                        : this.projectPlanning,                       
              'highLevelProjectPlanning'               : this.highLevelProjectPlanning,               
              'sprintFrequency'                        : this.sprintFrequency,                        
              'projectEstimationMethod'                : this.projectEstimationMethod,                
              'sprintPlanning'                         : this.sprintPlanning,                         
              'taskUpdateFreq'                         : this.taskUpdateFreq,                         
              'onsiteOffsiteOverlap'                   : this.onsiteOffsiteOverlap,                   
               'releaseFrequency'                      :  this.releaseFrequency,                       
               'releaseplanning'                       :  this.releaseplanning,                        
               'projectProcess'                        :  this.projectProcess,                         
               'dailyScrum'                            :  this.dailyScrum,                            
               'sprintBacklogManagement'               :  this.sprintBacklogManagement,                
               'estimationTrackingTool'                :  this.estimationTrackingTool,                 
               'projectDocumentRepository'             :  this.projectDocumentRepository,              
               'meetingMinutes'                        :  this.meetingMinutes,                         
               'tdcInvolvement'                        :  this.tdcInvolvement,                         
               'infrastructureRequestManagement'       :  this.infrastructureRequestManagement,        
               'sprintBaselineCritera'                 :  this.sprintBaselineCritera,                  
               'supportTtaskManagement'                :  this.supportTtaskManagement,                 
               'isProjectPlanningTaskTrackingapplicable': this.isProjectPlanningTaskTrackingapplicable
          }),
          "iterationDemo"      : this._fb.group({
              'demoFrequency'                   :this.demoFrequency,                     
              'demoMode'                        :this.demoMode,                          
              'demoParticipants'                :this.demoParticipants,                 
              'demoFeedbackRecording'           :this.demoFeedbackRecording,             
              'isIterationDemoapplicable'       :this.isIterationDemoapplicable
          }),
          "iterationRetrospective"      : this._fb.group({
              'retrospectiveDuration'                     :this.retrospectiveDuration,             
              'retrospectiveFrequency'                    :this.retrospectiveFrequency,            
              'retrospectiveActionItemTracking'           :this.retrospectiveActionItemTracking,   
              'retrospectiveOutcomeDocumentation'         :this.retrospectiveOutcomeDocumentation, 
              'retrospectiveOwner'                        :this.retrospectiveOwner,                
              'stakeholderInvolvement'                    :this.stakeholderInvolvement,            
              'retrospectiveFocusOnAll'                   :this.retrospectiveFocusOnAll,           
              'isIterationRetrospectiveapplicable'        :this.isIterationRetrospectiveapplicable,
          }),
  });
    
 }




  ngOnInit() { 
    this.commonAPIservice.getSqa('/v-ptd/loadPtd?projectId=1&auditVersion=1').subscribe(  (res) => {
      this.APMList = res.entity ; 
    });
  }

  onSubmit(){
    this.commonAPIservice.update('/v-ptd/merge?userId=1&projectId=1&version=1' , this.APMList).subscribe(
      (res) => {
        alert('updated Sucessfully');
    });
  }

  chk(event){
    console.log(event)
   
  }



}

