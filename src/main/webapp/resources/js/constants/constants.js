var context = '';
if(bowser.mobile || bowser.tablet){
	context = 'http://'+location.hostname+':8080/perfwfm/';
}
var urlPrefix = context+'v-';
var tdcUrl = 'http://'+location.hostname+'/tdc/home.html';
var ctaUrl = 'http://'+location.hostname+'/taa/home.html';
var deskLoginUrl = '/perfwfm/mobileLogin'
var mobileLoginUrl = 'http://'+location.hostname+':8080/perfwfm/mobileLogin';
//var mobileHome = "index.html#/home";
var mobileHome = "/perfwfm/home";
var mobileLoginPage = "login.html";
var deskHome = "/perfwfm/home";
var deskLoginPage = "login";
var mobileVersion=2.0