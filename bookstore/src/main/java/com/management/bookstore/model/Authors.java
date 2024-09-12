package com.management.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Authors")
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @SequenceGenerator(name = "author_seq", sequenceName = "author_sequence", allocationSize = 1)
    private Long authorId;
    private String name;
    //private List<String> books;

// here avoid to use @manyToOne annotation -- it create joins in internal query which can affect performance,
// so improve performance here not to use annotation
    // here follow industrial approach
}
