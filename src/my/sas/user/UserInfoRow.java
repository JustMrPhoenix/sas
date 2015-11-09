package my.sas.user;

import com.google.common.base.Strings;
import my.sas.message.RussianMessage;
import org.bukkit.ChatColor;

public class UserInfoRow {
    private String description;

    private boolean booleanValue;
    private String stringValue;

    public UserInfoRow(String description, boolean value) {
        this.description = Strings.nullToEmpty(description);
        this.booleanValue = value;
    }

    public UserInfoRow(String description, String value) {
        this.description = Strings.nullToEmpty(description);
        this.stringValue = value;
    }

    public String getInfoString() {
        String result = ChatColor.GOLD + description;

        if (stringValue != null) {
            result += ChatColor.WHITE + stringValue;
        } else {
            if (booleanValue) {
                result += ChatColor.DARK_GREEN + RussianMessage.YES;
            } else {
                result += ChatColor.DARK_RED + RussianMessage.NO;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return this.getInfoString();
    }
}
