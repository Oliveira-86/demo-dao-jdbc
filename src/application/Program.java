package application;

import java.sql.Connection;

import db.DB;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "bob");
		System.out.println(obj);
	}

}
