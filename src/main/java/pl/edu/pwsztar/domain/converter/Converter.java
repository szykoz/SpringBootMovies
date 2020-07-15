package pl.edu.pwsztar.domain.converter;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
