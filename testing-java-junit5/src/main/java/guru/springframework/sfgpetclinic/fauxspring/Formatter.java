package guru.springframework.sfgpetclinic.fauxspring;

import java.text.ParseException;
import java.util.Locale;


public interface Formatter<T> {

    String print(T petType, Locale locale);

    T parse(String text, Locale locale) throws ParseException;
}
