package cine;

import java.time.LocalDate;

public class Film35mmCopy extends FilmCopy {
    public static final int MAX_PROJECTIONS = 50;
    private int projectionCount;

    public Film35mmCopy(Movie movie, int initialProjections) {
        super(movie);
        this.projectionCount = initialProjections;
    }

    public Film35mmCopy(Movie movie) {
        this(movie, 0);
    }

    public int getProjectionCount() {
        return projectionCount;
    }

    @Override
    public void checkPlayable(Projector projector, LocalDate screeningDate) throws BusinessRuleException {
        if (!(projector instanceof AnalogProjector)) {
            throw new BusinessRuleException("une copie 35 mm ne peut pas être lue par un " + projector.getTypeDescription());
        }
        if (projectionCount >= MAX_PROJECTIONS) {
            throw new BusinessRuleException(
                    String.format("la copie 35 mm de « %s » est usée (%d projections)",
                            movie.getTitle(),
                            projectionCount)
            );
        }
    }

    @Override
    public void recordProjection() {
        this.projectionCount++;
    }

    @Override
    public String toString() {
        return String.format("%s : copie 35 mm, %d projections", movie, projectionCount);
    }
}
