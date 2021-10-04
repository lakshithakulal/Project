package com.jdbc;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class StudentDetails {
	Scanner scan=new Scanner(System.in);
	EntityManager manager=null;
	EntityManagerFactory factory=null;
	EntityTransaction transaction=null;
	Student student=null;
	public static void main(String[] args) {
		StudentDetails studentdetails=new StudentDetails();
		Student student=new Student();

		boolean exit=false;
		do {
			Scanner scan=new Scanner(System.in);
			System.out.println("Press 1 to see all data");
			System.out.println("Press 2 to see any particular data");
			System.out.println("Press 3 to update any Particular data");
			System.out.println("press 4 to delete data");
			System.out.println("Press 5 to exit");
			System.out.println("Enter Your Choice");
			int choice=scan.nextInt();
			switch (choice) {
			case 1:

				try {
					studentdetails.display();
				} catch (Exception e2) {
					
					e2.printStackTrace();
				}

				break;
			case 2:
				try {
					studentdetails.displaySingleValue();
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			case 3:
				try {
					studentdetails.UpdateValue();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				break;
			case 4:
				try {
					studentdetails.deleteValue();
				} 
				catch (Exception e) {

					e.printStackTrace();
				}break;
			case 5:
				exit=true;
				break;
			default:System.out.println("Invalid choice");

			} 
		}while (!exit) ;

	}




	public void display()
	{
		EntityManager manager=null;
		EntityManagerFactory factory=null;
		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager = factory.createEntityManager();
			String display="from Student";
			Query query=manager.createQuery(display);
			List <Student> list=query.getResultList();
			for(Student student1:list)
			{
				System.out.println(student1);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			if(factory!=null)
				factory.close();
			if(manager!=null)
				manager.close();

		}
	}


	public void displaySingleValue()
	{
		EntityManager manager=null;
		EntityManagerFactory factory=null;


		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager = factory.createEntityManager();
			System.out.println("Enter the your Id");
			int id=scan.nextInt();
			String display="from Student where sid=:sid";
			Query query=manager.createQuery(display);
			query.setParameter("sid",id);
			Student student=(Student) query.getSingleResult();
			System.out.println(student);

		} catch (Exception e) {
			throw new IdInvalidException();
		}
		finally {
			if(factory!=null)
				factory.close();
			if(manager!=null)
				manager.close();
		}
	}


	public void UpdateValue()
	{
		student=new Student();
		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			System.out.println("Enter the id you wish to update");
			int id=scan.nextInt();
			String display="from Student where sid=:id";
			Query query=manager.createQuery(display);
			query.setParameter("id",id);
			Student student=(Student) query.getSingleResult();
			if(student!=null)
			{
				System.out.println("do you want to update the name(y/n)");
				char alphabet=scan.next().charAt(0);
				if(alphabet=='y'||alphabet=='Y')
				{
					System.out.println("Enter the name");
					String name=scan.next();
					transaction.begin();
					String findby="update Student set sname=:name where sid=:id";
					Query query1=manager.createQuery(findby);
					query1.setParameter("name",name);
					query1.setParameter("id",id);
					query1.executeUpdate();
					transaction.commit();
				}	

				System.out.println("Do you want to update the phoneno(y/n)");
				char alphabet1=scan.next().charAt(0);
				if(alphabet1=='y'||alphabet1=='Y')
				{
					System.out.println("Enter the phoneno");
					long phone=scan.nextLong();
					transaction.begin();
					String findby="update Student set phone_no=:phone where id=:eid";
					Query query2=manager.createQuery(findby);
					query2.setParameter("phone",phone);
					query2.setParameter("eid",id);
					query2.executeUpdate();
					transaction.commit();
				}	
				System.out.println("Do you want to update the standard(y/n)");
				char alphabet2=scan.next().charAt(0);
				if(alphabet1=='y'||alphabet1=='Y')
				{
					System.out.println("Enter the Standard");
					int standard=scan.nextInt();
					transaction.begin();
					String findby="update Student set standard=:standard where id=:eid";
					Query query3=manager.createQuery(findby);
					query3.setParameter("standard",standard);
					query3.setParameter("eid",id);
					query3.executeUpdate();
					transaction.commit();
					System.out.println("Value updated");
				}
			}
		}
		catch (Exception e) {
			throw new IdInvalidException();
		}



	}

	public void deleteValue()
	{
		EntityManager manager=null;
		EntityManagerFactory factory=null;
		EntityTransaction transaction=null;
		try {
			factory=Persistence.createEntityManagerFactory("emp");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			System.out.println("Enter the id you wish to delete");
			int id=scan.nextInt();
			String display="from Student where sid=:id";
			Query query=manager.createQuery(display);
			query.setParameter("id",id);
			Student student=(Student) query.getSingleResult();
			if(student!=null)
			{


				transaction.begin();
				String display1="delete from Student where id=:id";
				Query query1=manager.createQuery(display1);
				query1.setParameter("id",id);
				query1.executeUpdate();
				System.out.println("values deleted");
				transaction.commit();
			}
		} catch (Exception e) {

			throw new IdInvalidException();

		}finally {
			if(factory!=null)
				factory.close();
			if(manager!=null)
				manager.close();

		}
	}
}