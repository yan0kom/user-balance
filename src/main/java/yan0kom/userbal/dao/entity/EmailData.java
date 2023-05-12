package yan0kom.userbal.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_data")
@SequenceGenerator(name = "EmailDataIdSeq", sequenceName = "email_data_id_seq", allocationSize = 1)
public class EmailData {
    @Id
    @GeneratedValue(generator = "EmailDataIdSeq")
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(length = 200, nullable = false, unique = true)
    private String email;
}
