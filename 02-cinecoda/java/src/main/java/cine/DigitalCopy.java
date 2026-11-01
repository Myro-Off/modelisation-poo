package cine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DigitalCopy extends FilmCopy {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDate keyExpirationDate;

    public DigitalCopy(Movie movie, LocalDate keyExpirationDate) {
        super(movie);
        this.keyExpirationDate = keyExpirationDate;
    }

    public LocalDate getKeyExpirationDate() {
        return keyExpirationDate;
    }

    @Override
    public void checkPlayable(Projector projector, LocalDate screeningDate) throws BusinessRuleException {
        if (!(projector instanceof DigitalProjector)) {
            throw new BusinessRuleException("un fichier numérique ne peut pas être lu par un " + projector.getTypeDescription());
        }
        if (screeningDate.isAfter(keyExpirationDate)) {
            throw new BusinessRuleException(
                    String.format("la clé de lecture de « %s » a expiré le %s",
                            movie.getTitle(),
                            keyExpirationDate.format(DATE_FORMATTER))
            );
        }
    }

    @Override
    public void recordProjection() {
        // Le support numérique ne s'use pas
    }

    @Override
    public String toString() {
        return String.format("%s : fichier numérique, clé valable jusqu'au %s",
                movie,
                keyExpirationDate.format(DATE_FORMATTER));
    }
}
