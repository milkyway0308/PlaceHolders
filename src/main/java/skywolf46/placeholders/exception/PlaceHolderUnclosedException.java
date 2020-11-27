package skywolf46.placeholders.exception;

public class PlaceHolderUnclosedException extends PlaceHolderException {
    public PlaceHolderUnclosedException(String x, int point) {
        super("ERR: Parameter unclosed on string \"" + x + "\" on position " + point + " || (" + x.substring(0, point) + " > " + x.charAt(point) + " < " + x.substring(point + 1) + ")");
    }
}
