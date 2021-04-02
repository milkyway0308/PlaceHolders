package skywolf46.placeholders.impl.holders.java.time;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.MessageParameters;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LocalTimePlaceHolder extends AbstractPlaceHolder {
    private SimpleDateFormat sdf;

    public LocalTimePlaceHolder(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    @Override
    public String getName() {
        return "localTime";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        System.out.println(Arrays.asList(parameters));
        return new LocalTimePlaceHolder(new SimpleDateFormat(parameters.length > 1 ? parameters[1] : "yyyy년 MM월 dd일 HH:mm:ss"));
    }

    @Override
    public String asString(MessageParameters storage) {
        return sdf.format(new Date(System.currentTimeMillis()));
    }
}
