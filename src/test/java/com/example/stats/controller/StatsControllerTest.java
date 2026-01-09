package com.example.stats.controller;

import com.example.stats.entity.Items;
import com.example.stats.entity.ItemsData;
import com.example.stats.pojo.ItemWithNameInt;
import com.example.stats.pojo.ListLeft;
import com.example.stats.pojo.Listing;
import com.example.stats.pojo.MainItemInt;
import com.example.stats.service.StatsService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatsController.class)
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statserv;
    
    // Static arrays for dummy data
    static MainItemInt[] mainItemIntArray;
    static ItemWithNameInt[] itemWithNameIntArray;
    static Listing[] listingArray;
    static ListLeft[] listLeftArray;
    static Items[] itemsArray;
    static ItemsData[] itemsDataArray;

    // Mock implementations for interface instantiation with fields
    static class MainItemIntImpl implements MainItemInt {
        private String id, name, itemname, itemid;
        public MainItemIntImpl(String id, String name, String itemname, String itemid) {
            this.id = id; this.name = name; this.itemname = itemname; this.itemid = itemid;
        }
        public String getId() { return id; }
        public String getName() { return name; }
        public String getItemname() { return itemname; }
        public String getItemid() { return itemid; }
    }
    static class ItemWithNameIntImpl implements ItemWithNameInt {
        private String mainitemname, itemname;
        public ItemWithNameIntImpl(String mainitemname, String itemname) {
            this.mainitemname = mainitemname; this.itemname = itemname;
        }
        public String getMainitemname() { return mainitemname; }
        public String getItemname() { return itemname; }
    }
    static class ListingImpl implements Listing {
        private String id, namedata, data, iditem;
        public ListingImpl(String id, String namedata, String data, String iditem) {
            this.id = id; this.namedata = namedata; this.data = data; this.iditem = iditem;
        }
        public String getId() { return id; }
        public String getNamedata() { return namedata; }
        public String getData() { return data; }
        public String getIditem() { return iditem; }
    }
    static class ListLeftImpl implements ListLeft {
        private String id, namedata, data, iditem, iditemname;
        public ListLeftImpl(String id, String namedata, String data, String iditem, String iditemname) {
            this.id = id; this.namedata = namedata; this.data = data; this.iditem = iditem; this.iditemname = iditemname;
        }
        public String getId() { return id; }
        public String getNamedata() { return namedata; }
        public String getData() { return data; }
        public String getIditem() { return iditem; }
        public String getIditemname() { return iditemname; }
    }

    @BeforeAll
    static void initTestArrays() {
        mainItemIntArray = new MainItemInt[] {
            new MainItemIntImpl("id1", "name1", "itemname1", "itemid1"),
            new MainItemIntImpl("id2", "name2", "itemname2", "itemid2"),
            new MainItemIntImpl("id3", "name3", "itemname3", "itemid3")
        };
        itemWithNameIntArray = new ItemWithNameInt[] {
            new ItemWithNameIntImpl("mainitemname1", "itemname1"),
            new ItemWithNameIntImpl("mainitemname2", "itemname2")
        };
        listingArray = new Listing[] {
            new ListingImpl("id1", "namedata1", "data1", "iditem1"),
            new ListingImpl("id2", "namedata2", "data2", "iditem2"),
            new ListingImpl("id3", "namedata3", "data3", "iditem3"),
            new ListingImpl("id4", "namedata4", "data4", "iditem4")
        };
        listLeftArray = new ListLeft[] {
            new ListLeftImpl("id1", "namedata1", "data1", "iditem1", "iditemname1"),
            new ListLeftImpl("id2", "namedata2", "data2", "iditem2", "iditemname2")
        };
        itemsArray = new Items[3];
        for (int i = 0; i < itemsArray.length; i++) {
            Items item = new Items();
            item.setName("itemName" + (i+1));
            itemsArray[i] = item;
        }
        itemsDataArray = new ItemsData[2];
        for (int i = 0; i < itemsDataArray.length; i++) {
            ItemsData data = new ItemsData();
            data.setData("data" + (i+1));
            itemsDataArray[i] = data;
        }
    }    

    @Test
    void testGetMainItems() throws Exception {
		when(statserv.getMainItems(anyList())).thenReturn(Arrays.asList(mainItemIntArray));
        mockMvc.perform(get("/mainitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].itemname").value("itemname2"));
    }

    @Test
    void testGetItemsAndMainItemName() throws Exception {
        when(statserv.getItemsAndMainItemName(anyString())).thenReturn(Arrays.asList(itemWithNameIntArray));
        mockMvc.perform(get("/mainitemanditemname").param("itemid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].mainitemname").value("mainitemname1"));
    }

    @Test
    void testGetItems() throws Exception {
        when(statserv.getItems(anyString(), anyList())).thenReturn(Arrays.asList(itemsArray));
        mockMvc.perform(get("/items").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("itemName1"));
    }

    @Test
    void testGetItemsAndData() throws Exception {
        when(statserv.getItemsAndData(anyString())).thenReturn(Arrays.asList(listingArray));
        mockMvc.perform(get("/itemsanddata").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("id1"));
    }

    @Test
    void testGetItemsAndNamesForOneItem() throws Exception {
        when(statserv.getItemsAndNamesForOneItem(anyString(), anyList())).thenReturn(Arrays.asList(itemsDataArray));
        mockMvc.perform(get("/oneitemsnames").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].data").value("data2"));
    }

    @Test
    void testGetItemsAndDataForOneItem() throws Exception {
        when(statserv.getItemsAndDataForOneItem(anyString(), anyString())).thenReturn(Arrays.asList(listLeftArray));
        mockMvc.perform(get("/oneitemsdata").param("mainitemid", "1").param("itemid", "2"))
                .andExpect(status().isOk())                              
                .andExpect(jsonPath("$.[1].namedata").value("namedata2"));

    }

    @Test
    void testGetMainItemImp() throws Exception {
        mockMvc.perform(get("/mainitemsimp"))
                .andExpect(status().isOk());
    }
}