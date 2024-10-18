package com.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.entity.Forex;
import com.portfolio.service.ForexService;

@RestController
@RequestMapping("/api/forex")
public class ForexController {

	@Autowired
	private ForexService forexService;

	private static final Logger logger = LoggerFactory.getLogger(ForexController.class);

	/** insert Data */
	@GetMapping("/insertApi")
	public Object insertApi() {
		try {
			if (forexService.insertApi()) {
				return forexService.getForexAll();
			}
			return "insert error";
		} catch (Exception e) {
			logger.error("insert error", e);
			return "insert error";
		}
	}

	/** insert Data By CODE */
	@GetMapping("/insertApiByCode")
	public Object insertApiCode(@Param(value = "code") String code) {
		try {
			if (forexService.insertApi(code)) {
				return forexService.getForexOne(code);
			}
			return "insert error";
		} catch (Exception e) {
			logger.error("insert error", e);
			return "insert error";
		}
	}

	/** update Data */
	@PutMapping("/updateApi")
	public Object getupdate() {
		try {
			if (forexService.updateApi().equalsIgnoreCase("SUCCESS")) {
				return forexService.getForexAll();
			}
			return forexService.updateApi();
		} catch (Exception e) {
			logger.error("insert error", e);
			return "insert error";
		}
	}

	/** getAPI JSON */
	@GetMapping("/getApiData")
	public Object getApiData() {
		try {
			forexService.getApiData();
			System.out.println("success");

			return forexService.getApiData();
		} catch (Exception e) {
			logger.error("Error", e);
		}

		return "error";
	}

	/** DB 資料 */
	@GetMapping("/getForexAll")
	public Object getForex() {
		try {
			forexService.getForexAll();
			System.out.println("success");

			return forexService.getForexAll();
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return "error";
	}

	/** GET By CODE */
	@GetMapping("/getOne/{code}")
	public Forex getOne(@PathVariable String code) {
		return forexService.getForexOne(code);
	}

	/** DELETE */
	@DeleteMapping("/delete/{code}")
	@Transactional
	public Boolean deleteForex(@PathVariable String code) {
		try {
			System.out.println("delete code success");
			forexService.deleteForexByCode(code);

			return true;
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return false;
	}

	/** DELETE */
	@DeleteMapping("/deleteAll")
	@Transactional
	public Boolean deleteAll() {
		try {

			forexService.deleteAll();
			System.out.println("delete ALL success");

			return true;
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return false;
	}
}
