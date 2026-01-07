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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Mock implementations for interface instantiation
    static class MainItemIntImpl implements MainItemInt {
        public String getId() { return "id"; }
        public String getName() { return "name"; }
        public String getItemname() { return "itemname"; }
        public String getItemid() { return "itemid"; }
    }
    static class ItemWithNameIntImpl implements ItemWithNameInt {
        public String getMainitemname() { return "mainitemname"; }
        public String getItemname() { return "itemname"; }
    }
    static class ListingImpl implements Listing {
        public String getId() { return "id"; }
        public String getNamedata() { return "namedata"; }
        public String getData() { return "data"; }
        public String getIditem() { return "iditem"; }
    }
    static class ListLeftImpl implements ListLeft {
        public String getId() { return "id"; }
        public String getNamedata() { return "namedata"; }
        public String getData() { return "data"; }
        public String getIditem() { return "iditem"; }
        public String getIditemname() { return "iditemname"; }
    }

    @Test
    void testGetMainItemsReturnsList() {
        List<MainItemInt> expected = Arrays.asList(new MainItemIntImpl(), new MainItemIntImpl());
        when(mirep.getMainItems(anyList())).thenReturn(expected);
        List<MainItemInt> result = statsService.getMainItems(Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndMainItemNameReturnsList() {
        List<ItemWithNameInt> expected = Arrays.asList(new ItemWithNameIntImpl(), new ItemWithNameIntImpl());
        when(it.getItemsAndMainItemName(anyString())).thenReturn(expected);
        List<ItemWithNameInt> result = statsService.getItemsAndMainItemName("iditem");
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsReturnsList() {
        List<Items> expected = Arrays.asList(new Items(), new Items());
        when(it.getItems(anyString(), anyList())).thenReturn(expected);
        List<Items> result = statsService.getItems("mainitemid", Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndDataReturnsList() {
        List<Listing> expected = Arrays.asList(new ListingImpl(), new ListingImpl());
        when(itdat.getItemsAndData(anyString())).thenReturn(expected);
        List<Listing> result = statsService.getItemsAndData("mainitemid");
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndNamesForOneItemReturnsList() {
        List<ItemsData> expected = Arrays.asList(new ItemsData(), new ItemsData());
        when(itdat.getItemsAndNamesForOneItem(anyString(), anyList())).thenReturn(expected);
        List<ItemsData> result = statsService.getItemsAndNamesForOneItem("mainitemid", Collections.singletonList("exclude"));
        assertEquals(expected, result);
    }

    @Test
    void testGetItemsAndDataForOneItemReturnsList() {
        List<ListLeft> expected = Arrays.asList(new ListLeftImpl(), new ListLeftImpl());
        when(itdat.getItemsAndDataForOneItem(anyString(), anyString())).thenReturn(expected);
        List<ListLeft> result = statsService.getItemsAndDataForOneItem("mainitemid", "itemid");
        assertEquals(expected, result);
    }

    @Test
    void testGenerateTablesSuccess() throws IOException {
        String[] files = {"c:/projects/cereal.csv", "c:/projects/cars.csv", "c:/projects/laptop.csv"};
        String[] names = {"cereal", "cars", "laptop"};
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