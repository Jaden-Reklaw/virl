import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html'
})
export class ParentComponent {
  message: string = '';
  messageFromChild: string = '';
  parent_message:string ='';
  parent_message2:string ='';

  onNotify(message: string): void {
    this.messageFromChild = message;
  }

}
