package com.ironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by illladell on 6/22/16.
 */
@Controller
public class PurchaseTrackerController {

    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @PostConstruct
    public void init() throws FileNotFoundException {
       if (customers.count() == 0) {
           File f = new File("customers.csv");
           Scanner scanner = new Scanner(f);
           scanner.nextLine();
           while (scanner.hasNext()) {
               String line = scanner.nextLine();
               String[] columns = line.split(",");
               Customer customer = new Customer(Integer.valueOf(columns[0]), columns[1], columns[2]);
               customers.save(customer);
           }
       }
       if (purchases.count() == 0) {
           File f = new File("purchases.csv");
           Scanner scanner = new Scanner(f);
           scanner.nextLine();
           while (scanner.hasNext()) {
               String line = scanner.nextLine();
               String[] columns = line.split(",");
               Purchase purchase = new Purchase(Integer.valueOf(columns[0]), columns[1], columns[2], Integer.valueOf(columns[3]), columns[4], customers.findOne(Integer.valueOf(columns[0])));
               purchases.save(purchase);
           }
       }
    }


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {

        model.addAttribute("purchases", purchases.findAll());

        return "home";
    }
}
