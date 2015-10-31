package my.sas.useless;

import org.bukkit.ChatColor;

/**
 * Created by Mr.Phoenix on 10/28/2015.
 */
public class SasUs {

    private String name = "unknown";
    private Boolean aBoolean;
    private String string;
    private String comment;

    public SasUs( String name ){
        this.name = name;
    }

    public boolean getBoolean() {
        return aBoolean.booleanValue();
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

    public String get( boolean h ){
        String rt;
        if( h ){
            rt = ChatColor.GOLD+name;
            if( aBoolean != null ){
                if( aBoolean ){
                    rt = rt + ChatColor.DARK_GREEN+"да";
                }else{
                    rt = rt + ChatColor.DARK_RED+"нет";
                }
            }
            if( string != null ){
                rt = rt +ChatColor.WHITE+string;
            }
            if( comment != null ){
                rt = rt + ChatColor.DARK_GRAY+" --"+comment;
            }
        }else{
            rt = "\u001B[33m"+name;
            if( aBoolean != null ){
                if( aBoolean ){
                    rt = rt + "\u001B[32mда";
                }else{
                    rt = rt + "\u001B[31mнет";
                }
            }
            if( string != null ){
                rt = rt +"\u001B[0m"+string;
            }
            if( comment != null ){
                rt = rt + "\u001B[36m --"+comment;
            }
        }
        return rt;
    }
}
