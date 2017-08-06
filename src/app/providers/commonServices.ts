import { Component, OnInit  } from '@angular/core';
import {Injectable , Input} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {Subject } from 'rxjs/Subject';
import {Observable} from 'rxjs/Rx';
export interface Employee {
    Frequency?;
}


@Injectable()
export class commonAPIservice {
@Input() public modalAttrChange = 1;
public AuditVersionId = 1;
url = "/v-ptd/loadPtd?projectId=10&auditVersion=1";

    constructor(private http: Http) {}
    getSqa(url) {
        return this.http.get(this.url)
                    .map(data => data.json() )
                    .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
        }

    get() {
        return this.http.get('/v-ptd/loadPtd?projectId='+this.modalAttrChange+'&auditVersion='+ this.modalAttrChange)
                    .map(data => data.json() )
                    .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
        }

    add(url ,data){
            return this.http.post(url, data) 
                            .map((res:Response) => res.json()) 
                            .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    }

    update(url , data ){
            return this.http.put(url ,data) 
                            .map((res:Response) => res.json()) 
                            .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    }

    // Delete a comment
    remove (url , id): Observable<Comment[]> {
        return this.http.delete(`${url}/${id}`) 
                            .map((res:Response) => res.json()) 
                            .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    }   



}

