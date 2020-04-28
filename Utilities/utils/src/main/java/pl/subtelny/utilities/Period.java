package pl.subtelny.utilities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Period implements Serializable {

    private final LocalDateTime start;

    private final LocalDateTime end;

    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static Period of(LocalDateTime start) {
        return new Period(start, start);
    }

    public static Period of(LocalDateTime start, LocalDateTime end) {
        return new Period(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(start, period.start) &&
                Objects.equals(end, period.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
