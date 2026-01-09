package com.example.stats.service;

import com.example.stats.entity.Items;
import com.example.stats.entity.ItemsData;
import com.example.stats.entity.ItemsDataName;
import com.example.stats.entity.MainItem;
import com.example.stats.pojo.ItemWithNameInt;
import com.example.stats.pojo.ListLeft;
import com.example.stats.pojo.Listing;
import com.example.stats.pojo.MainItemInt;
import com.example.stats.repository.ImportData;
import com.example.stats.repository.ItemsDataNameRepository;
import com.example.stats.repository.ItemsDataRepository;
import com.example.stats.repository.ItemsRepository;
import com.example.stats.repository.MainItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsServiceTest {
    @Mock
    ImportData impDat;
    @Mock
    MainItemRepository mirep;
    @Mock
    ItemsRepository it;
    @Mock
    ItemsDataRepository itdat;
    @Mock
    ItemsDataNameRepository itnamedata;
    @Mock
    EntityManager em;

    @InjectMocks
    StatsService statsService;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMainItemsReturnsList() {
        List<MainItemInt> expected = Arrays.asList(mainItemIntArray);
        when(mirep.getMainItems(anyList())).thenReturn(expected);
        List<MainItemInt> result = statsService.getMainItems(Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndMainItemNameReturnsList() {
        List<ItemWithNameInt> expected = Arrays.asList(itemWithNameIntArray);
        when(it.getItemsAndMainItemName(anyString())).thenReturn(expected);
        List<ItemWithNameInt> result = statsService.getItemsAndMainItemName("iditem");
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsReturnsList() {
        List<Items> expected = Arrays.asList(itemsArray);
        when(it.getItems(anyString(), anyList())).thenReturn(expected);
        List<Items> result = statsService.getItems("mainitemid", Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndDataReturnsList() {
        List<Listing> expected = Arrays.asList(listingArray);
        when(itdat.getItemsAndData(anyString())).thenReturn(expected);
        List<Listing> result = statsService.getItemsAndData("mainitemid");
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndNamesForOneItemReturnsList() {
        List<ItemsData> expected = Arrays.asList(itemsDataArray);
        when(itdat.getItemsAndNamesForOneItem(anyString(), anyList())).thenReturn(expected);
        List<ItemsData> result = statsService.getItemsAndNamesForOneItem("mainitemid", Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndDataForOneItemReturnsList() {
        List<ListLeft> expected = Arrays.asList(listLeftArray);
        when(itdat.getItemsAndDataForOneItem(anyString(), anyString())).thenReturn(expected);
        List<ListLeft> result = statsService.getItemsAndDataForOneItem("mainitemid", "itemid");
        assertEquals(expected, result);
    }

    @Test
    void testGenerateTablesSuccess() throws IOException {
        List<String[]> recs = new ArrayList<>();
        String[] header = {"col1", "col2", "col3", "col4", "col5", "col6", "col7"};
        String[] row = {"row1", "row2", "row3", "row4", "row5", "row6", "row7"};
        recs.add(header);
        recs.add(row);
        when(impDat.generateTables(anyString())).thenReturn(recs);
        when(itnamedata.compareTsringToDb(anyString())).thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> statsService.generateTables());
        verify(em, atLeastOnce()).persist(any(MainItem.class));
        verify(em, atLeastOnce()).persist(any(Items.class));
        verify(em, atLeastOnce()).persist(any(ItemsData.class));
        verify(em, atLeastOnce()).persist(any(ItemsDataName.class));
    }

    @Test
    void testGenerateTablesFileNotFoundException() throws IOException {
        when(impDat.generateTables(anyString())).thenThrow(new FileNotFoundException());
        assertThrows(FileNotFoundException.class, () -> statsService.generateTables());
    }

    @Test
    void testGenerateTablesIOException() throws IOException {
        when(impDat.generateTables(anyString())).thenThrow(new IOException());
        assertThrows(IOException.class, () -> statsService.generateTables());
    }

    @Test
    void testGetMainItemsWithEmptyExcludeList() {
        when(mirep.getMainItems(anyList())).thenReturn(Collections.emptyList());
        List<MainItemInt> result = statsService.getMainItems(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetItemsWithNullMainItemId() {
        when(it.getItems(isNull(), anyList())).thenReturn(Collections.emptyList());
        List<Items> result = statsService.getItems(null, Collections.singletonList("exclude"));
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetItemsAndMainItemNameWithNullId() {
        when(it.getItemsAndMainItemName(isNull())).thenReturn(Collections.emptyList());
        List<ItemWithNameInt> result = statsService.getItemsAndMainItemName(null);
        assertTrue(result.isEmpty());
    }
}