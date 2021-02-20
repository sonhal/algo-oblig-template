
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CardTrade {

    private final Problem problem;


    CardTrade(Problem problem) {
        this.problem = problem;
    }

    public int optimalProfit() {
        // TODO implement the hard shit
        throw new RuntimeException();
    }

    static class Problem {
        static class Card implements Comparable<Card>{
            int value;
            int sellPrice;
            int buyPrice;

            Card(int value, int buyPrice, int sellPrice) {
                this.value = value;
                this.sellPrice = sellPrice;
                this.buyPrice = buyPrice;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Card card = (Card) o;
                return value == card.value && sellPrice == card.sellPrice && buyPrice == card.buyPrice;
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, sellPrice, buyPrice);
            }

            @Override
            public int compareTo(Card o) {
                if(this.value > o.value) return 1;
                else if(this.value < o.value) return -1;
                return 0;
            }
        }

        static class Builder {

            private final List<Card> deck = new ArrayList<>();
            private int typeCounter = 1;
            private final int comboGoal;
            Builder(int comboGoal) {
                this.comboGoal = comboGoal;
            }

            Builder addCardType(int buyPrice, int sellPrice, int num) {
                for(;num > 0; num--){
                    deck.add(new Card(typeCounter, buyPrice, sellPrice));
                }
                typeCounter++;
                return this;
            }

            Problem build() {
                return new Problem(typeCounter, deck.stream().sorted().collect(toList()), comboGoal);
            }

        }

        int numCardTypes;
        List<Card> deck;
        int deckSize;
        int comboGoal;

        Problem(int numCardTypes, List<Card> deck, int comboGoal) {
            this.numCardTypes = numCardTypes;
            this.deck = deck;
            this.comboGoal = comboGoal;
            this.deckSize = deck.size();
        }

        static Problem fromInput(InputStream inputStream) throws IOException {
            var reader = new BufferedReader(new InputStreamReader(inputStream));

            String topLine = reader.readLine();
            int N = Integer.parseInt(topLine.substring(0, 1));
            int T = Integer.parseInt(topLine.substring(2, 3));
            int K = Integer.parseInt(topLine.substring(4, 5));


            String cardLine = reader.readLine();
            List<Integer> cardLineIntegers = Arrays.stream(cardLine.split("\\s+"))
                    .map(Integer::valueOf).collect(toList());
            var cardTypeNum = new HashMap<Integer, Integer>();

            for(Integer cardI : cardLineIntegers.stream().distinct().collect(Collectors.toList())) {
                cardTypeNum.put(cardI, (int) cardLineIntegers.stream().filter( (other) -> other.equals(cardI)).count());
            }

            var cards = new ArrayList<Card>();
            for(Map.Entry<Integer, Integer> cardEntry : cardTypeNum.entrySet()) {
                List<Integer> cardPrices = Arrays.stream(reader.readLine().split("\\s+")).map(Integer::parseInt).collect(toList());
                for(int i = cardEntry.getValue(); i > 0; i--) {
                    cards.add(new Card(cardEntry.getKey(), cardPrices.get(0), cardPrices.get(1)));
                }
            }
            return new Problem(T, cards, K);
        }

        @Override
        public String toString() {
                var builder = new StringBuilder(String.format("N=%d T=%d K=%d", deckSize, numCardTypes, comboGoal)).append("\n");
                for (Card card : deck) {
                    builder.append(card.value).append(" ");
                }
                builder.append("\n");
                for (Card card : deck.stream().distinct().collect(toList())) {
                builder.append(card.buyPrice).append(" ").append(card.sellPrice).append("\n");
                }
                return builder.toString();
        }

    }
}
