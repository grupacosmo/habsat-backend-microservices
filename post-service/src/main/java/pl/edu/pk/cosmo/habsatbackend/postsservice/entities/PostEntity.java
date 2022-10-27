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
@Table(name = "posts", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "thumbnail_id")
    private Long thumbnailId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @Column(unique = true)
    private String slug;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String emailOfAuthor;

    @Getter
    @Setter
    private Date publishedAt;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Getter
    @ManyToOne
    @JoinColumn(name="thumbnail_id", insertable = false, updatable = false)
    private MediaEntity thumbnail;

}
