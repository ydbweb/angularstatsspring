package com.example.stats.repository;

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
	
    public List<String[]> generateTables(String files1) throws FileNotFoundException, IOException{    
    	List<String[]> records=new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(files1).toString()))) {
	    	
	        String line;
	        Integer i=0;
	        while ((line = br.readLine()) != null) {
	        	String[] values = line.split(",",-1);
       	
	        	records.add(values);

	        }
	    }
		return records; 
    
    } 	
    

}
