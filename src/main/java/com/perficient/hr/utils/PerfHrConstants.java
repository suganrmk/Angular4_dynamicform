package com.perficient.hr.utils;

public class PerfHrConstants {

	public static final boolean ACTIVE = false;
	public static final boolean INACTIVE = true;

	public static final int READ = 1;
	public static final int UNREAD = 0;

	public static final String FIRST_HALF = "FIRST";
	public static final String SECOND_HALF = "SECOND";

	public static final int WORK_HOURS = 8;
	public static final float LEAVE_PER_MONTH = (float) 1.67;

	public static final String USER_ID = "userId";

	public static final String LOGIN_MODEL = "login";

	public static final String ACTIVE_COLUMN = "active";
	public static final String GENERIC_ID_COLUMN = "genericId";
	public static final String PASS_KEY = "210b5a29d0a385181bfca13de7ef67db";
	public static final String TEXT_ONLY = "^[a-zA-Z ]*$";
	public static final String NUMBER_ONLY = "^[0-9]+$";
	public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String DATE = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";;
	public static final String ALPHA_NUMERIC = "^[a-zA-Z0-9 ]*$";
	public static final String PENDING = "PENDING";
	public static final String REQUEST = "REQUEST";
	public static final String SUCCESS = "SUCCESS";
	public static final String CANCEL = "CANCEL";
	public static final String FAILURE = "FAILURE";
	public static final String PROCESSING = "PROCESSING";
	public static final String OPEN = "OPEN";
	public static final String LOGOUT = "/jsp/logout.jsp";
	
	public static final String EMAIL_FROM = "hradmin@perficient.com";

	public static final double[] LEAVE_BY_MONTH = {0, 0, 1.5, 3.5, 5, 6.5, 8.5, 10, 11.5, 13.5, 15, 16.5, 18.5}; 
	
	public static final int SECURITY_KEY_LENGTH = 32;
	public static final String SUBCONTRACTOR = "Subcontractor-Fixed";
	public static final String INTERN_CONSULTING = "INTERN CONSULTING";
	public static final String INTERN_ADMIN = "INTERN ADMIN";
	
	public static final int DUMMY_PASSWORD_LENGTH = 3;
	
	//Audit Constants
	public static final String PTD_DRAFT = "DRAFT";
	public static final String PTD_FINAL = "FINAL";
	public static final String PROJECT_ID = "projectId";
	public static final String AUDIT_VERSION = "version";
	public static final String STATUS_COLUMN = "status";
	public static final String CCR_ID = "7";
	public static final String DM_ID = "9";
	public static final String TCDR_ID = "4";
	public static final String DAR_ID = "13";
	public static final String RIDM_ID = "2";
	public static final String SC_ID = "5";
	public static final String PQPP_ID = "12";
	public static final String CM_ID = "10";
	public static final String APM_ID = "1";
	public static final String SR_ID = "3";
	public static final String AD_ID = "6";
	public static final String AST_ID = "8";
	public static final String HO_ID = "11";
	public static final String CAR_ID = "14";
	
	//	public int[] LEAVE_EARNED = {0, 12, 28, 40, 52, 68, 80, 92, 108, 120, 132, 148};
	
	private PerfHrConstants() {

	}
}