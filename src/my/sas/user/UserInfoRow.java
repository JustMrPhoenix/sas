package my.sas.user;

import com.google.common.base.Strings;
import my.sas.message.RussianMessage;
import org.bukkit.ChatColor;

public class UserInfoRow {
    private String description;

    private Boolean booleanValue;
    private String stringValue;

    public UserInfoRow(String description, boolean value) {
        this.description = Strings.nullToEmpty(description);
        this.booleanValue = value;
    }

    public UserInfoRow(String description, String value) {
        this.description = Strings.nullToEmpty(description);
        this.stringValue = value;
    }

    public String getStatusString() {
        String result = ChatColor.GOLD + description;

        if (booleanValue != null) {
            if (booleanValue) {
                result = result + ChatColor.DARK_GREEN + RussianMessage.YES;
            } else {
                result = result + ChatColor.DARK_RED + RussianMessage.NO;
            }
        }
        if (stringValue != null) {
            result = result + ChatColor.WHITE + stringValue;
        }

        return result;
    }

    @Override
    public String toString() {
        return this.getStatusString();
    }
}
