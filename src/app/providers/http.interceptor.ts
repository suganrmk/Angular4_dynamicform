import {Injectable , OnInit} from "@angular/core";
import { ConnectionBackend, RequestOptions, Request, RequestOptionsArgs, Response, Http, Headers} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {environment} from "../../environments/environment";

@Injectable()
export class InterceptedHttp extends Http{
    constructor(backend: ConnectionBackend, defaultOptions: RequestOptions) {
        super(backend, defaultOptions);
        defaultOptions.withCredentials = true        
    }


    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
    
        return super.get(url, this.getRequestOptionArgs(options));
            
    }

    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.post(url, body, this.getRequestOptionArgs(options));
    }

    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.put(url, body, this.getRequestOptionArgs(options));
    }

    delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
        url = this.updateUrl(url);
        return super.delete(url, this.getRequestOptionArgs(options));
    }

     request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
      return this.intercept(super.request(url, options));
     }

     intercept(observable: Observable<Response>): Observable<Response> {
   
    return observable
     
      .do((res: Response) => {
        document.body.classList.add('ShowModal');
      }, (err: any) => {
        document.body.classList.remove('ShowModal');
       
        console.log("Caught error: " + err);
      })
      .finally(() => {
        var timer = Observable.timer(1000);
        timer.subscribe(t => {
        document.body.classList.remove('ShowModal');
            
        });
      });
    }

    
    private updateUrl(req: string) {
        return  environment.origin + req;
    }

    

    private getRequestOptionArgs(options?: RequestOptionsArgs) : RequestOptionsArgs {
        if (options == null) {
            options = new RequestOptions();
        }
        if (options.headers == null) {
            options.headers = new Headers();
        }
        options.headers.append('Content-Type', 'application/json');
        options.withCredentials = true
        return options;
        
    }
}