package com.example.stats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.stats.entity.Items;
import com.example.stats.entity.MainItem;
import com.example.stats.pojo.ItemWithNameInt;
import com.example.stats.pojo.MainItemInt;


@Repository
public interface ItemsRepository extends CrudRepository<Items, Integer> {
	
	@Query(value="SELECT * FROM items it WHERE it.mainitem_id = :mainitemid AND"
			+ " it.id NOT IN (:excludeItemsTop) ORDER BY id ASC"
			,nativeQuery=true)
	List<Items> getItems(String mainitemid,List<String> excludeItemsTop);
	
	@Query(value="SELECT mi.name as mainitemname,it.name as itemname"
			+ " FROM  items it "
			+ "LEFT JOIN main_item mi ON (it.mainitem_id=mi.id)"			
			+ "WHERE it.id=:iditem"
		,nativeQuery=true)
    List<ItemWithNameInt> getItemsAndMainItemName(String iditem);		

}
