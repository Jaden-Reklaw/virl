import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-child',
  templateUrl: './child.component.html'
})
export class ChildComponent {

  child_message:string = '';

  @Input() 
  parent_message: string = '';

  @Input() 
  parent_message2: string = '';

  @Output()
  notify: EventEmitter<string> = new EventEmitter<string>();

  notifyParent() {
    this.notify.emit(this.child_message);
  }
  
}
