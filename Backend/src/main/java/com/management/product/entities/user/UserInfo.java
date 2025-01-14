package com.management.product.entities.user;

import com.management.product.entities.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo  extends Auditable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String firstname;
        @Column(unique = true)
        private String email;
        private String password;
}
