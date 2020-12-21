import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserProfileService } from './services/user-profile.service';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { MenteeComponent } from './mentee/mentee.component';
import { MentorComponent } from './mentor/mentor.component';
import { ClarityModule } from '@clr/angular';
import { FormsModule } from '@angular/forms';
import { MenteeFormComponent } from './mentee-form/mentee-form.component';
import { ChildComponent } from './test/child/child.component';
import { ParentComponent } from './test/parent/parent.component';

@NgModule({
  declarations: [
    AppComponent,
    MenteeComponent,
    MentorComponent,
    MenteeFormComponent,
    ChildComponent,
    ParentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    HttpClient,
    UserProfileService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
