package jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jpa.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> { // 엔티티 클래스, PK의 타입

	Station findByName(String name);
}
