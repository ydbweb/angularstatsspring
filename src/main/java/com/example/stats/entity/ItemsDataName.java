package com.example.stats.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemsDataName {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name_data;
	
    @JsonIgnore
    @OneToMany(mappedBy="itemsDataName")
    private Set<ItemsData> itemsdata;

	public ItemsDataName(Long id, String name_data, Set<ItemsData> itemsdata) {
		super();
		this.id = id;
		this.name_data = name_data;
		this.itemsdata = itemsdata;
	}

	public ItemsDataName() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_data() {
		return name_data;
	}

	public void setName_data(String name_data) {
		this.name_data = name_data;
	}

	public Set<ItemsData> getItemsdata() {
		return itemsdata;
	}

	public void setItemsdata(Set<ItemsData> itemsdata) {
		this.itemsdata = itemsdata;
	}
    
    

}