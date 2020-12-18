import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Mentee } from '../types/mentee';

@Component({
  selector: 'app-mentee',
  templateUrl: './mentee.component.html',
  styleUrls: ['./mentee.component.css']
})
export class MenteeComponent implements OnInit {
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

  saveMentee() {
    console.log('Saving Mentee!');
    
  }

}
