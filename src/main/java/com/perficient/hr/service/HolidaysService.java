package com.perficient.hr.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.perficient.hr.model.Holidays;

public interface HolidaysService {

	public Object loadHolidays();
	
	public Object loadHolidaysById(String holidaysId);
	
	public Object getHolidaysByYear(String year);
	
	@PreAuthorize("@emprolesService.hasRoles('hdSvBtn')")
	public Object addHolidays(Holidays Holidays, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('hdUpBtn')")
	public Object updateHolidays(Holidays Holidays, String userId);
	
	@PreAuthorize("@emprolesService.hasRoles('hdDlBtn')")
	public Object deleteHolidays(Holidays Holidays, String userId);
	
}
