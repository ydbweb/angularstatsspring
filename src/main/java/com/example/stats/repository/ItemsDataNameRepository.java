package com.example.stats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.stats.entity.ItemsData;
import com.example.stats.entity.ItemsDataName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Repository
public interface ItemsDataNameRepository extends CrudRepository<ItemsDataName, Long> {	
	
	@Query(value= "SELECT itdatname.* FROM items_data_name itdatname "
			     +"WHERE itdatname.name_data LIKE :strname LIMIT 1"
			,nativeQuery=true)	
	public List<ItemsDataName> compareTsringToDb(String strname);
	

}
