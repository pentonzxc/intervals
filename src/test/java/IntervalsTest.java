import com.innowise.Intervals;
import com.innowise.Main;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class IntervalsTest {


    @ParameterizedTest
    @MethodSource("IntervalsTest#constructionArguments")
    void whenIntervalConstruction_thenReturnNote(String[] args, String expectedNote) {
        Assertions.assertThat(Intervals.intervalConstruction(args)).isEqualTo(expectedNote);
    }

    @ParameterizedTest
    @MethodSource("IntervalsTest#identificationArguments")
    void whenIntervalIdentification(String[] args, String expectedInterval){
        Assertions.assertThat(Intervals.intervalIdentification(args)).isEqualTo(expectedInterval);
    }




    private static Stream<Arguments> constructionArguments() {
        return Stream.of(
                Arguments.of(new String[]{"M2", "C", "asc"}, "D"),
                Arguments.of(new String[]{"P5", "B", "asc"}, "F#"),
                Arguments.of(new String[]{"m2", "Bb", "dsc"}, "A"),
                Arguments.of(new String[]{"M3", "Cb", "dsc"}, "Abb"),
                Arguments.of(new String[]{"m3", "B", "dsc"}, "G#"),
                Arguments.of(new String[]{"m2", "Fb", "asc"}, "Gbb"),
                Arguments.of(new String[]{"M2", "E#", "dsc"}, "D#"),
                Arguments.of(new String[]{"P4", "E", "dsc"}, "B"),
                Arguments.of(new String[]{"m2", "D#", "asc"}, "E"),
                Arguments.of(new String[]{"M7", "G", "asc"}, "F#")
        );
    }

    private static Stream<Arguments> identificationArguments() {
        return Stream.of(
                Arguments.of(new String[]{"C", "D", "asc"}, "M2"),
                Arguments.of(new String[]{"B", "F#", "asc"}, "P5"),
                Arguments.of(new String[]{"Fb", "Gbb", "asc"}, "m2"),
                Arguments.of(new String[]{"G", "F#", "asc"}, "M7"),
                Arguments.of(new String[]{"Bb", "A", "dsc"}, "m2"),
                Arguments.of(new String[]{"Cb", "Abb", "dsc"}, "M3"),
                Arguments.of(new String[]{"G#", "D#", "dsc"}, "P4"),
                Arguments.of(new String[]{"E", "B", "dsc"}, "P4"),
                Arguments.of(new String[]{"E#", "D#", "dsc"}, "M2"),
                Arguments.of(new String[]{"B", "G#", "dsc"}, "m3")
        );
    }


}
