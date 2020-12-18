import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import { Mentee } from '../types/mentee';

@Component({
  selector: 'app-mentee-form',
  templateUrl: './mentee-form.component.html',
  styleUrls: ['./mentee-form.component.css']
})
export class MenteeFormComponent {
  model = new Mentee('Axel Walker', 'MN', 'DEV');

  submitted = false;

  onSubmit() {
    this.submitted = true;
  }

  getDiagnostic() {
    return JSON.stringify(this.model);
  }

}
