import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    var passEncode = 'passprft123';
		var doLogin = function(){
			var username= $('#username').val();
			var password= $('#password').val();
			if($.trim(username).length == 0 || $.trim(password).length == 0){
				alert('Username/password cannot be empty.');
				return false;
			}
			var loginUrl = 'http://localhost:8080/perfwfm/mobileLogin';

		    $('#overlay').show();
			$.ajax({
			  type: "post",
			  url: loginUrl,
			  contentType: "application/json",
			  dataType: "json",
			  headers: {"username":$('#username').val(),"passphrase":'.f4pp6o2r1k', "iv":'35eaee53d44053c73a0ca72e1a9af177', "salt":'e3028805eba0a645c43c2b82bddd5d37', "ciphertext":'xRHZaQNGYkvEea3x2o5D2A=='},
			  success: function(resp){
				if(resp.mobileResponse){
					//document.cookie = 'JSESSIONID = '+resp.mobileResponse+'; path: /';
          document.cookie = 'JSESSIONID='+resp.mobileResponse+'; path=/';
					window.localStorage.setItem("JSESSIONID", resp.mobileResponse);
					console.log('redirecting to home page happens...'); // re route back to home page
        }
			  },
			  error: function(xhr, ajaxOptions, thrownError){
				$('#overlay').hide();
				alert('Incorrect Username/Password.');
			  }
		   });

		};
		$('#username').add('#password').keypress(function (e) {
		  var key = e.which;
		  if(key == 13) {
		    doLogin();
		    return false;
		  }
		});
		$('#login').click(function(){
			doLogin();
		});
  }


}
