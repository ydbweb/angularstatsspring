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
	
	@CrossOrigin(origins = "*")
	@GetMapping("/mainitems")
	public List<MainItemInt> getMainItems() throws JsonProcessingException{

		return statserv.getMainItems(this.excludeItemsTop);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/mainitemanditemname")
	public String getItemsAndMainItemName(@RequestParam String itemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndMainItemName(itemid));
	}		
	
	@CrossOrigin(origins = "*")
	@GetMapping("/items")
	public String getItems(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItems(mainitemid, this.excludeItemsTop));
	}		
	
	@CrossOrigin(origins = "*")
	@GetMapping("/itemsanddata")
	public String getItemsAndData(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndData(mainitemid));
	}	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/oneitemsnames")
	public String getItemsAndNamesForOneItem(@RequestParam String mainitemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndNamesForOneItem(mainitemid, this.excludeItemsTop));
	}	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/oneitemsdata")
	public String getItemsAndDataForOneItem(@RequestParam String mainitemid, @RequestParam String itemid) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper.writeValueAsString(statserv.getItemsAndDataForOneItem(mainitemid, itemid));
	}	
		
	
	

	@CrossOrigin(origins = "*")
	@GetMapping("/mainitemsimp")
	public void getMainItem() throws FileNotFoundException, IOException {
		statserv.generateTables();
	}	

	
}
