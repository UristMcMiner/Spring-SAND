package de.dhbw_mannheim.tinf13itns.internaliterator;

import java.util.List;

public class InternalIterationDemo {

    public static void main(String[] args) {
        List<Employee> list = Employee.getEmpList();
        list.stream().filter(e->e.getSal()>3000).forEach(e->System.out.println(e.getSal()));
        System.out.println("Salary before update");
        list.forEach(e->System.out.print(e.getSal()+"  "));
        list.forEach(e->e.setSal(e.getSal()*2));
        System.out.println("\nSalary after update");
        list.forEach(e->System.out.print(e.getSal()+"  "));
    }
}
