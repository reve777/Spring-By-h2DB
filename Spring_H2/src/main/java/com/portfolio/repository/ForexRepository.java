package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.entity.Forex;

@Repository
public interface ForexRepository extends JpaRepository<Forex, String> {

	void deleteByCode(String code);

}
