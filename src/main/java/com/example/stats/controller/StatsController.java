package com.example.stats.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.stats.entity.MainItem;
import com.example.stats.entity.Items;
import com.example.stats.entity.ItemsData;
import com.example.stats.pojo.ItemWithNameInt;
import com.example.stats.pojo.ListLeft;
import com.example.stats.pojo.Listing;
import com.example.stats.pojo.MainItemInt;
import com.example.stats.service.StatsService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	@GetMapping(value = "/mainitemanditemname")
	public List<ItemWithNameInt> getItemsAndMainItemName(@RequestParam String itemid) {
		return statserv.getItemsAndMainItemName(itemid);
	}		
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/items")
	public List<Items> getItems(@RequestParam String mainitemid) {
		return statserv.getItems(mainitemid, this.excludeItemsTop);
	}		
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/itemsanddata")
	public List<Listing> getItemsAndData(@RequestParam String mainitemid) {
		return statserv.getItemsAndData(mainitemid);
	}	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/oneitemsnames")
	public List<ItemsData> getItemsAndNamesForOneItem(@RequestParam String mainitemid) {
		return statserv.getItemsAndNamesForOneItem(mainitemid, this.excludeItemsTop);
	}	
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/oneitemsdata")
	public List<ListLeft> getItemsAndDataForOneItem(@RequestParam String mainitemid, @RequestParam String itemid) {
		return statserv.getItemsAndDataForOneItem(mainitemid, itemid);
	}	
		
	
	
/*
	@CrossOrigin(origins = "*")
	@GetMapping("/mainitemsimp")
	public void getMainItem() throws FileNotFoundException, IOException {
		statserv.generateTables();
	}	
*/
	
}