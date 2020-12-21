import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {FormBuilder, FormControl, NgForm, Validators} from "@angular/forms";
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

  onSubmit(menteeForm: NgForm) {
    //console.log("From the Child Form",menteeForm.value);
    
    this.submitted = true;
    this.submitMentee.emit(this.model);
    this.closeModal(menteeForm);
  }

  closeModal(menteeForm: NgForm) {
    this.visible = false;
    this.modalState.emit(false);
    menteeForm.reset();
  }

  getDiagnostic() {
    return JSON.stringify(this.model);
  }

}
