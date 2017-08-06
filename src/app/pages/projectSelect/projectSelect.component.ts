import { Component  , Injectable , OnInit , Input} from '@angular/core';
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
  CamMembers: any[] = [{'itemName':'Select Member', 'id':null} ];
  Projects: SelectItem[] = [{label:'Select Project', value:null} ];
  auditVersion: SelectItem[] = [{label:'Select audit version', value:null} , {label:'Version 1', value: '1'} ];
     display: boolean = true;
  SEPGApprover : string;
auditControl: any;
    dropdownSettings = {};



  constructor(private formBuilder: FormBuilder , private newService: commonAPIservice , private http: Http) {              

   }

   ngOnInit() {


  this.http.get('/v-employee/loadEmployees').subscribe((users: any) => {

     for (let i = 0; i < users.json().entity.length; i++) {
      
         this.CamMembers.push({'itemName': users.json().entity[i].firstName,
                              'id': users.json().entity[i].firstName
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
     auditControl: new FormControl('', [ Validators.required]),
     
          
    });



        this.dropdownSettings = { 
                                  singleSelection: false, 
                                  text:"Select Cam Member",
                                  selectAllText:'Select All',
                                  unSelectAllText:'UnSelect All',
                                  enableSearchFilter: true,
                                  classes:"myclass custom-class",
                                  badgeShowLimit: 1
                                };     

   }

   updateModal(){
     this.display = true;
     
   }

   onItemSelect(item:any){
        console.log(item);
    }
    OnItemDeSelect(item:any){
        console.log(item);
    }
    onSelectAll(items: any){
        console.log(items);
    }
    onDeSelectAll(items: any){
        console.log(items);
    }
 

 

}

