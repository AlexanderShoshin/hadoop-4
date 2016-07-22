package shoshin.alex.data;

/**
 * Created by Administrator on 22.07.2016.
 */
public enum OS {
    WINDOWS, MACINTOSH, OTHER;

    public static OS parse(String os) {
        if (os.toLowerCase().indexOf("win") != -1) {
            return WINDOWS;
        } else if (os.toLowerCase().indexOf("mac") != -1) {
            return MACINTOSH;
        } else {
            return OTHER;
        }
    }
}
