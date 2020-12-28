package skywolf46.placeholders.impl.holders.java.korean;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.MessageParameters;
import skywolf46.placeholders.util.PostWordDetector;

public class PostWordPlaceHolder extends AbstractPlaceHolder {
    private String targetWord = null;
    private String[] values = new String[3];

    @Override
    public String getName() {
        return "postword";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        PostWordPlaceHolder holder = new PostWordPlaceHolder();
        switch (parameters.length) {
            case 4: {
                holder.targetWord = parameters[1];
                holder.values[0] = holder.values[2] = parameters[2];
                holder.values[1] = parameters[3];
            }
            break;
            case 5: {
                holder.targetWord = parameters[1];
                holder.values[0] = parameters[2];
                holder.values[1] = parameters[3];
                holder.values[2] = parameters[4];
            }
            break;
        }
        return holder;
    }

    @Override
    public String asString(MessageParameters storage) {
        if (targetWord == null)
            return "<postword;noTargetError>";
        try {
            String sx = storage.get(targetWord);
            return PostWordDetector.getPostWord(sx, values[0], values[1], values[2]);
        } catch (Exception ex) {
            return "<postword;typeError>";
        }
    }
}
