import { Component  , Injectable , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice } from '../../providers/commonServices';
import {Employee } from '../../providers/commonServices';
@Component({
  selector: 'projectSelect',
  templateUrl: './projectSelect.html'
})

export class projectSelectComponent   implements OnInit{
  myform: FormGroup;
  Project:String ;
  Cam1: string;
  Cam2: string;
  CamMembers: SelectItem[] = [{label:'Select Member', value:null} ];
  Projects: SelectItem[] = [{label:'Select Project', value:null} ];
  
  SEPGApprover : string;


  constructor(private formBuilder: FormBuilder , private newService: commonAPIservice , private http: Http) {              

   }

   ngOnInit() {


  this.http.get('/v-employee/loadEmployees').subscribe((users: any) => {

     for (let i = 0; i < users.json().entity.length; i++) {
      
         this.CamMembers.push({label: users.json().entity[i].firstName,
                              value: users.json().entity[i].firstName
                            });
          }      
     })
 

   this.http.get('/v-projects/loadProjects').subscribe((project: any) => {

     for (let i = 0; i < project.json().entity.length; i++) {
         this.Projects.push({label: project.json().entity[i].projectName,
                              value: project.json().entity[i].pk
                            });
          }      
     })

    this.myform = new FormGroup({
     
     Project: new FormControl('', [ Validators.required]),
     Cam1: new FormControl('', [ Validators.required]),
     Cam2: new FormControl('', [ Validators.required]),
     SEPGApprover: new FormControl('', [ Validators.required]),
     AccountName: new FormControl('', [ Validators.required]),
          
    });




   }

 

 

}

