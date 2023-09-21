package com.example.clonepastebincom.model;

import com.example.clonepastebincom.enums.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 255, message = "The message should not be more than 255 characters")
    private String text;
    private LocalDateTime expiration;
    @NotNull
    private Access access;
    private String hash;

    @Override
    public String toString() {
        return "Id=" + id +
                ", text=" + text +
                ", expiration=" + expiration +
                ", access=" + access +
                ", hash=" + hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasta p = (Pasta) o;
        return this.text.equals(p.getText()) && this.hash.equalsIgnoreCase(p.getHash())
                && this.access.name().equals(p.getAccess().name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, access, hash);
    }
}
