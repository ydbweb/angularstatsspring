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
public class Items {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	
    @ManyToOne
    @JoinColumn(name="mainitem_id", nullable=true)
    private MainItem mainitem;	
    
    @JsonIgnore
    @OneToMany(mappedBy="item")
	private Set<ItemsData> itemsdata;
    
	public Items() {
	}    

	public Items(Long id, String name, MainItem mainitem, Set<ItemsData> itemsdata) {
		super();
		this.id = id;
		this.name = name;
		this.mainitem = mainitem;
		this.itemsdata = itemsdata;
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

	public MainItem getMainitem() {
		return mainitem;
	}

	public void setMainitem(MainItem mainitem) {
		this.mainitem = mainitem;
	}

	public Set<ItemsData> getItemsdata() {
		return itemsdata;
	}

	public void setItemsdata(Set<ItemsData> itemsdata) {
		this.itemsdata = itemsdata;
	} 
	
	
    
    

}
