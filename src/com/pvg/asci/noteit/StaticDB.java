package com.pvg.asci.noteit;

import android.content.Context;

public class StaticDB {

	Context ourContext;
	NoteItDB db;

	public StaticDB(Context context) {
		ourContext = context;
	}

	public void addUsers() {
		try{
			db = new NoteItDB(ourContext);
			db.open();
			db.addPassKey("CP", "cp  cp", "FALSE");
			db.addPassKey("Prachi Bhalerao", "p@asciTech", "FALSE");
			db.addPassKey("Saurabh Porwal", "Sniper@89", "FALSE");
			db.addPassKey("Vivek Pandita", "vivekrajat", "FALSE");
			db.addPassKey("Akshay Bhondave", "akshay26", "FALSE");
			db.addPassKey("moiz merchant", "moizhusen", "FALSE");
			db.addPassKey("Pooja Dilip Chavan", "ashapri44", "FALSE");
			db.addPassKey("Shashank Rai", "Jill2525", "FALSE");
			db.addPassKey("Dhwani Rajkumar Girwalkar", "Dhwani10", "FALSE");
			db.addPassKey("AKSHAY SURENDRA KARNAWAT", "qazwsxedc", "FALSE");
			db.addPassKey("Vivek Bhaskar Nathe", "vivsteinasci", "FALSE");
			db.addPassKey("Neeraj Nitin Deshpande", "neeraj1995#", "FALSE");
			db.addPassKey("Sharvari Natu", "Idlisambar7588288", "FALSE");
			db.addPassKey("Rushikesh Sane", "poojarushisane", "FALSE");
			db.addPassKey("Prakhar Srivastava", "25884077", "FALSE");
			db.addPassKey("Apoorva Shewale", "ascipassword", "FALSE");
			db.addPassKey("Piyush Kumar", "till i collapse", "FALSE");
			db.addPassKey("Saurabh", "saurabhsd", "FALSE");
			db.addPassKey("Bhawana Purandare", "bhawana20", "FALSE");
			db.addPassKey("prerana babbar", "ascipasski451", "FALSE");
			db.addPassKey("NIKITA ANIL GADE", "zanahisangatnikita", "FALSE");
			db.addPassKey("Waghole Samiksha Namdeo", "samw2701", "FALSE");
			db.addPassKey("Manish Ladkat", "HL6911", "FALSE");
			db.addPassKey("Shubham Pachpute", "17shubham", "FALSE");
			db.addPassKey("Tanvi D Kulkarni", "tanvidk1", "FALSE");
			db.addPassKey("Deepen patel", "Password", "FALSE");
			db.addPassKey("Apurva b krishnappanavar", "apurvakrish", "FALSE");
			db.addPassKey("Pratiksha Shashikant Jagtap", "pratisha19", "FALSE");
			db.addPassKey("Akshata Dilip Dabade", "#@akshata#@", "FALSE");
			db.addPassKey("Harsha  Vishvanath Mahajan", "Harsha@95", "FALSE");
			db.addPassKey("Namrata Devbhankar", "namrata0407", "FALSE");
			db.addPassKey("Akshay", "helloasci", "FALSE");
			db.addPassKey("Maithili S Kadam", "liebemutter", "FALSE");
			db.addPassKey("SHREYA KARVE", "shreya1996", "FALSE");
			db.addPassKey("Sharvari Deshpande", "s11s22v33g44", "FALSE");
			db.addPassKey("Komal Sorte ", "KAS3964@15 ", "FALSE");
			db.addPassKey("Radhika Vijay Chtinis", "ASCIPassKey", "FALSE");
			db.addPassKey("Anusha Rao", "@#anurao0295", "FALSE");
			db.addPassKey("Paramveer Singh", "nanak1469", "FALSE");
			db.addPassKey("Suyog Gandhi", "suga@asci5", "FALSE");
			
			
		}catch(Exception e){
			
		}finally{
			db.close();
		}
	}

	public void addGames() {
		try{
			db = new NoteItDB(ourContext);
			db.open();
			db.addGame("Counter Strike", 200, 5);
			db.addGame("Age Of Empires", 150, 3);
			db.addGame("FIFA", 50, 1);
			db.addGame("Need For Speed", 50, 1);
			db.addGame("Pocket Tanks", 30, 1);
		}catch(Exception e){
			
		}finally{
			db.close();
		}
	}
}
