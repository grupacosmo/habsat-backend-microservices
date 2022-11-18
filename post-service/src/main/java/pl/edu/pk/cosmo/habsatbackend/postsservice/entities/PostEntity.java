package pl.edu.pk.cosmo.habsatbackend.postsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Accessors(chain = true)
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "thumbnail_id")
    private Long thumbnailId;

    @Setter
    private String title;

    @Setter
    @Column(unique = true)
    private String slug;

    @Setter
    private String content;

    @Setter
    private String emailOfAuthor;

    @Setter
    private Date publishedAt;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="thumbnail_id", insertable = false, updatable = false)
    private MediaEntity thumbnail;

}
