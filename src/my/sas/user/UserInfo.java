package my.sas.user;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private List<UserInfoRow> statusRows = new ArrayList<>();

    public void addRow(UserInfoRow row) {
        statusRows.add(row);
    }

    public List<UserInfoRow> getStatusRows() {
        return statusRows;
    }

    public void send(CommandSender receiver) {
        List<String> messageLines = new ArrayList<>();

        messageLines.add(ChatColor.YELLOW + "=== SAS СТАТУС ИГРОКА ===");
        for (UserInfoRow statusRow : statusRows) {
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
