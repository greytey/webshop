package ch.bbbaden.lernatelier.controller;

import ch.bbbaden.lernatelier.database.JDBCTemplate;
import ch.bbbaden.lernatelier.simpleClasses.*;
import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Name("testController")
@Controller
public class TestController {

    @Autowired
    JDBCTemplate jdbcTemplate;

    private User user = null;

    private boolean returnToCheckOut = false;
    private Cart cart = new Cart();
    private Item currentlyOpenedItem = null;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> items = jdbcTemplate.getAllProducts();
        model.addAttribute("items", items);
        model.addAttribute("searchResult", "");
        model.addAttribute("isHidden", true);
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
        boolean wasLoginSuccessful = false;
        if (user.getPassword().equals(user.getRepeatPassword())) {
            wasLoginSuccessful = jdbcTemplate.registerNewUser(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user.getRepeatPassword());
        } else {
            wasLoginSuccessful = jdbcTemplate.login(user.getEmail(), user.getPassword());
            user = jdbcTemplate.getLoggedInUser(user.getEmail(), user.getPassword());
        }
        this.user = user;

        if (returnToCheckOut && wasLoginSuccessful) {
            return "redirect:/check-out";
        } else if (!returnToCheckOut && wasLoginSuccessful) {
            return "redirect:/";
        } else{
            return "login";
        }
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
            model.addAttribute("comments",jdbcTemplate.getAllCommentsForAProduct(item.getId()));
            model.addAttribute("numberOfComments", jdbcTemplate.getAllCommentsForAProduct(item.getId()).size());
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

    @GetMapping("/remove")
    public String removeFromCart(@RequestParam(name = "productName", defaultValue = "null") String productName, Model model) {
        cart.removeProduct(productName);
        currentlyOpenedItem = null;
        List<Item> itemsInCart = cart.getItemsSelected();
        model.addAttribute("cart", itemsInCart);
        model.addAttribute("total", cart.getTotal());
        return "cart";
    }

    @GetMapping("/check-out")
    public String checkOut(Model model) {
        if (user == null) {
            returnToCheckOut = true;
            return "redirect:/login";
        }
        currentlyOpenedItem = null;
        List<Item> itemsInCart = cart.getItemsSelected();
        model.addAttribute("order", new Order(user));
        model.addAttribute("cart", itemsInCart);
        model.addAttribute("total", cart.getTotal());

        return "check-out";
    }

    @PostMapping("/check-out")
    public String ordered(@ModelAttribute Order order, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        if (order.isChecked()) {
            //Update Userdata
            user.setCity(order.getCity());
            user.setZip(order.getZip());
            user.setAddress(order.getAddress());
            user.setLastname(order.getLastname());
            user.setFirstname(order.getFirstname());
            user.setEmail(order.getEmail());
            user.updateDatabase();

            //Send Email
            String mailOfReceiver = order.getEmail();
            Mail mail = new Mail();
            mail.sendMail(mailOfReceiver, order, cart);
            return "redirect:/";
        } else {
            return "check-out";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword") String keyword, Model model) {
        if (keyword != null) {
            List<Item> items = jdbcTemplate.getItemsFromSearch(keyword);
            model.addAttribute("items", items);
            model.addAttribute("isHidden", false);
            model.addAttribute("searchResult", "Results for &quot;" + keyword + "&quot;");
            if (items.size() == 0){
                model.addAttribute("searchResult", "No results for &quot;" + keyword + "&quot;");
            }
        }
        return "index";
    }
}
