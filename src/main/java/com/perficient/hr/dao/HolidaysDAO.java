package com.perficient.hr.dao;

import java.util.List;

import org.hibernate.Session;

import com.perficient.hr.model.Holidays;

public interface HolidaysDAO {

	public List<Holidays> loadHolidays(Session session);
	
	public List<Holidays> getHolidaysByYear(String year, Session session);
	
	public Holidays loadHolidayById(String holidayId, Session session);
	
	public Holidays addHoliday(Holidays holiday, Session session);
	
	public boolean updateHoliday(Holidays holiday, Session session);

}
