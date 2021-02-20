import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CardTradeTest {

    @BeforeEach
    void setUp() {
    }


    @Test
    void problemCreation() {
        var problem = new CardTrade.Problem.Builder(2)
                .addCardType(1, 50, 2)
                .addCardType(50, 20, 1)
                .addCardType(40, 30, 1)
                .build();
        System.out.println(problem);
    }

    @Test
    void problemFromInput() throws IOException {
        var problem = CardTrade.Problem.fromInput(new FileInputStream("src/test/resources/test_problem.txt"));
        assertEquals(4, problem.deckSize);
    }

    @Test
    void profit() {
        var problem = new CardTrade.Problem.Builder(2)
                .addCardType(1, 50, 2)
                .addCardType(50, 20, 1)
                .addCardType(40, 30, 1)
                .build();
        assertEquals(10, new CardTrade(problem).optimalProfit());
    }
}