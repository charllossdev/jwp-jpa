package jpa.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jpa.dao.LineRepository;
import jpa.dao.StationRepository;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stations;

	@Autowired
	private LineRepository lines;

	@Test
	void saveTest() {

		final Station expected = new Station("잠실역");
		assertThat(expected.getId()).isNull();

		final Station actual = stations.save(expected);
		assertThat(actual.getId()).isNotNull();
		assertThat(actual.getName()).isEqualTo("잠실역");
	}

	@Test
	void findByName() {

		// 비 영속 상태
		final Station expected = new Station("잠실역");

		// 영속 상태로 변환 <-> 준영속 상태(영속성 상태로 관리되다가 분리된 상태)
		// 양속성 컨텐스트는 트랜젝션 범위 내에서 관리되고 실행된다.
		stations.save(expected);

		final Station actual = stations.findByName("잠실역");
		assertThat(actual.getName()).isEqualTo("잠실역");
	}

	@Test
	void identity() {
		final Station station1 = stations.save(new Station("잠실역"));
		final Station station2 = stations.findById(station1.getId()).get();

		// ID를 기반으로 동일성 보장 // ID가 동일하다면, 주소값이 동일하다
		// 영속 컨텍스트가 처리해준다.
		assertThat(station1 == station2).isTrue();

		// 꼭 ID를 기반으로가 아니더라도 동일성 보장 가능
		final Station station3 = stations.findByName("잠실역");

		assertThat(station1 == station3).isTrue();
	}

	@Test
	void update() {

		final Station station1 = stations.save(new Station("잠실역"));
		// 변경 감지 // flush() 엔티티와 스냅샷 비교
		station1.changeName("몽촌토성역"); // save 를 하지 않아도, 변경되는 이름이 반영이 됨
		//station.flush();

		// 영속성 컨텍스트에서 name 으로 엔티티 클래스를 찾을 수가 없다.
		// findById 가 아닌, findByName 은 JPQL 로 영속성컨텍스트가 아닌 바로 데이터베스에 질의를 한다.
		// JPQL 문은 항상 flush 가 일어난다. -> 엔티티 클래스와 영속성컨텍스트 간에 스냅샷을 비교해서 업데이트가 일어나고, 질의가 시작한다.
		final Station station2 = stations.findByName("몽촌토성역");
		assertThat(station2).isNotNull();
	}

	@Test
	@DisplayName("지하철 역이 어느 노선에 속해 있는지 테스트")
	void stationFindLineTest() {

		final Station station1 = new Station("잠실역");
		final Line line = lines.save(new Line("2호선"));
		station1.setLine(line);

		final Station actual = stations.save(station1);

		assertThat(station1.getLine().getName()).isEqualTo("2호선");
	}

	@Test
	void updateWithLine() {
		final Station station1 = new Station("잠실역");
		final Line line = lines.save(new Line("2호선"));
		station1.setLine(line);

		final Station actual = stations.save(station1);
	}

	@Test
	void findByLineId() {

		final Line line = lines.findByName("2호선");

		line.getStations();
	}
}
