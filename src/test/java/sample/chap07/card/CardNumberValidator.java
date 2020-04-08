package sample.chap07.card;

public class CardNumberValidator {
    public CardValidity validate(String cardNumber) {
        // ... call an external service ...
        if ("12341234".equals(cardNumber)) {
            return CardValidity.VALID;
        }

        if ("56785678".equals(cardNumber)) {
            return CardValidity.THEFT;
        }

        return CardValidity.INVALID;
    }
}
