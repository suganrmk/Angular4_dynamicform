import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';
@Component({
  selector: 'app-Home',
  templateUrl: './home.html'
})

export class HomeComponent   implements OnInit{
  myform: FormGroup;
  Project:String ;
  Cam1: string;
  Cam2: string;
  CamMembers: SelectItem[];
  SEPGApprover : string;
  GDCReview: string = "checkn";
  AccountName: string;
  rForm: FormGroup;
  EmployeeList;
  constructor(private formBuilder: FormBuilder , private newService: commonAPIservice) {
   
   this.CamMembers = [
                        {label:'Select Member', value:null},
                        {label:'Member Perf - 1', value:'Member Perf -  1'},
                        {label:'Member Perf - 2', value:'Member Perf -  2'}                        
                       ]
              

   }

   ngOnInit() {

  //   this.newService.getCarsSmall().then(Employee => this.EmployeeList = Employee);

 
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
    Automation: [this.EmployeeList , Validators.required],
    

    ReviewTaskTracking: ['', Validators.required],
    ReviewCriteria: ['', Validators.required],
    ReviewPlanTracking: ['', Validators.required],
    
  });

 // this.rForm.setValue(this.EmployeeList)

   }

   onSubmit(post){
   console.log(post);
   console.log(this.EmployeeList);

   }

 

}

