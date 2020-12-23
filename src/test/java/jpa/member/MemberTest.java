package jpa.member;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.domain.Subway;
import jpa.favorite.Favorite;
import jpa.favorite.FavoriteRepository;
import jpa.line.Line;
import jpa.line.LineRepository;
import jpa.position.Position;
import jpa.position.PositionRepository;
import jpa.station.Station;
import jpa.station.StationRepository;

@DataJpaTest
class MemberTest {

	@Autowired
	private StationRepository stationRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private MemberRepository memberRepository;

	private Station 주안;
	private Station 시청;
	private Station 강남;

	private Line lineNumber1;
	private Line lineNumber2;

	@BeforeEach
	void init() {
		주안 = stationRepository.save(new Station("주안"));
		시청 = stationRepository.save(new Station("시청"));
		강남 = stationRepository.save(new Station("강남"));

		lineNumber1 = lineRepository.save(new Line("1호선", Color.BLUE));
		lineNumber2 = lineRepository.save(new Line("2호선", Color.GREEN));

		Subway.createSubway(주안, lineNumber1, positionRepository.save(new Position(10)));
		Subway.createSubway(시청, lineNumber1, positionRepository.save(new Position(67)));
		Subway.createSubway(시청, lineNumber2, positionRepository.save(new Position(40)));
		Subway.createSubway(강남, lineNumber2, positionRepository.save(new Position(77)));
	}

	@Test
	@DisplayName("사용자는 여러 즐겨찾기를 가질 수 있다.")
	void createObjectTest() {

		Favorite favorite1 = favoriteRepository.save(new Favorite(주안, 시청));
		Favorite favorite2 = favoriteRepository.save(new Favorite(시청, 강남));
		Favorite favorite3 = favoriteRepository.save(new Favorite(주안, 강남));

		Member user = memberRepository.save(new Member());
		user.addFavorite(favorite1);
		user.addFavorite(favorite2);
		user.addFavorite(favorite3);

		Member actual = memberRepository.findById(1L).get();

		assertThat(actual.getFavorites()).hasSize(3);
	}
}
