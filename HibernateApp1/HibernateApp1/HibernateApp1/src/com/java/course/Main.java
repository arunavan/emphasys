package com.java.course;

import java.util.List;

import java.util.Iterator;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



public class Main {

	public static void main(String[] args) {
		Main obj = new Main();
		//SessionFactory  sf = new Configuration().configure().buildSessionFactory();
		//System.out.println("============");
		
		
		//Long courseId1 = obj.saveCourse("java");
		Long courseId2 = obj.saveCourse("jquery");
		Long courseId3 = obj.saveCourse("ajax");
		obj.listCourse();
		obj.updateCourse(courseId2, "Mathematics");
		obj.deleteCourse(courseId3);
obj.listCourse();
	}
	
	public Long saveCourse(String courseName)
	{
	
		
		
		
		SessionFactory  sf = new Configuration().configure().buildSessionFactory();
		
		
		Session session=sf.openSession();
		
		Transaction transaction = null;
		
		Long courseId = null;
		
		try {
			transaction = session.beginTransaction();
			
			
			Course course = new Course();
			
			course.setCourseName(courseName);  //trancient
			
			courseId = (Long) session.save(course);//insert    course is persistent
			
			
			transaction.commit();
			
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			
			session.close();
			sf.close();
		}
		return courseId;
	}
	
	public void listCourse()
	{
		SessionFactory  sf = new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		//Transaction transaction = null;
		try {
		//	transaction = session.beginTransaction();
			
			List courses = session.createQuery("from Course").list();
			
			
			for (Iterator iterator = courses.iterator(); iterator.hasNext();)
			{
				Course course = (Course) iterator.next();
				System.out.println(course.getCourseId()+"  "+course.getCourseName());
			}
			//transaction.commit();
		} catch (HibernateException e) {
			//transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void updateCourse(Long courseId, String courseName)
	{
		SessionFactory  sf = new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			
			Course course = (Course) session.get(Course.class, courseId);
			
			course.setCourseName(courseName);
			session.flush();
			
		transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void deleteCourse(Long courseId)
	{
		SessionFactory  sf = new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		session.setFlushMode(FlushMode.AUTO);
			Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Course course = (Course) session.get(Course.class, courseId);
			
			session.delete(course);
			
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}}

