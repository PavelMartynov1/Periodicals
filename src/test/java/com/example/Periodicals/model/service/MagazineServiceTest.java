package com.example.Periodicals.model.service;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.model.dao.DaoFactory;
import com.example.Periodicals.model.dao.MagazineDao;
import com.example.Periodicals.model.dao.impl.JDBCMagazineDao;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.MagazineSearchParameters;
import com.example.Periodicals.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MagazineServiceTest {
    @Mock
    private MagazineDao magazineDao;
    @Mock
    DaoFactory factory;
    @InjectMocks
    private MagazineService magazineService;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void find() {
        Magazine magazine=new Magazine.Builder()
                .setId(5)
                .setName("Discovery").build();
        //when(factory.createMagazineDao()).thenReturn(new JDBCMagazineDao());
        when(magazineDao.find(magazine.getId())).thenReturn(Optional.of(magazine));
        Optional<Magazine> actual=magazineService.find(magazine.getId());
        assertEquals(magazine,actual.get());
    }



    @Test
    public void getCountWithParams() {
        MockitoAnnotations.initMocks(this);
        MagazineSearchParameters parameters = new MagazineSearchParameters.Builder()
                .setCategory(new String("5"))
                .setName("Discovery").build();
        int expectedCount = 1;
        when(magazineDao.countWithParams(parameters)).thenReturn(expectedCount);
        int actual = magazineService.getCountWithParams(parameters);
        assertEquals(expectedCount, actual);

    }

    @Test
    public void countAll() {
        int expectedNumberOfPeriodicals = 4;
        when(magazineDao.countAll()).thenReturn(expectedNumberOfPeriodicals);
        int actual = magazineService.countAll();
        assertEquals(expectedNumberOfPeriodicals, actual);
    }
@Test
public void deleteShouldReturnFalseIfEmpty(){
    Magazine magazine=new Magazine();
    User user=new User();
    when(magazineDao.delete(magazine,user)).thenReturn(null);
    boolean result=magazineService.delete(magazine,user);
    assertFalse(result);
}
    @Test
    public void delete() {
        Magazine magazine=new Magazine();
        User user=new User();
        when(magazineDao.delete(magazine,user)).thenReturn(magazine);
        boolean status= magazineService.delete(magazine,user);
        assertTrue(status);
    }

    @Test
    public void findAll() {
        MockitoAnnotations.initMocks(this);
        int expectedSize = 4;
        List<Magazine> magazines = Arrays.asList(new Magazine(), new Magazine(), new Magazine(), new Magazine());
        when(magazineDao.findAll()).thenReturn(magazines);
        List<Magazine> magazineList = magazineService.findAll();
        assertEquals(expectedSize, magazineList.size());
    }

    @Test
    public void findBySearchParameters() {
        MagazineSearchParameters parameters=new MagazineSearchParameters.Builder()
                .setName("Discovery")
                .build();
        List<Magazine> list=Arrays.asList(new Magazine.Builder().setName("Discovery").build());
        when(magazineDao.findWithParameters(parameters)).thenReturn(list);
        List<Magazine> actual=magazineService.findBySearchParameters(parameters);
        assertEquals(list,actual);
    }

    @Test
    public void addMagazineShouldReturnFalseIFNull(){
        Category finance=new Category();
        finance.setName("Finance");
        MagazineCreationInput input=new MagazineCreationInput.Builder()
                .setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice("45")
                .setDescription("Descr")
                .build();
        Magazine magazine=new Magazine();
        magazine.setName("Forbes");
        when(magazineDao.insert(any())).thenReturn(null);
        boolean result=magazineService.addMagazine(input);
        assertFalse(result);
    }
    @Test
    public void addMagazine() {
        Category finance=new Category();
        finance.setName("Finance");
        MagazineCreationInput input=new MagazineCreationInput.Builder()
                .setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice("45")
                .setDescription("Descr")
                .build();
        Magazine magazine=new Magazine();
        magazine.setName("Forbes");
        when(magazineDao.insert(any())).thenReturn(magazine);
       // when(magazineService.buildMagazine(input)).thenReturn(magazine);
        boolean status=magazineService.addMagazine(input);
    assertTrue(status);
    }

    @Test
    public void buildMagazine() {
        Category finance=new Category();
        finance.setName("Finance");
        MagazineCreationInput input=new MagazineCreationInput.Builder()
                .setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice("45")
                .setDescription("Descr")
                .build();
        MagazineService service=new MagazineService();
        String expectedName=service.buildMagazine(input).getName();
        assertEquals("Forbes",expectedName);
    }

    @Test
    public void updateMagazineShouldReturnTrueIfPresent() {
        Category finance=new Category();
        finance.setName("Finance");
        MagazineCreationInput input=new MagazineCreationInput.Builder()
                .setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice("45")
                .setDescription("Descr")
                .build();
        Magazine magazine=new Magazine.Builder().setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice(new BigDecimal("45"))
                .setDescription("Descr")
                .build();

        when(magazineDao.update(any())).thenReturn(magazine);
        boolean result=magazineService.updateMagazine(input);
        assertTrue(result);
    }
    @Test
    public void updateMagazineShouldReturnFalseIsEmpty(){
        Category finance=new Category();
        finance.setName("Finance");
        MagazineCreationInput input=new MagazineCreationInput.Builder()
                .setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice("45")
                .setDescription("Descr")
                .build();
        Magazine magazine=new Magazine.Builder().setName("Forbes")
                .setPublisher("Forbs.Inc")
                .setCategory(finance)
                .setPrice(new BigDecimal("45"))
                .setDescription("Descr")
                .build();
        when(magazineDao.update(any())).thenReturn(null);
        boolean result=magazineService.updateMagazine(input);
        assertFalse(result);
    }
}