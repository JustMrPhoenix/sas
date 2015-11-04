package my.sas.user;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserStatus {
    private List<UserStatusRow> statusRows = new ArrayList<>();

    public void addRow(UserStatusRow row) {
        statusRows.add(row);
    }

    public List<UserStatusRow> getStatusRows() {
        return statusRows;
    }

    public void send(CommandSender receiver) {
        List<String> messageLines = new ArrayList<>();

        messageLines.add(ChatColor.YELLOW + "=== SAS СТАТУС ИГРОКА ===");
        for (UserStatusRow statusRow : statusRows) {
            messageLines.add(statusRow.getStatusString());
        }

        if (receiver instanceof Player) {
            receiver.sendMessage(StringUtils.join(messageLines, "\n"));
        } else {
            for (String messageLine : messageLines) {
                receiver.sendMessage(messageLine);
            }
        }
    }
}
