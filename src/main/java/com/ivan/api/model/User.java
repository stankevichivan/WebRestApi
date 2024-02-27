package com.ivan.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String status;
  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDate createdAt;
  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDate updatedAt;
  @OneToMany(cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      mappedBy = "user",
      orphanRemoval = true)
  private List<Event> events = new ArrayList<>();

  public void addEvent(Event event) {
    this.events.add(event);
    event.setUser(this);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", userName='" + name + '\'' +
        ", eventList=" + events +
        '}';
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(name, user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
