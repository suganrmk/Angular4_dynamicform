package com.perficient.hr.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perficient.hr.dao.EmployeeDAO;
import com.perficient.hr.dao.HolidaysDAO;
import com.perficient.hr.model.Holidays;
import com.perficient.hr.utils.PerfHrConstants;

@Repository("holidaysDAO")
public class HolidaysDAOImpl implements HolidaysDAO {

protected Logger logger = LoggerFactory.getLogger(HolidaysDAOImpl.class);
	
	@Autowired
    EmployeeDAO employeeDAO;

	@Override
	@SuppressWarnings("unchecked")
	public List<Holidays> loadHolidays(Session session) {
		String sqlQuery = " from Holidays d where d.active=:active";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		return query.list();
	}

	@Override
	public Holidays addHoliday(Holidays holiday, Session session){
		session.save(holiday);
		return holiday;
	}

	@Override
	public Holidays loadHolidayById(String holidayId, Session session) {
		return (Holidays)session.get(Holidays.class, Long.parseLong(holidayId));
	}

	@Override
	public boolean updateHoliday(Holidays holiday, Session session) {
		session.merge(holiday);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Holidays> getHolidaysByYear(String year, Session session) {
		String sqlQuery = " from Holidays d where d.active=:active and d.year=:year";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("active", PerfHrConstants.ACTIVE);
		query.setParameter("year", Integer.parseInt(year));
		return query.list();
	}
	
}
