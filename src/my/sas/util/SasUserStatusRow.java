package my.sas.util;

import org.bukkit.ChatColor;

public class SasUserStatusRow {
    private String name = "unknown";
    private Boolean aBoolean;
    private String string;
    private String comment;

    public SasUserStatusRow(String name) {
        this.name = name;
    }

    public String get(boolean dummy) {
        return getStatus();
    }

    public String getStatus() {
        String result = ChatColor.GOLD + name;

        if (aBoolean != null) {
            if (aBoolean) {
                result = result + ChatColor.DARK_GREEN + RussianMessage.YES;
            } else {
                result = result + ChatColor.DARK_RED + RussianMessage.NO;
            }
        }
        if (string != null) {
            result = result + ChatColor.WHITE + string;
        }
        if (comment != null) {
            result = result + ChatColor.DARK_GRAY + " --" + comment;
        }

        return result;
    }

    public boolean getBoolean() {
        return aBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
