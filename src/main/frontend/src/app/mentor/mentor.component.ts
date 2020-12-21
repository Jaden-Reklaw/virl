import { Component, OnInit } from '@angular/core';
import { Mentee } from '../types/mentee';
import { HttpClient } from '@angular/common/http';

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

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getMentees();
  }

  getMentees() {
    this.http.get<Mentee[]>('/api/mentee/').subscribe((menteeList:Mentee[]) => {
      this.mentees = menteeList;
    });
  }

  saveMentee(mentee: Mentee) {
    this.newMenteeModal = false;
    console.log('Saving Mentee!');
    console.log(mentee);
  }

  menteeModalState(visible:boolean) {
    console.log('From the child', visible);
    this.newMenteeModal = visible;
  }
}
