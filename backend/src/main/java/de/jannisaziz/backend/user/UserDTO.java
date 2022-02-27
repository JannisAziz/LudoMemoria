package de.jannisaziz.backend.user;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {

    @Id
    private String id;

    private String username;
    private String email;
}
