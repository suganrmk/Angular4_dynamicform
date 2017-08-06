import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, FormControl, Validator } from '@angular/forms';

@Component({
    selector: 'Loader',
    template:
        `<div   class="loading">
          <img src="assets/img/ptd-loader.svg"/>
         <span class="lodtext">Loading...</span>
        </div>      
        `,       
})
export class Loader {





}
