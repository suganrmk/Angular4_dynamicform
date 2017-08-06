import { Component, Input, Output,EventEmitter, ElementRef} from '@angular/core';



@Component ({
  selector: 'Ptd-Modal',
	template:`<div class="modal"  data-backdrop="static" tabindex="-1"  [ngStyle]="{'display': visible ? 'block' : 'none', 'opacity': visible ? 1 : 0}">
				<div class="modal-dialog" >
					<div class="modal-content">
						<div class="modal-header" >
                            <h4 class="modal-title" > {{this.Modaltitle}}</h4>
							<button type="button" (click)="close()" id="closeIcon" class="close" data-dismiss="modal"><i class="fa fa-close"></i></button>
						</div>
						<div class="modal-body">
							 <ng-content select="Modal-Body"></ng-content>
						</div>
					</div>
				</div>
</div>`   
})
export class ModalComponent {
	
constructor(public el: ElementRef) {}


   @Input() public visible = true;
	@Input() id : string;
	@Input() Modaltitle : string;
	
	 public open(): void {
	    this.visible = true;
	  }
	
	  public close(): void {
	    this.visible = false;
	    
	  }
}
