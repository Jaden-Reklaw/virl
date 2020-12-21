import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-child',
  templateUrl: './child.component.html'
})
export class ChildComponent {

  @Input() 
  parent_message: string = '';

  @Input() 
  parent_message2: string = '';
  
}
