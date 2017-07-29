import { Component  , Injectable , OnInit , OnChanges , Input} from '@angular/core';
import {Http, Response} from '@angular/http';
import { ReactiveFormsModule, FormsModule, FormGroup, FormControl, Validators, FormBuilder} from '@angular/forms';
import {SelectItem } from 'primeng/primeng';
import {commonAPIservice  } from '../../providers/commonServices';
import{ValidationService , ControlMessagesComponent} from '../../providers/validationService'


@Component({
  selector: 'ProcessView-app',
  templateUrl: './processView.html'
})




export class ProcessViewComponent {

}

