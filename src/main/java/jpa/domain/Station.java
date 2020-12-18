package jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// 해당 클래스가 엔티티 클래스라고 명시(테이블과 맵핑되는 클래스)
@Entity
@Table(name = "station")
public class Station {

	@Id // 엔티티 클래스틑 반드시 PK를 가져야 한다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 생성 전략 // GenerationType.IDENTITY => AutoIncrement
	private Long id; // id가 PK를 명시

	@Column(name = "station_name", nullable = false) // NotNull // 테이블에선 NotNull이지만, String 객체에는 Null이 될수 있어 개발할때 주의
	private String name;

	//  엔티티 클래스와 엔티티 클래스 간에 관계를 명시해주어야 한다.
	@ManyToOne // Station(Many) to Line(One)
	@JoinColumn(name = "line_id") // 자동으로 만들어 질땐, 엔티티명_PK명
	private Line line;

	// 생성자를 추가한다면, 매개변수가 없는 생성자를 반드시 생성해야한다.
	private Station() { // 기본 생성자가 private 으로 설정하면 에러

	}

	public Station(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Station(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Line getLine() {
		return line;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void setLine(Line line) {
		this.line = line;
		line.getStations().add(this); // 재귀 함수를 조심해야 한다.
	}

	// 다대일 단방향 일대다 단방향, 일대일 단방향, 다대다 단방향
}
