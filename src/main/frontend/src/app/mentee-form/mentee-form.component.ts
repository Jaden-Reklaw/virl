import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import { Mentee } from '../types/mentee';

@Component({
  selector: 'app-mentee-form',
  templateUrl: './mentee-form.component.html',
  styleUrls: ['./mentee-form.component.css']
})
export class MenteeFormComponent {
  @Input() visible:boolean = false;
  @Output() submitMentee: EventEmitter<Mentee> = new EventEmitter<Mentee>();
  @Output() modalState: EventEmitter<boolean> = new EventEmitter<boolean>();
  model:Mentee = new Mentee('', '', '');
  submitted:boolean = false;

  onSubmit() {
    this.submitted = true;
    this.submitMentee.emit(this.model);
    this.visible = false;
  }

  closeModal() {
    this.visible = false;
    this.modalState.emit(false);
  }

  getDiagnostic() {
    return JSON.stringify(this.model);
  }

}
