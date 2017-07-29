import { Component, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

import {  
  NG_VALUE_ACCESSOR,
} from '@angular/forms';




@Component({
  selector: 'FormInput-text',
  template: ` <div class="col-md-12">
              <div class="form-group required">
              <label for="formGroupExampleInput">{{inputLabel}}</label>
              <input type="text" class="form-control" formcontrolname="frequency"  [(ngModel)]="model"  pInputText   />
              <control-messages control="errorControl"></control-messages>
              </div>
              </div>`,
  providers: [
    {provide: NG_VALUE_ACCESSOR, useExisting: FormInputComponent, multi: true}
  ],
})
export class FormInputComponent {
  @Input() model: any;
  @Input() inputLabel: string;
  @Input() errorControl: any;
  @Input() formControlName2:any;
}

// <control-messages [control]="rForm.controls.continuosCodeReview.controls.frequency"></control-messages>

// PRList.continuosCodeReview.frequency