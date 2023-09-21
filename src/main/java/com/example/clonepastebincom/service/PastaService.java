package com.example.clonepastebincom.service;

import com.example.clonepastebincom.dao.PastaRepo;
import com.example.clonepastebincom.dto.PastaDTO;
import com.example.clonepastebincom.enums.Access;
import com.example.clonepastebincom.enums.TimeType;
import com.example.clonepastebincom.exception.ExpireDateException;
import com.example.clonepastebincom.model.Pasta;
import jakarta.transaction.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PastaService {

    private final PastaRepo repo;

    @SneakyThrows
    public URL save(PastaDTO pastaDTO) {
        Pasta save = repo.save(convert(pastaDTO));
        String urlString = "http://localhost:8080/".concat(save.getHash());
        return URI.create(urlString).toURL();
    }

    public Pasta get(String hash) {
        Pasta pastaByHash = repo.findPastaByHash(hash);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = pastaByHash.getExpiration();

        if (expiration.isBefore(now)) {
            throw new ExpireDateException(expirationDate(expiration));
        }
        return pastaByHash;
    }

    private Pasta convert(PastaDTO pastaDTO) {
        Pasta pasta = new Pasta();
        pasta.setText(pastaDTO.getText());
        pasta.setAccess(pastaDTO.getAccess());

        setTimeType(pastaDTO, pasta);

        HashService.goodFastHash(pasta);

        addCacheDeque(pasta);

        Pasta pastaByHash = repo.findPastaByHashAndAccess(pasta.getHash(), pasta.getAccess());

        Pasta save;

        save = Objects.requireNonNullElseGet(pastaByHash, () -> repo.save(pasta));

        return save;
    }

    private void addCacheDeque(Pasta pasta) {
        Access aPublic = Access.PUBLIC;
        int size = CacheDeque.getArrayDeque().size();
        boolean contains = CacheDeque.getArrayDeque().contains(pasta);

        if ((size >= 10 && pasta.getAccess().equals(aPublic)) && !contains) {
            CacheDeque.getArrayDeque().removeLast();
            CacheDeque.getArrayDeque().push(pasta);
        } else if ((size < 10 && pasta.getAccess().equals(aPublic)) && !contains) {
            CacheDeque.getArrayDeque().push(pasta);
        }
    }

    private void setTimeType(PastaDTO pastaDTO, Pasta pasta) {
        if (pastaDTO.getType().getValue() > 0) {
            TimeType type = pastaDTO.getType();
            switchType(pasta, type);
        } else {
            pasta.setExpiration(null);
        }
    }

    @SneakyThrows
    private void switchType(Pasta pasta, TimeType type) {
        switch (type) {
            case MINUTE_10:
                pasta.setExpiration(
                        LocalDateTime.now().plusMinutes(type.getValue())
                );
                break;
            case HOUR_1:
            case HOUR_3:
                pasta.setExpiration(
                        LocalDateTime.now().plusHours(type.getValue())
                );
                break;
            case DAY_1:
                pasta.setExpiration(
                        LocalDateTime.now().plusDays(type.getValue())
                );
                break;
            case WEEK_1:
                pasta.setExpiration(
                        LocalDateTime.now().plusWeeks(type.getValue())
                );
                break;
            case MONTH_1:
                pasta.setExpiration(
                        LocalDateTime.now().plusMonths(type.getValue())
                );
                break;
            default:
                throw new NotSupportedException("Not supported value");
        }
    }

    public List<Pasta> getAll() {
        return repo.findAll();
    }

    private String expirationDate(LocalDateTime date) {
        return "Date " + date + " has expired";
    }

    public Deque<Pasta> getCacheAll() {
        LocalDateTime now = LocalDateTime.now();
        Deque<Pasta> arrayDeque = CacheDeque.getArrayDeque();

        checkCacheForExpirationDate(now, arrayDeque);

        int size = arrayDeque.size();

        requestLatestPastesForCache(now, arrayDeque, size);

        return arrayDeque;
    }

    private void requestLatestPastesForCache(LocalDateTime now, Deque<Pasta> arrayDeque, int size) {
        if (size < 10) {
            List<Pasta> list = repo.findPastasByExpirationAfter(now, PageRequest.of(0, 10));
            arrayDeque.clear();
            list.forEach(arrayDeque::push);
        }
    }

    private void checkCacheForExpirationDate(LocalDateTime now, Deque<Pasta> arrayDeque) {
        arrayDeque.forEach(pasta -> {

            if (pasta.getExpiration().isBefore(now)) {
                arrayDeque.removeFirst();
            }
        });
    }
}
