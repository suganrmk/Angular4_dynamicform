import { Component, Input, Output,EventEmitter, ElementRef} from '@angular/core';



@Component ({
  selector: 'Ptd-Menu',
	template:` <ul class="sidebar-menu">
            <br>
            <li *ngFor="let menu of menulist"  class="treeview">
               <a  (click)="toggleMenu($event)"  event="true"  [routerLinkActive]="['active']" [routerLink]="[menu.path]">
               <span class="ptd-tooltip">{{menu.tooltip}}</span>
                  <label><i class="{{menu.icon}}"></i>{{menu.name}}</label>
               </a>
            </li>
            </ul>`   
})
export class MenuComponent {
	
constructor(public el: ElementRef) {}

  @Output() Menuchange = new EventEmitter();


	@Input() menulist : any;
	@Input() parentMenu:boolean;
	
  toggleMenu(menu){
     console.log(menu.target.firstElementChild.innerText === "Process Review" , menu.target.firstElementChild.innerText );
      if(menu.target.firstElementChild.innerText === "Process Review" || menu.target.firstElementChild.innerText === "Home"){
       //  this.parentMenu = !this.parentMenu;
         this.Menuchange.emit(this.parentMenu)
      }
         
  }

}
