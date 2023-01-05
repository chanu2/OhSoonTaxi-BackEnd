package ohsoontac.serverapi.domain.user.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontac.serverapi.domain.common.Sex;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EnableJpaAuditing
@SpringBootApplication
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long id;

    private String uid;
    private String password;

    private String name;
    private String schoolNum;
    private String phoneNum;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @Builder
    public User(String uid, String password, String name, String schoolNum, String phoneNum, Sex sex, List<String> roles) {
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.schoolNum = schoolNum;
        this.phoneNum = phoneNum;
        this.sex = sex;
        this.roles=roles;
    }
}
