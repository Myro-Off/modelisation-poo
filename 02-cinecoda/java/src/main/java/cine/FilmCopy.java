package cine;

import java.time.LocalDate;

public abstract class FilmCopy {
    protected final Movie movie;

    public FilmCopy(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public abstract void checkPlayable(Projector projector, LocalDate screeningDate) throws BusinessRuleException;

    public abstract void recordProjection();
}
