import { Component, OnInit } from '@angular/core';
import { UserProfileService } from './services/user-profile.service';
import { UserProfile } from './types/user-profile';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  //Development Mode
  devProfile:boolean = true;

  menteeView:boolean = false;
  mentorView:boolean = false;

  constructor(private userProfileService: UserProfileService) {
    
  }

  ngOnInit() {
    if(this.devProfile) {
      //Manually set the view
      this.menteeView = true;
    } else {
      
      this.userProfileService.getSessionProfile().subscribe((profile: UserProfile) => {
        if(profile.role === 'MENTOR') {
          this.mentorView = true;
        } else if(profile.role === 'MENTEE') {
          this.menteeView = true;
        }
      });
    }
  }//end ngInit()
}
