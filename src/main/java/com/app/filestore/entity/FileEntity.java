package com.app.filestore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String file;
    private String title;
    private String description;
    private LocalDate creationTime;

    public FileEntity(String file, String title, String description, LocalDate creationTime) {
        this.file = file;
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
    }
}
