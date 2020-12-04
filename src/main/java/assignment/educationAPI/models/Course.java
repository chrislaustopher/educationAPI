package assignment.educationAPI;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<User> students = new HashSet<>();

	private String name;


	@ManyToOne
    @JoinColumn(name = "creator_id")
	private User creator;

	public Course() {}

	public Course(String name, User creator) {
		this.name = name;
		this.creator = creator;
	}

	public Course(Long id, String name, User creator) {
		this.id = id;
		this.name = name;
		this.creator = creator;
	}

	// getters

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getCreator() {
		return creator;
	}

	public Set<User> getStudents() {
		return students;
	}

	// setters

	public void setName(String name) {
		this.name = name;
	}

}