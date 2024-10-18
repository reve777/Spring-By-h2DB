package com.portfolio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.portfolio.controller.ForexController;
import com.portfolio.entity.Forex;
import com.portfolio.service.ForexService;

@ExtendWith(MockitoExtension.class)
class forexControllerTest {

	@Mock
	private ForexService forexService;

	@InjectMocks
	private ForexController forexController;

	private Forex testForex;
	private List<Forex> testForexList;

	@BeforeEach
	void setUp() {
		testForex = new Forex();
		testForex.setCode("USD");
		testForex.setDescription("US Dollar");
		testForex.setRate("30.123");
		testForex.setRate_float(30.123);
		testForex.setUpdateTime("2024/10/18 15:51:21");

		testForexList = Arrays.asList(testForex);
	}

	@Test
	void insertApi_Success() {
	    when(forexService.getForexAll()).thenReturn(testForexList);

	    Object result = forexController.insertApi();

	    assertEquals(testForexList, result);

	    verify(forexService, times(1)).insertApi();
	}

	@Test
	void getupdate_Success() {
	
	    when(forexService.getForexAll()).thenReturn(testForexList);

	    Object result = forexController.getupdate();

	    assertEquals(testForexList, result);

	    verify(forexService, times(1)).updateApi();
	}

	@Test
	void getApiData_Success() {

		when(forexService.getApiData()).thenReturn(testForexList);

		Object result = forexController.getApiData();

		assertEquals(testForexList, result);
		verify(forexService, times(2)).getApiData();
	}

	@Test
	void getForex_Success() {

		when(forexService.getForexAll()).thenReturn(testForexList);

		Object result = forexController.getForex();

		assertEquals(testForexList, result);
		verify(forexService, times(2)).getForexAll();
	}

	@Test
	void getOne_Success() {

		when(forexService.getForexOne("USD")).thenReturn(testForex);

		Forex result = forexController.getOne("USD");

		assertNotNull(result);
		assertEquals("USD", result.getCode());
		verify(forexService, times(1)).getForexOne("USD");
	}

	@Test
	void deleteForex_Success() {

		Boolean result = forexController.deleteForex("USD");

		assertTrue(result);
		verify(forexService, times(1)).deleteForexByCode("USD");
	}
}