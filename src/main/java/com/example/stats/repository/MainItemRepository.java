package com.example.stats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.stats.entity.MainItem;
import com.example.stats.pojo.MainItemInt;


@Repository
public interface MainItemRepository extends CrudRepository<MainItem, Long> {	
	
	@Query(value="SELECT mi.id as id,mi.name, it.name as itemname, it.id as itemid"
				+ " FROM main_item mi "
				+ "LEFT JOIN items it ON (it.mainitem_id=mi.id)"
				+ "WHERE it.id NOT IN (:excludeItemsTop)"			
				+ "ORDER BY mi.id DESC,it.id ASC"
			,nativeQuery=true)
	List<MainItemInt> getMainItems(List<String> excludeItemsTop);
	


}
