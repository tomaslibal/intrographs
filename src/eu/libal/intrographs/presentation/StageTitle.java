package eu.libal.intrographs.presentation;

public class StageTitle {
    private String prefix;
    private String title;
    private String suffix;

    public StageTitle(String title) {
        this("", title, "");
    }

    public StageTitle(String prefix, String title) {
        this(prefix, title, "");
    }

    public StageTitle(String prefix, String title, String suffix) {
        this.prefix = prefix;
        this.title = title;
        this.suffix = suffix;
    }

    public String toString() {
        return prefix + " " + title + " " + suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
