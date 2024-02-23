package com.example.stats.pojo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ImportData {
	
    public List<String[]> generateTables() throws FileNotFoundException, IOException{    
    	List<String[]> records=new ArrayList<>();
	    System.out.println("bbb");
	    try (BufferedReader br = new BufferedReader(new FileReader(Paths.get("/home/geronimo/data/own/cerealssemi.csv").toString()))) {
	    	
	        String line;
	        Integer i=0;
	        while ((line = br.readLine()) != null) {
	        	String[] values = line.split(";",-1);
       	
	        	records.add(values);

	        }
	    }
		return records; 
    
    } 	
    

}
