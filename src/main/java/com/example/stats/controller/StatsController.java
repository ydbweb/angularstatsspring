package com.example.stats.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.stats.entity.MainItem;
import com.example.stats.pojo.MainItemInt;
import com.example.stats.service.StatsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class StatsController {
	
	@Autowired
	StatsService statserv;
	
	private List<String> excludeItemsTop=Arrays.asList("19030", "20879", "23729"); 
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/mainitems")
	public List<MainItemInt> getMainItems() throws JsonProcessingException{

		return statserv.getMainItems(this.excludeItemsTop);
	}
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/mainitemanditemname")
	public String getItemsAndMainItemName(@RequestParam String itemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndMainItemName(itemid));
	}		
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/items")
	public String getItems(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItems(mainitemid, this.excludeItemsTop));
	}		
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/itemsanddata")
	public String getItemsAndData(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndData(mainitemid));
	}	
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/oneitemsnames")
	public String getItemsAndNamesForOneItem(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndNamesForOneItem(mainitemid, this.excludeItemsTop));
	}	
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/oneitemsdata")
	public String getItemsAndDataForOneItem(@RequestParam String mainitemid, String itemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndDataForOneItem(mainitemid, itemid, this.excludeItemsTop));
	}	
		
	
	/*

	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/mainitemsimp")
	public List<String[]> getMainItem() throws FileNotFoundException, IOException {
		return statserv.generateTables();
	}	

	*/
}
