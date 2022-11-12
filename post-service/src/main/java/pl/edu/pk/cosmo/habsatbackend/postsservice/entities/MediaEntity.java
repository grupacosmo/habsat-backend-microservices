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
@Table(name = "media", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "s3_key")
    private String s3Key;

    @Setter
    private String nameOfFile;

    @Setter
    private String typeOfFile;

    @Setter
    private Long sizeOfFile;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
