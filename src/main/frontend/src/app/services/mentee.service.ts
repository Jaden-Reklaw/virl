import {Injectable} from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Mentee } from '../types/mentee';
import { catchError, retry } from 'rxjs/operators';

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'my-auth-token'
    })
}

@Injectable()
export class MenteeService {
    menteeUrl:string = '/api/mentee/';

    constructor(private http: HttpClient) {}

    addMentee(mentee: Mentee):Observable<Mentee> {
        return this.http.post<Mentee>(this.menteeUrl, mentee, httpOptions)
        .pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if(error.error instanceof ErrorEvent) {
            //A client-side or network error occurred, Handle it accordingly
            console.log('An error occurred:', error.error.message);
        } else {
            //The backend returned an unsuccessful response code.
            //The response body may contain clues as to what went wrong.
            console.log(`Backend returned code ${error.status} body was: ${error.error}`);
        }
        return throwError('Something Bad Happened; please try again later.');
    }
}