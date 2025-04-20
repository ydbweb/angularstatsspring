package com.example.stats.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemsData {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String data;

	@ManyToOne
    @JoinColumn(name="id_item", nullable=false)
    private Items item;
	
	@ManyToOne
    @JoinColumn(name="id_item_name", nullable=true)
    private ItemsDataName itemsDataName;
    
	public ItemsData() {
	}

	public ItemsData(Long id, String data, Items item) {
		super();
		this.id = id;
		this.data = data;
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}
	

	public ItemsDataName getItemsDataName() {
		return itemsDataName;
	}

	public void setItemsDataName(ItemsDataName itemsDataName) {
		this.itemsDataName = itemsDataName;
	}

	@Override
	public String toString() {
		return "ItemsData [id=" + id + ", data=" + data + ", item=" + item + "]";
	}	
	
	
}