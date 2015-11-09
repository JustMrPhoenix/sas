package my.sas.user;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private List<UserInfoRow> infoRows = new ArrayList<>();

    public void addRow(UserInfoRow row) {
        infoRows.add(row);
    }

    public void send(CommandSender receiver) {
        List<String> messageLines = new ArrayList<>();

        messageLines.add(ChatColor.YELLOW + "=== SAS СТАТУС ИГРОКА ===");
        for (UserInfoRow infoRow : infoRows) {
            messageLines.add(infoRow.getInfoString());
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
