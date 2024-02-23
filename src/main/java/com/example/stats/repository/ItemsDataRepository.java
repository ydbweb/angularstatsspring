package com.example.stats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.stats.entity.ItemsData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Repository
public interface ItemsDataRepository extends CrudRepository<ItemsData, Integer> {	
	
	@Query(value= "SELECT itdat.* FROM items_data itdat "
			     +"LEFT JOIN items it ON (it.id=itdat.id_item) "
			     +"WHERE it.mainitem_id = :mainitemid ORDER BY itdat.id_item ASC"
			,nativeQuery=true)	
	public List<ItemsData> getItemsAndData(String mainitemid);
	
	@Query(value= "SELECT itdat.* FROM items_data itdat "
		     +"LEFT JOIN items it ON (it.id=itdat.id_item) "
		     +"WHERE it.mainitem_id = :mainitemid AND it.id IN (:excludeItemsTop) ORDER BY itdat.id_item ASC"
		,nativeQuery=true)		
	public List<ItemsData> getItemsAndNamesForOneItem(String mainitemid, List<String> excludeItemsTop);	
	
	@Query(value= "SELECT * FROM items_data itdat2 " 
			     +"LEFT JOIN items it ON (itdat2.id_item = it.id) "
			     +"WHERE it.mainitem_id = :mainitemid AND it.id IN (:excludeItemsTop) ORDER BY itdat2.id_item ASC"
		,nativeQuery=true)		
	public List<ItemsData> getItemsAndDataForOneItem(String mainitemid, List<String> excludeItemsTop);
	

}
