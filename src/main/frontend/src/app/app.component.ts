import { Component, OnInit } from '@angular/core';
import { UserProfileService } from './services/user-profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';
  profileMessge:string; //message is from user-profile.service.ts

  constructor(private userProfileService: UserProfileService) {
    
  }

  ngOnInit() {
    this.profileMessge = 'Component Var ' + this.userProfileService.serviceMessage;
    this.userProfileService.getUserProfile('jordan.walker');
  }
}
