package com.example.clonepastebincom.dto;

import com.example.clonepastebincom.enums.Access;
import com.example.clonepastebincom.enums.TimeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class PastaDTO {

    private String text;
    private Access access;
    private TimeType type;

    @Override
    public String toString() {
        return "Text='" + text +
                ", access=" + access +
                ", type=" + type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastaDTO pastaDTO = (PastaDTO) o;
        return Objects.equals(text, pastaDTO.text) && access == pastaDTO.access && type == pastaDTO.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, access, type);
    }
}
