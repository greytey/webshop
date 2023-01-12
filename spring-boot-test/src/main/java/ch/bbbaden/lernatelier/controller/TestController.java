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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Name("testController")
@Controller
public class TestController {

    @Autowired
    JDBCTemplate jdbcTemplate;

    private User user = null;

    private boolean returnToCheckOut = false;
    private boolean returnToVerification = false;
    private Cart cart = new Cart();
    private Item currentlyOpenedItem = null;
    private  Mail mail = new Mail();

    @GetMapping("/")
    public String home(Model model) {
        String path = "/login";
        List<Item> items = jdbcTemplate.getAllProducts();
        model.addAttribute("items", items);
        model.addAttribute("searchResult", "");
        model.addAttribute("isHidden", true);
        if(user != null){
            path = "/logout";
        }
        model.addAttribute("path", path);
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
            try {
                wasLoginSuccessful = jdbcTemplate.registerNewUser(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user.getRepeatPassword());
                if (wasLoginSuccessful){
                    mail.sendVerificationMail(user);
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
            }
        } else {
            wasLoginSuccessful = jdbcTemplate.login(user.getEmail(), user.getPassword());
            user = jdbcTemplate.getLoggedInUser(user.getEmail(), user.getPassword());
        }
        this.user = user;

        if (returnToCheckOut && wasLoginSuccessful) {
            returnToCheckOut = false;
            return "redirect:/check-out";
        }

        if (returnToVerification && wasLoginSuccessful) {
            returnToVerification = false;
            return "redirect:/verification";
        }

         if (wasLoginSuccessful) {
            return "redirect:/";
        } else{
            return "login";
        }
    }

    @GetMapping("/logout")
    public String showLogout(Model model){
        return "logout";
    }

    @PostMapping("/logout")
    public String logout(Model model){
        user = null;
        return "redirect:/";
    }

    @GetMapping("/product-page")
    public String productPage(@RequestParam(name = "productId", defaultValue = "null") String productId, Model model) {
        if (productId.equals("null")) {
            return "error";
        } else {
            String path = "/login";
            Item item = jdbcTemplate.getItemFromId(Integer.valueOf(productId));
            currentlyOpenedItem = item;
            model.addAttribute("name", item.getName());
            model.addAttribute("description", item.getDescription());
            model.addAttribute("price", item.getPriceAsString());
            model.addAttribute("code", item.getCode());
            model.addAttribute("length", item.getLength());
            model.addAttribute("id", item.getId());
            model.addAttribute("comments",jdbcTemplate.getAllCommentsForAProduct(item.getId()));
            model.addAttribute("comment", new Comment());
            model.addAttribute("numberOfComments", jdbcTemplate.getAllCommentsForAProduct(item.getId()).size());
            if(user != null){
                path = "/logout";
            }
            model.addAttribute("path", path);
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
        String path = "/login";
        List<Item> itemsInCart = cart.getItemsSelected();
        if(user != null){
            path = "/logout";
        }
        model.addAttribute("path", path);
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

     @PostMapping("/product-page")
     public String addComment(@RequestParam(name = "id", defaultValue = "null") int id, @ModelAttribute Comment comment, Model model){
        if(user != null){
            comment.setProduct(id);
            comment.setName(user.getFirstname());
            comment.setDate(new Date().toString());
            jdbcTemplate.addComment(comment);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
     }

    @GetMapping("/check-out")
    public String checkOut(Model model) {
        if (user == null || user.getIsVerified() != 1) {
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
        if (user == null || user.getIsVerified() != 1) {
            return "redirect:/login";
        }
        if (order.isChecked()) {
            //Update Userdata
            user.setCity(order.getCity());
            user.setZip(order.getZip());
            user.setAddress(order.getAddress());
            user.setLastname(order.getLastname());
            user.setFirstname(order.getFirstname());
            jdbcTemplate.updateUser(user);

            //Send Email
            String mailOfReceiver = order.getEmail();
            ArrayList<String> attachments = new ArrayList<>();
            for(Item item : cart.getItemsSelected()){
                attachments.add(item.getName());
            }
            mail.sendProductMail(mailOfReceiver, order, cart, jdbcTemplate.getAttachments(attachments));
            cart = new Cart();
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
            model.addAttribute("searchResult", "Results for \"" + keyword + "\";");
            if (items == null){
                model.addAttribute("searchResult", "No results for \"" + keyword + "\"");
            }
        }
        return "index";
    }

    @GetMapping("/verification")
    public String verification(@RequestParam(name = "verificationCode") String verificationCode, RedirectAttributes redirectAttributes) {
        if (user == null) {
            returnToVerification = true;
            return "redirect:/login";
        }
        if (user.getVerificationCode().equals(verificationCode)){
            jdbcTemplate.verifyUser(user);
            redirectAttributes.addFlashAttribute("success", "Everything went just fine");
            return "index";
        }else{
            redirectAttributes.addFlashAttribute("failure", "The verification-code was not correct");
            return "verify";
        }
    }


    @GetMapping("/error")
    public String error(Model model){
        return "error";
    }
}
