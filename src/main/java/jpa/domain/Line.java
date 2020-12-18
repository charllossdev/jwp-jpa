package jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Line {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	// 연관관계의 주인 // 연관관계의 관리자(즉 Line 테이블에서는 Station 관련 컬럼이 생성 되지 않고, 연관관계를 가진 엔티티 클래스의 테이블을 참조한다.
	@OneToMany(mappedBy = "line") // 연관된 엔티티 클래스의 인스턴스 변수 명 // Station 클래스의 line 인스턴스 변수를 가리킨다
	private List<Station> stations = new ArrayList<>();

	public Line() {
	}

	public Line(final String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Station> getStations() {
		return stations;
	}
}

