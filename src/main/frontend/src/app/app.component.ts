import { Component, OnInit } from '@angular/core';
import { UserProfileService } from './services/user-profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'frontend';
  profileMessage:string = "";
  userProfile:any;

  constructor(private userProfileService: UserProfileService) {
    
  }

  ngOnInit() {
    this.profileMessage = 'Component Var ' + this.userProfileService.serviceMessage;
    //this.userProfileService.getUserProfile('jordan.walker');
    this.userProfileService.getSessionProfile().subscribe(profile => {
      this.userProfile = profile;
      console.log(this.userProfile);
    });
  }
}
