package chap09.card;

import okhttp3.*;

import java.io.IOException;

/**
 * 외부 카드사 API를 이용해서 카드번호가 유효한지 확인한다.
 */
public class CardNumberValidator {

    private String server;
    private OkHttpClient client = new OkHttpClient();

    public CardNumberValidator(String server) {
        this.server = server;
    }

    public CardValidity validate(String cardNumber) {
        FormBody formBody = new FormBody.Builder()
                .add("cardNumber", cardNumber)
                .build();

        Request request = new Request.Builder()
                .url(server + "/card")
                .header("Content-Type", "text/plain")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        String result = null;

        try {
            Response response = call.execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return CardValidity.ERROR;
        }

        switch (result) {
            case "ok": return CardValidity.VALID;
            case "bad": return CardValidity.INVALID;
            case "expired": return CardValidity.EXPIRED;
            case "theft": return CardValidity.THEFT;
            default: return CardValidity.UNKNOWN;
        }
    }
}
