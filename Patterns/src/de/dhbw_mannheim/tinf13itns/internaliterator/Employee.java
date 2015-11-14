package de.dhbw_mannheim.tinf13itns.internaliterator;

import java.util.ArrayList;
import java.util.List;

public class Employee {

	public int id;
	 public String name;
	 private int sal;
	 public Employee(int id,String name,int sal  ){
	     this.id = id;
	     this.name = name;
	     this.sal = sal;
	 }
	 public void setSal(int sal) {
	     this.sal = sal;
	 }
	 public int getSal() {
	     return sal;
	 }
	 
	 public static List<Employee> getEmpList(){
	     List<Employee> list = new ArrayList();
	     list.add(new Employee(1, "A", 2000));
	     list.add(new Employee(2, "B", 3000));
	     list.add(new Employee(3, "C", 4000));
	     list.add(new Employee(4, "D", 5000));
	     return list;
	 }

}
