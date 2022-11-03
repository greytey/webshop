package ch.bbbaden.lernatelier.controller;

import ch.bbbaden.lernatelier.database.JDBCTemplate;
import ch.bbbaden.lernatelier.simpleClasses.*;
import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.*;
import java.util.List;

@Name("testController")
@Controller
public class TestController {

    @Autowired
    JDBCTemplate jdbcTemplate;

    private User user;
    private Cart cart = new Cart();
    private Item currentlyOpenedItem = null;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> items = jdbcTemplate.getAllProducts();
        model.addAttribute("items", items);
        currentlyOpenedItem = null;
        return "index";
    }

    @GetMapping("/login")
    public String loginPre(Model model) {
        List<Item> items = jdbcTemplate.getAllProducts();
        model.addAttribute("items", items);
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute User user, Model model) {
        this.user = user;
        if (!jdbcTemplate.login(user.getUsername(), user.getRepeatPassword())) {
            jdbcTemplate.registerNewUser(user.getUsername(), user.getPassword(), user.getRepeatPassword());
            System.out.println("Neuer Benutzer registriert");
        }
        return "redirect:/";
    }

    @GetMapping("/product-page")
    public String productPage(@RequestParam(name = "productId", defaultValue = "null") String productId, Model model) {
        if (productId.equals("null")) {
        } else {
            Item item = jdbcTemplate.getItemFromId(Integer.valueOf(productId));
            currentlyOpenedItem = item;
            model.addAttribute("name", item.getName());
            model.addAttribute("description", item.getDescription());
            model.addAttribute("price", item.getPrice());
            model.addAttribute("code", item.getCode());
            model.addAttribute("length", item.getLength());
            model.addAttribute("id", item.getId());
        }

        return "product-page";
    }

    @GetMapping("/addToCart")
    public String addToCart(Model model) {
        if (currentlyOpenedItem == null) {
            //Does not work
            //attributes.addFlashAttribute("Error", "Item could not be added to cart. Leave the page and try it again.");
        } else {
            cart.addProduct(currentlyOpenedItem);
        }
        currentlyOpenedItem = null;
        List<Item> itemsInCart = cart.getItemsSelected();
        model.addAttribute("cart", itemsInCart);
        model.addAttribute("total", cart.getTotal());
        return "cart";
    }

    @GetMapping("/cart")
    public String displayCart(Model model) {
        currentlyOpenedItem = null;
        List<Item> itemsInCart = cart.getItemsSelected();
        model.addAttribute("cart", itemsInCart);
        model.addAttribute("total", cart.getTotal());

        return "cart";
    }

    @GetMapping("/check-out")
    public String checkOut(Model model) {
        currentlyOpenedItem = null;
        List<Item> itemsInCart = cart.getItemsSelected();
        model.addAttribute("order", new Order());
        model.addAttribute("cart", itemsInCart);
        model.addAttribute("total", cart.getTotal());

        return "check-out";
    }

    @PostMapping("/check-out")
    public String ordered(@ModelAttribute Order order, Model model) {
        String mailMessage = "testmail";
        String mailOfReceiver = order.getEmail();
        Mail mail = new Mail();
        mail.sendMail(mailOfReceiver, mailMessage, order, cart);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword") String keyword, Model model) {
        if (keyword != null) {
            List<Item> items = jdbcTemplate.getItemsFromSearch(keyword);
            model.addAttribute("items", items);
        }
        return "index";
    }
}
