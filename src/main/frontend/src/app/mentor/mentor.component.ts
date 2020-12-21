import { Component, OnInit } from '@angular/core';
import { Mentee } from '../types/mentee';
import { HttpClient } from '@angular/common/http';
import { MenteeService } from '../services/mentee.service';

@Component({
  selector: 'app-mentor',
  templateUrl: './mentor.component.html',
  styleUrls: ['./mentor.component.css']
})
export class MentorComponent implements OnInit {
  mentees: Mentee[] = [];
  //Modal variable
  newMenteeModal:boolean = false;
  errorModal:boolean = false;

  constructor(private menteeService: MenteeService, private http: HttpClient) { }

  ngOnInit(): void {
    this.getMentees();
  }

  getMentees() {
    this.menteeService.getMentees().subscribe(mentees => this.mentees = mentees);
  }

  saveMentee(newMentee: Mentee) {
    this.newMenteeModal = false;
    //console.log("From Parent Component", newMentee);
    this.menteeService.addMentee(newMentee).subscribe(mentee => this.mentees.push(mentee));
  }

  menteeModalState(visible:boolean) {
    //console.log('From the child', visible);
    this.newMenteeModal = visible;
  }
}
