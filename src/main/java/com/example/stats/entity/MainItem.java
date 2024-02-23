package com.example.stats.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MainItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
	@JsonIgnore
    @OneToMany(mappedBy="mainitem")
	private Set<Items> items;

	public MainItem() {
	}	
    
	public MainItem(Long id, String name, Set<Items> items) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
	}



	public Long getid() {
		return id;
	}

	public void setid(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Items> getItems() {
		return this.items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}	
    
    

}
