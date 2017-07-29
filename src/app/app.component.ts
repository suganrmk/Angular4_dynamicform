import { Component , ModuleWithProviders , Input , OnInit} from '@angular/core';
import {Http, Response} from '@angular/http';

import { Routes, RouterModule } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent  implements OnInit{
  title = 'app works!';
  @Input() pageTitle: string = "Peer Review";
  users: Array<any> = [];

  notify:boolean = true;


  constructor(  private http: Http) {

  }
     ngOnInit() {

       this.http.get('/v-employee/loadEmployee').subscribe((users: any) => {
                    this.users = users.json().entity;
                }
            )
     }
}

