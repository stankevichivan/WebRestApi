package com.ivan.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Override
    public String toString() {
        return "File{" +
               "id=" + id +
               ", fileName='" + fileName + '\'' +
               ", filePath='" + filePath + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(fileName, file.fileName) && Objects.equals(filePath, file.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, filePath);
    }
}
