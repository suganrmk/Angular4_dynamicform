import { Component , ModuleWithProviders , Input , OnInit , ElementRef ,EventEmitter, AfterViewInit , Renderer, Output} from '@angular/core';
import {Http, Response} from '@angular/http';
import {SelectItem } from 'primeng/primeng';
import { Routes, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent  implements OnInit , AfterViewInit {
  title = 'app works!';
  users: Array<any> = [];
  Menuchanges: boolean = true;
  menu1: Array<any>;
  menu2: Array<any>;
    auditVersion: SelectItem[] = [{label:'Select audit version', value:null} , {label:'Version 1', value: 1} ];
      Projects: SelectItem[] = [{label:'Select Project', value:null} ];

auditControl: any;
name: string  ="ok";
myform:any;
        dropdownSettings = {};
  @Output() projectSelection = new EventEmitter();
  test:any;

  constructor( private formBuilder: FormBuilder , private http: Http, private elementRef:ElementRef , renderer: Renderer) {


    this.myform = new FormGroup({
     
     auditControl: new FormControl('', [ Validators.required]),
     Project: new FormControl('', [ Validators.required])
     
     
          
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


   this.http.get('/v-projects/loadProjects').subscribe((project: any) => {

     for (let i = 0; i < project.json().entity.length; i++) {
         this.Projects.push({label: project.json().entity[i].projectName,
                              value: project.json().entity[i].pk
                            });
          }      
     })


  this.menu1 = [
        {
            name:'',
            tooltip:'Home',
            path:'#',
            icon: 'fa fa-home'
        },
          {
            name:'',
            tooltip:'Process Review',
            path:'/PR',
            icon: 'fa fa-pencil-square-o',
            action: 'toggleMenu()'
        },
          {
            name:'',
            tooltip:'Review and submit',
            path:'/RS',
            icon: 'fa fa-plane'
        }
      ]

   this.menu2 = [
        {
            name:'',
            tooltip:'Home',
            path:'',
            icon: 'fa fa-home'
        },
          {
            name:'APM',
            tooltip:'APM',
            path:'/APM',
        },
          {
            name:'RIDM',
            tooltip:'RIDM',
            path:'/RIDM',
        },{
            name:'SR',
            tooltip:'SR',
            path:'/SR',
        },
          {
            name:'TCDR',
            tooltip:'TCDR',
            path:'/TCDR',
        },
          {
            name:'SC',
            tooltip:'SC',
            path:'/SC',
        },
        {
            name:'AD',
            tooltip:'AD',
            path:'/AD',
        },
          {
            name:'PR',
            tooltip:'PR',
            path:'/PR',
        },
          {
            name:'AST',
            tooltip:'AST',
            path:'/AST',
        },
        {
            name:'DM',
            tooltip:'DM',
            path:'/DM',
        },
          {
            name:'CM',
            tooltip:'CM',
            path:'/CM',
        },
          {
            name:'PQPP',
            tooltip:'PQPP',
            path:'/PQPP',
        },
          {
            name:'HO',
            tooltip:'HO',
            path:'/HO',
        },
          {
            name:'DAR',
            tooltip:'DAR',
            path:'/DAR',
        },
          {
            name:'CAR',
            tooltip:'CAR',
            path:'/CAR',
        }
      ]
     
  }

  

  ngOnInit() {

    this.http.get('/v-employee/loadEmployee').subscribe((users: any) => {
                this.users = users.json().entity;
            }
        )
  }

  ngAfterViewInit() {



}


onSubmit(value){

this.test = value;
console.log(this.test)
this.projectSelection.emit(value)
}

handleUserUpdated(){
  this.Menuchanges = !this.Menuchanges;
}
    
}

