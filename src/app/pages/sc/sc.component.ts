import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice } from '../../providers/commonServices';
import { projectSelectComponent } from '../../pages/projectSelect/projectSelect.component';
@Component({
  selector: 'app-sc',
  templateUrl: './sc.html'
})

export class SCComponent   implements OnInit{
  myform: FormGroup;
  Project:String ;
  Cam1: string;
  Cam2: string;
  CamMembers: SelectItem[];
  SEPGApprover : string;
  GDCReview: string = "checkn";
  AccountName: string;
  rForm: FormGroup;
  PRList;
  constructor(private formBuilder: FormBuilder , private commonAPIservice: commonAPIservice) {
   
   this.CamMembers = [
                        {label:'Select Member', value:null},
                        {label:'Member Perf - 1', value:'Member Perf -  1'},
                        {label:'Member Perf - 2', value:'Member Perf -  2'}                        
                       ]
              

   }

   ngOnInit() {

      //   this.commonAPIservice.getSqa('/loadEmployee').subscribe(  (res) => this.PRList = res);

 
    this.myform = new FormGroup({
     
     Project: new FormControl('', [ Validators.required]),
     Cam1: new FormControl('', [ Validators.required]),
     Cam2: new FormControl('', [ Validators.required]),
     SEPGApprover: new FormControl('', [ Validators.required]),
     AccountName: new FormControl('', [ Validators.required]),
          
    });


    this.rForm = this.formBuilder.group({
    Frequency: ['', Validators.required],
    TeamReviewpeerReview: ['', Validators.required],
    GDCReview: [this.GDCReview, Validators.required],
    EnableReview: ['', Validators.required],
    ReviewComentTracking: ['', Validators.required],
    Automation: [this.PRList , Validators.required],
    

    ReviewTaskTracking: ['', Validators.required],
    ReviewCriteria: ['', Validators.required],
    ReviewPlanTracking: ['', Validators.required],
    
  });

 // this.rForm.setValue(this.PRList)

   }

   onSubmit(post){
   console.log(post);
   console.log(this.PRList);

   }

 

}

