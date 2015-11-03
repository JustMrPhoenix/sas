package my.sas.user;

import com.google.common.base.Strings;
import my.sas.message.RussianMessage;
import org.bukkit.ChatColor;

// TODO: Move to package my.sas.commands.vanish with SasVanish after SasCommandExecutor refactor
public class UserStatusRow {
    private String description;

    private Boolean booleanValue;
    private String stringValue;

    public UserStatusRow(String description, boolean value) {
        this.description = Strings.nullToEmpty(description);
        this.booleanValue = value;
    }

    public UserStatusRow(String description, String value) {
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
