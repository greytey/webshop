package ch.bbbaden.lernatelier.simpleClasses;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Cart {

    public List<Item> getItemsSelected() {
        return itemsSelected;
    }

    List<Item> itemsSelected = new ArrayList<>();

    public void addProduct(Item item){
        itemsSelected.add(item);
    }

    public void removeProduct(String productname){
        Item itemToRemove = null;
        for(Item item : itemsSelected){
            if(item.getName().equals(productname)){
                itemToRemove = item;
                break;
            }
        }
        if(itemToRemove != null) {
            itemsSelected.remove(itemToRemove);
        }
    }

    public String getTotal(){
        float total = 0.0f;
        for(Item item : itemsSelected){
            total += item.getPrice();
        }
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(total);
    }

    public String getMailText(Order order){
        String itemText = "";
        for(Item item : itemsSelected){
            itemText += "<tr>\n<td>" + item.getName() + "</td>\n<td style=\"text-align:end;\">CHF " + item.getPriceAsString() + "</td>\n</tr>\n";
        }
        return "<!DOCTYPE html>\n" +
                "<html>\n<head>" +
                "\n<meta charset=\"utf-8\">\n<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css\">" +
                "\n<style>\ncontainer {\n margin: 50px; \n}\n" +
                "table {\n width: 400px;\n border-collapse: collapse; \n}\n" +
                "table, th, td {\n border: 1px solid; \n padding: 5px; \n}\n" +
                "</style>\n</head>\n" +
                "<body>\n<div class=\"container\">" +
                "<p>Hallo " + order.getFirstname() + " " + order.getLastname() + "</p>\n" +
                "<p>Vielen Dank für deine Bestellung!</p>\n" +
                "<p>Hier nochmals eine Übersicht:</p>\n<table>\n" + itemText +
                "<tr>\n<td>Total</td>\n<td style=\"text-align:end;\">CHF " + getTotal() + "</td>\n</tr>\n" +
                "</table>\n<p>Die Rechnung wird an folgende Adresse geschickt:</p>\n" +
                "<p>" + order.getAddress() + ",\n<br>" + order.getZip() + " " + order.getCity() + "\n</p>"+
                "<p>Die Codedatei(en) findest du im Anhang. Viel Spass damit!<br>Wir würden uns freuen," +
                " wenn du uns mal wieder besuchst. Machs gut!</p>\n" +
                "<p>Mit freundlichen Grüssen,<br> dein codelineway-Team</p>\n" +
                "</div>\n</body>\n</html>";
    }

}
