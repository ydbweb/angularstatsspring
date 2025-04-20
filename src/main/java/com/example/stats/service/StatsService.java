package com.example.stats.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.stats.entity.Items;
import com.example.stats.entity.ItemsData;
import com.example.stats.entity.ItemsDataName;
import com.example.stats.entity.MainItem;
import com.example.stats.repository.ImportData;
import com.example.stats.repository.ItemsDataNameRepository;
import com.example.stats.pojo.ItemWithNameInt;
import com.example.stats.pojo.ListLeft;
import com.example.stats.pojo.Listing;
import com.example.stats.pojo.MainItemInt;
import com.example.stats.repository.ItemsDataRepository;
import com.example.stats.repository.ItemsRepository;
import com.example.stats.repository.MainItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class StatsService {
	
	@Autowired
	ImportData impDat;
	
	@Autowired
	MainItemRepository mirep;
	
	@Autowired
    ItemsRepository it;
	
	@Autowired
	ItemsDataRepository itdat;
	
	@Autowired
	ItemsDataNameRepository itnamedata;
	
    @PersistenceContext // or even @Autowired
    private EntityManager em;	
    
	public List<MainItemInt> getMainItems(List<String> excludeItemsTop){
		List<MainItemInt> mi = mirep.getMainItems(excludeItemsTop);
		
		return mi;
	}	
	
	
	public List<ItemWithNameInt> getItemsAndMainItemName(String iditem) {
		List<ItemWithNameInt> itres = it.getItemsAndMainItemName(iditem);
		
		return itres;
	}
	
	
	public List<Items> getItems(String mainitemid,List<String> excludeItemsTop){
		List<Items> mi = it.getItems(mainitemid, excludeItemsTop);
		
		return mi;
	}	
	
	
	public List<Listing> getItemsAndData(String mainitemid){
		List<Listing> mi = itdat.getItemsAndData(mainitemid);
		
		return mi;
	}	
	
	public List<ItemsData> getItemsAndNamesForOneItem(String mainitemid,List<String> excludeItemsTop){
		List<ItemsData> mi = itdat.getItemsAndNamesForOneItem(mainitemid,excludeItemsTop);
		
		return mi;
	}		
	
	public List<ListLeft> getItemsAndDataForOneItem(String mainitemid,String itemid){
		List<ListLeft> mi = itdat.getItemsAndDataForOneItem(mainitemid,itemid);
		
		return mi;
	}			
	
	
	
	@Transactional
	public void generateTables() throws FileNotFoundException, IOException {
		String[] recordsfiles= new String[3];
		recordsfiles[0]="c:/projects/cereal.csv";
		recordsfiles[1]="c:/projects/cars.csv";
		recordsfiles[2]="c:/projects/laptop.csv";
		String[] recordsnames= new String[3];
		recordsnames[0]="cereal";
		recordsnames[1]="cars";
		recordsnames[2]="laptop";		
		List<String[]> recs;
		for (int z=0;z<recordsfiles.length;z++) {
			recs=impDat.generateTables(recordsfiles[z]);
			
			
			
			int[] listtables= {0,1,2,3,4,5,6};		
			//recs.get(0);
			MainItem mainitem = new MainItem();
			mainitem.setName(recordsnames[z]);
			em.persist(mainitem);	
			 
			
			for (int i =0; i < 7;i++) {
				String[] tab1=recs.get(0);
				Items item=new Items();
				item.setMainitem(mainitem);
				item.setName(tab1[i]);
				em.persist(item);						
				for (int j =1; j < recs.size();j++) {
					ItemsData it = new ItemsData();
					it.setItem(item);		
					String[] tab2=recs.get(j);	
					ItemsDataName itemsdataname = new ItemsDataName();
	
					if (itnamedata.compareTsringToDb(tab2[0]).size()==0) {
						//itemsdataname.setName_data(tab2[0].replace("_", ""));
						itemsdataname.setName_data(tab2[0]);
						em.persist(itemsdataname);					
					}else {
						it.setItemsDataName(itnamedata.compareTsringToDb(tab2[0]).get(0));
					}
					it.setData(tab2[i]);
					if (i!=0)
					em.persist(it);	
					
				}				
			}
		}
	
	}
}
