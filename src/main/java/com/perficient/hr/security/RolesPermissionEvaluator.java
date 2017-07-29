package com.perficient.hr.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class RolesPermissionEvaluator implements PermissionEvaluator {


	@Override
	 public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		 boolean hasPermission = false;
		 if ( authentication != null &&  permission instanceof String){
		  	   hasPermission =  true;
		 } else {
			  hasPermission = false; 
		 }
		 return hasPermission;
	 }

	 @Override
	 public boolean hasPermission(Authentication authentication,Serializable targetId, String targetType, Object permission) {
		 boolean hasPermission = false;
		 if ( authentication != null &&  permission instanceof String){
		  	   hasPermission =  false;
		 } else {
			  hasPermission = false; 
		 }
		 return hasPermission;
	 }
	 
}