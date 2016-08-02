package shoshin.alex.data;

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
