package cronograma.api.model;

import cronograma.api.dto.CronogramaAtualizarDTO;
import cronograma.api.dto.CronogramaCadastrarDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name = "cronogramas")
@Entity(name = "Cronograma")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cronograma implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique=true)
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Cronograma(CronogramaCadastrarDTO cronogramaCadastrarDTO) {
        this.nome = cronogramaCadastrarDTO.nome();
        this.login = cronogramaCadastrarDTO.login();
        this.senha = new BCryptPasswordEncoder().encode(cronogramaCadastrarDTO.senha());
        this.role = Role.USER;
    }

    public void atualizar(CronogramaAtualizarDTO cronogramaAtualizarDTO) {
        if (cronogramaAtualizarDTO.nome() != null) {
            this.nome = cronogramaAtualizarDTO.nome();
        }
        if (cronogramaAtualizarDTO.login() != null) {
            this.login = cronogramaAtualizarDTO.login();
        }
        if (cronogramaAtualizarDTO.senha() != null) {
            this.senha = cronogramaAtualizarDTO.senha();
        }
    }

    public void bloquar() {
        this.role = Role.BLOQUEADO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (this.role == Role.USER) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_BLOQUEADO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
