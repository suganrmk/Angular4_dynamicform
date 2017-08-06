import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, FormControl, Validator } from '@angular/forms';

@Component({
    selector: 'fieldInput',
    template:
        `<div class="col-md-4">
        <div class="form-group required">
            <label>{{fieldlabel}}</label>
            <input type="text"  class="form-control" required  pInputText maxLength="32" [value]="userValue" (focusout)="focusOut($event , $scope)"  (change)="onChange($event)" (keyup)="onChange($event)"/>
            </div> 
            <span class="errorMessage">{{errorText}} </span>
        </div>      
        `,
    providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => fieldInput),
      multi: true,
    },
    // {
    //   provide: NG_VALIDATORS,
    //   useExisting: forwardRef(() => fieldInput),
    //   multi: true,
    // }
]        
})
export class fieldInput implements ControlValueAccessor {
    private userValue: string;
    private parseError: boolean;
    private data: any;
    @Input() fieldlabel: any;
    errorText:string;
    

    // this is the initial value set to the component
    public writeValue(obj: any) {
        if (obj) {
           // this.data = obj;
            // this will format it with 4 character spacing
            this.userValue = obj;
        }
    }

    // registers 'fn' that will be fired wheb changes are made
    // this is how we emit the changes back to the form
    public registerOnChange(fn: any) {
        this.propagateChange = fn;
    }

    // validates the form, returns null when valid else the validation object
    // in this case we're checking if the json parsing has passed or failed from the onChange method
    // public validate(c: FormControl) {
    //     return (!this.parseError) ? null : {
    //         jsonParseError: {
    //             valid: false,
    //         },
    //     };
    // }

    // not used, used for touch input
    public registerOnTouched() { }

    // change events from the textarea
    private onChange(event) {
      
        // get value from text area
        let newValue = event.target.value;

        try {
            // parse it to json
            this.data = newValue;
            this.parseError = false;
        } catch (ex) {
            // set parse error if it fails
            this.parseError = true;
        }

        // update the form
        this.propagateChange(this.data);
    }

    // the method set in registerOnChange to emit changes back to the form
    private propagateChange = (_: any) => { };

    focusOut(event , scope){ 
        if(event.target.attributes.required){
            this.errorText =""
            if(this.data == "") 
               this.errorText ="required"
        }
        if(event.target.attributes.maxLength){
            if (this.data.length > parseInt(event.target.attributes.maxLength.value))
                 this.errorText ='You must enter at below ' + event.target.attributes.maxLength.value + 'characters';
        }
   


}
}