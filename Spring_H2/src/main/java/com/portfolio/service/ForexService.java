package com.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.controller.ForexController;
import com.portfolio.entity.Forex;
import com.portfolio.repository.ForexRepository;

@Service
public class ForexService {

	private static final Logger logger = LoggerFactory.getLogger(ForexController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ForexRepository forexRepository;

	private static final String API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	@SuppressWarnings("unchecked")
	public Boolean insertApi() {

		try {
			byte[] response = restTemplate.getForObject(API_URL, byte[].class);

			if (response != null && response.length > 0) {
				String responseString = new String(response);

				logger.info("Received data: {}", responseString);
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> responseData = objectMapper.readValue(responseString,
						new TypeReference<Map<String, Object>>() {
						});
				SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH);
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Map<String, Object> time = (Map<String, Object>) responseData.get("time");

				String updated = time.get("updated").toString();

				String formattedDate = outputFormat.format(inputFormat.parse(updated));

				System.out.println(formattedDate);

				Map<String, Object> bpi = (Map<String, Object>) responseData.get("bpi");
				if (!bpi.isEmpty()) {
					saveForexData((Map<String, Object>) bpi.get("USD"), formattedDate);
					saveForexData((Map<String, Object>) bpi.get("GBP"), formattedDate);
					saveForexData((Map<String, Object>) bpi.get("EUR"), formattedDate);
					return true;
				} else {
					logger.warn("No data received from API");
				}
			} else {
				logger.warn("No data received from API");
			}
		} catch (Exception e) {
			logger.error("Error occurred while processing forex data", e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean insertApi(String code) {

		try {
			byte[] response = restTemplate.getForObject(API_URL, byte[].class);

			if (response != null && response.length > 0) {
				String responseString = new String(response);

				logger.info("Received data: {}", responseString);
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> responseData = objectMapper.readValue(responseString,
						new TypeReference<Map<String, Object>>() {
						});
				SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH);
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Map<String, Object> time = (Map<String, Object>) responseData.get("time");

				String updated = time.get("updated").toString();

				String formattedDate = outputFormat.format(inputFormat.parse(updated));

				System.out.println(formattedDate);

				Map<String, Object> bpi = (Map<String, Object>) responseData.get("bpi");
				if (!bpi.isEmpty()) {
					switch (code.toLowerCase()) {
					case "usd":
						saveForexData((Map<String, Object>) bpi.get("USD"), formattedDate);
						break;
					case "gbp":
						saveForexData((Map<String, Object>) bpi.get("GBP"), formattedDate);
						break;
					case "eur":
						saveForexData((Map<String, Object>) bpi.get("EUR"), formattedDate);
						break;
					default:
						System.out.println("未處理的貨幣代碼: " + code);
						break;
					}
					return true;
				} else {
					logger.warn("No data received from API");
				}
			} else {
				logger.warn("No data received from API");
			}
		} catch (Exception e) {
			logger.error("Error occurred while processing forex data", e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String updateApi() {

		try {
			byte[] response = restTemplate.getForObject(API_URL, byte[].class);

			if (response != null && response.length > 0) {
				String responseString = new String(response);

				logger.info("Received data: {}", responseString);
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> responseData = objectMapper.readValue(responseString,
						new TypeReference<Map<String, Object>>() {
						});
				SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH);
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

				Map<String, Object> time = (Map<String, Object>) responseData.get("time");

				String updated = time.get("updated").toString();

				String formattedDate = outputFormat.format(inputFormat.parse(updated));

				System.out.println(formattedDate);

				String dateAString = "";
				String dateBString = "";
				Boolean updateCheck = false;
				if (getForexAll().size() > 0) {
					getForexAll().get(0).getUpdateTime();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date dateA = sdf.parse(formattedDate);
					Date dateB = sdf.parse(getForexAll().get(0).getUpdateTime());
					if (dateB.after(dateA) || dateB.equals(dateA)) {
						updateCheck = true;
						System.out.println("updateCheck :" + updateCheck);
						dateAString = sdf.format(dateA);
						dateBString = sdf.format(dateB);
					}
				}
				System.out.println("updateCheck :" + updateCheck);
				Map<String, Object> bpi = (Map<String, Object>) responseData.get("bpi");
				if (updateCheck) {
					if (!bpi.isEmpty()) {
						saveForexData((Map<String, Object>) bpi.get("USD"), formattedDate);
						saveForexData((Map<String, Object>) bpi.get("GBP"), formattedDate);
						saveForexData((Map<String, Object>) bpi.get("EUR"), formattedDate);
						return "SUCCESS";
					} else {
						logger.warn("No data received from API");
					}
				} else {

					return "API 獲取時間" + dateAString + "小於 DB更新時間" + dateBString;
				}
			} else {
				logger.warn("No data received from API");
			}
		} catch (Exception e) {
			logger.error("Error occurred while processing forex data", e);
		}

		return "fail";
	}

	private void saveForexData(Map<String, Object> currencyData, String time) {
		String code = currencyData.get("code").toString();
		String rate = currencyData.get("rate").toString();
		String description = currencyData.get("description").toString();
		Double rateFloat = (Double) currencyData.get("rate_float");

		Forex forex = new Forex();
		forex.setCode(code);
		forex.setRate(rate);
		forex.setDescription(description);
		forex.setRate_float(rateFloat);
		forex.setUpdateTime(time);
		forexRepository.save(forex);
	}

	public Object getApiData() {

		try {
			byte[] response = restTemplate.getForObject(API_URL, byte[].class);

			if (response != null && response.length > 0) {
				String responseString = new String(response);

				logger.info("Received data: {}", responseString);
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> responseData = objectMapper.readValue(responseString,
						new TypeReference<Map<String, Object>>() {
						});
				return responseData;
			} else {
				logger.warn("No data received from API");
				return "No data received from API";
			}
		} catch (Exception e) {
			logger.error("Error occurred while processing forex data", e);
			return "Exception";
		}
	}

	public List<Forex> getForexAll() {
		List<Forex> forex = forexRepository.findAll();
		return forex;
	}

	public Forex getForexOne(String code) {
		Optional<Forex> optforex = forexRepository.findById(code);
		return optforex.isPresent() ? optforex.get() : null;
	}

	@Transactional
	public void deleteForexByCode(String code) {
		forexRepository.deleteByCode(code);
	}

	@Transactional
	public void deleteAll() {
		forexRepository.deleteAll();
	}
}
