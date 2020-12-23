package jpa.domain;

import java.awt.*;
import java.util.Collections;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

class LineTest {

	@Resource
	private StationRepository stationRepository;

	@Resource
	private LineRepository lineRepository;

	private Station station;
	private Line line;

	@BeforeEach
	void init() {
		line = lineRepository.save(new Line(Color.ORANGE, "3호선"));
		station = stationRepository.save(new Station("교대역", Collections.singletonList(line)));
	}

	@Test
	void findByNameTest() {
		final Line line = lineRepository.findByName("3호선");
		//assertThat(line.getStations())
	}
}
