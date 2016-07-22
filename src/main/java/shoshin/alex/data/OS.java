package shoshin.alex.data;

/**
 * Created by Administrator on 22.07.2016.
 */
public enum OS {
    WINDOWS, OTHER;

    public static OS parse(String os) {
        if (os.toLowerCase().indexOf("win") != -1) {
            return WINDOWS;
        } else {
            return OTHER;
        }
    }
}
