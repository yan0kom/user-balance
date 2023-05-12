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
@Table(name = "phone_data")
@SequenceGenerator(name = "PhoneDataIdSeq", sequenceName = "phone_data_id_seq", allocationSize = 1)
public class PhoneData {
    @Id
    @GeneratedValue(generator = "PhoneDataIdSeq")
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(length = 200, nullable = false, unique = true)
    private String phone;
}
