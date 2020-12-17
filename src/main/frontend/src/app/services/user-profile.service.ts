import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class UserProfileService {
    public serviceMessage = "Service Injected Successfully";

    constructor(private http: HttpClient) {}

    getUserProfile(name: String) {
        this.http.get('/api/profile/' + name).subscribe(profile => {
            console.log(profile);
        });
    }
}