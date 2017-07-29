(function() {
	$(document).ready(function(){
		$('.carousel').carousel();
		
		var errorMsg = function(msg){
			$.alert({
			    title: 'Error',
			    theme: 'black',
			    type: 'red',
			    columnClass: 'col-md-6 col-md-offset-3',
			    content: msg,
			    buttons: {
			    	Okay: {
	    				btnClass: 'btn-danger'
	    	        }
			    }
			});
		}
		
		var successMsg = function(msg){
	    	$.alert({
			    title: 'Message:',
			    type: 'green',
			    columnClass: 'col-md-6 col-md-offset-3',
			    content: msg,
			    buttons: {
			    	Okay: {
	    				btnClass: 'btn-success'
	    	        }
			    }
			});
	    }
		
//		if($('#rememberMe').prop('checked')){
			if(window.localStorage.getItem("uname") != null
					&& window.localStorage.getItem("upwd") != null){
				$('#username').val(window.localStorage.getItem("uname"));
				$('#password').val(window.localStorage.getItem("upwd"));
			}
//		}
		
		var passEncode = 'passprft123';
		var doLogin = function(){
			var username= $('#username').val();
			var password= $('#password').val();
			if($.trim(username).length == 0 || $.trim(password).length == 0){
				errorMsg('Username/password cannot be empty.');
				return false;
			}
			var loginUrl = deskLoginUrl;
			if(bowser.mobile || bowser.tablet){
				loginUrl = mobileLoginUrl;
			}
			
		    var passphrase = Math.random().toString(36).substring(1);
		    var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
		    var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
		    var aesUtil = new AesUtil(128, 1000);
//		    if(bowser.mobile || bowser.tablet){
		    	var decrypted = CryptoJS.AES.decrypt(password, passEncode).toString(CryptoJS.enc.Utf8);
		    	if(decrypted.indexOf(passEncode) == 0){
			    	password=decrypted.replace(passEncode, '');
			    }
//		    }
		    var ciphertext = aesUtil.encrypt(salt, iv, passphrase, password);
		    $('#overlay').show();
			$.ajax({
			  type: "post",				  
			  url: loginUrl,
			  contentType: "application/json",
			  dataType: "json",
			  headers: {"username":$('#username').val(),"passphrase":passphrase, "iv":iv, "salt":salt, "ciphertext":ciphertext},
			  success: function(resp){
				if(resp.mobileResponse){												
					$.cookie('JSESSIONID', resp.mobileResponse, { path: '/' });
					window.localStorage.setItem("JSESSIONID", resp.mobileResponse);
					if($('#rememberMe').prop('checked')){
						window.localStorage.setItem("uname", username);
				        window.localStorage.setItem("upwd", CryptoJS.AES.encrypt(passEncode+password, passEncode));
					}
					navHomePage();
				}
			  }, 
			  error: function(xhr, ajaxOptions, thrownError){
				$('#overlay').hide();
				errorMsg('Incorrect Username/Password.');
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
		
		var navHomePage = function(){
			if(bowser.mobile || bowser.tablet){
				window.location.href = mobileHome;
			} else {
				window.location.href = deskHome;
			}
		};
		
		$('#forgotPwd').click(function(){
			var regEx = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i; 
			if($.trim($('#forgotPwdEmail').val()).length == 0){
				errorMsg('Email is required.');
				return false;
			} else if(!regEx.test($('#forgotPwdEmail').val())){
				errorMsg('Please enter valid Email.');
				return false;
			} else {
				$.ajax({
					 type: "post",				  
					 url: 'forgotPwdTicket?email='+$('#forgotPwdEmail').val(),
					 contentType: "application/json",
					 dataType: "json",
					 success: function(response){
						 successMsg('Your forgot password ticket '+response.entity.ticketNo+' is submitted successfully!');
						 $('#forgotPwdEmail').val('');
						 $('#forgetpass').modal('hide');
					 }, 
					 error: function(xhr, ajaxOptions, thrownError){
						$('#overlay').hide();
						errorMsg('Sorry! An Error occurred.  Try after sometime!');
					 }
				});
			}
		});
		
		var AesUtil = function(keySize, iterationCount) {
		  this.keySize = keySize / 32;
		  this.iterationCount = iterationCount;
		};

		AesUtil.prototype.generateKey = function(salt, passPhrase) {
		  var key = CryptoJS.PBKDF2(
		      passPhrase, 
		      CryptoJS.enc.Hex.parse(salt),
		      { keySize: this.keySize, iterations: this.iterationCount });
		  return key;
		}

		AesUtil.prototype.encrypt = function(salt, iv, passPhrase, plainText) {
		  var key = this.generateKey(salt, passPhrase);
		  var encrypted = CryptoJS.AES.encrypt(
		      plainText,
		      key,
		      { iv: CryptoJS.enc.Hex.parse(iv) });
		  return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
		}

		AesUtil.prototype.decrypt = function(salt, iv, passPhrase, cipherText) {
		  var key = this.generateKey(salt, passPhrase);
		  var cipherParams = CryptoJS.lib.CipherParams.create({
		    ciphertext: CryptoJS.enc.Base64.parse(cipherText)
		  });
		  var decrypted = CryptoJS.AES.decrypt(
		      cipherParams,
		      key,
		      { iv: CryptoJS.enc.Hex.parse(iv) });
		  return decrypted.toString(CryptoJS.enc.Utf8);
		}
	});
})();