import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileWriter;
import java.io.IOException;

public class FinalsCashRegister {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        System.out.println("=== WELCOME TO OCEAN LUXE CASH REGISTER ===");
        System.out.println();
        System.out.println("Please Sign Up to continue.");
        System.out.println();

        //sign-up
        String username;
        while(true) {
            System.out.println("Create your username.");
            System.out.println("Please be reminded of the following: ");
            System.out.println("1. It must be lowercase.");
            System.out.println("2. It must contain at least one number from 0-9.");
            System.out.println("3. It must contain at least one special characters (_ ,  . , @).");
            System.out.println("4. It must be 15-30 characters long.");
            System.out.println();
            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            if(validateUsername(username)) {
                usernames.add(username);
                break;
            } else {
                System.out.println("Invalid username. Please follow the format. Try again.");
            }
        }

        String password;
        while(true) {
            System.out.println();
            System.out.println("Create your password.");
            System.out.println("Please be reminded of the following:");
            System.out.println("1. It must have lowercase OR uppercase. It can also have both.");
            System.out.println("2. It must contain at least one number from 0-9.");
            System.out.println("3. It must contain at least one special characters: !, @, #, $, %, ^, &, *, (, ), _, +, -, =, [, ], {, }, ;, :, |, ,, ., <, >, ?");
            System.out.println("4. It must be 8 characters long.");
            System.out.println();
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            if(validatePassword(password)) {
                passwords.add(password);
                break;
            } else {
                System.out.println("Invalid password. Please follow the format. Try again.");
            }
        }

        boolean isLoggedIn = false;
        String loggedInUser = "";
        while(!isLoggedIn) {
            System.out.println();
            System.out.println("===== LOGIN =====");
            System.out.print("Username: ");
            String inputUser = scanner.nextLine();
            System.out.print("Password: ");
            String inputPass = scanner.nextLine();
            System.out.println();

            isLoggedIn = logIn(inputUser, inputPass, usernames, passwords);

            if(!isLoggedIn) {
                System.out.println("Incorrect input. Try again.");
            } else {
                System.out.println("Log in successful!");
                loggedInUser = inputUser; // Set the logged-in user
            }
        }

        boolean anotherTransaction = true;

        while (anotherTransaction) {
            System.out.println("\n        Welcome to");
            System.out.println("======= OCEAN LUXE =======");
            System.out.println("A Feast of Ocean Delights\n");

            ArrayList<String> productNames = new ArrayList<>();
            ArrayList<Double> productPrices = new ArrayList<>();
            ArrayList<Integer> productQuantities = new ArrayList<>();

            addProducts(productNames, productPrices, productQuantities);

            processTransaction(scanner, productNames, productPrices, productQuantities, loggedInUser);

            scanner.nextLine();

            char answer = ' ';
            while (true) {
                System.out.print("\nDo you want to perform another transaction? (Y/N): ");
                String input = scanner.nextLine().trim();
                if(!input.isEmpty()){
                    answer = Character.toUpperCase(input.charAt(0));
                    if (answer == 'Y' || answer == 'N') {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    }
                }
            }

            if (answer != 'Y') {
                anotherTransaction = false;
            }  
        }
    }

    public static boolean validateUsername(String username) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[_@.])[a-z0-9_@.]{15,30}$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};:|,.<>?])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:|,.<>?]{8}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean logIn(String inputUser, String inputPass, ArrayList<String> usernames, ArrayList<String> passwords) {
        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(inputUser) && passwords.get(i).equals(inputPass)) {
                return true;
            }
        }
        return false;
    }

    public static void addProducts(ArrayList<String> productNames, ArrayList<Double> productPrices, ArrayList<Integer> productQuantities) {
        productNames.add("Grilled Octopus with Citrus Aioli");
        productPrices.add(1500.0);
        productQuantities.add(0);

        productNames.add("Scallops in Brown Butter");
        productPrices.add(1800.0);
        productQuantities.add(0);

        productNames.add("Tuna Tartare with Avocado Mousse");
        productPrices.add(1600.0);
        productQuantities.add(0);

        productNames.add("Lobster Thermidor");
        productPrices.add(2500.0);
        productQuantities.add(0);

        productNames.add("King Crab Legs with Garlic Butter");
        productPrices.add(2200.0);
        productQuantities.add(0);
    }

    public static void processTransaction(Scanner scanner, ArrayList<String> productNames, ArrayList<Double> productPrices, ArrayList<Integer> productQuantities, String loggedInUser) {
        double totalPrice = 0;
        boolean addingItems = true;
        while (addingItems) {
            displayProducts(productNames, productPrices);
            System.out.println();
            System.out.print("Enter the item number to add to cart: ");
            int productNumber = -1;
            if (scanner.hasNextInt()) {
                productNumber = scanner.nextInt() - 1;
                if (productNumber < 0 || productNumber >= productNames.size()) {
                    System.out.println("Invalid item number. Please try again.");
                    continue;
                }
            } else {
                System.out.println("ERROR! Please enter a valid item number.");
                scanner.next();
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = -1;
            while (true) {
                if (scanner.hasNextInt()) {
                    quantity = scanner.nextInt();
                    if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("Invalid quantity. Please enter a positive number.");
                    }
                } else {
                    System.out.println("ERROR! Please enter a valid quantity.");
                    scanner.next();
                }
            }

            productQuantities.set(productNumber, productQuantities.get(productNumber) + quantity);
            totalPrice += productPrices.get(productNumber) * quantity;

            System.out.print("Do you want to add another product? (Y/N): ");
            char addMore = scanner.next().charAt(0);
            while (addMore != 'Y' && addMore != 'y' && addMore != 'N' && addMore != 'n') {
                System.out.println("Invalid input! Please enter 'Y' or 'N'.");
                System.out.print("Do you want to add another product? (Y/N): ");
                addMore = scanner.next().charAt(0);
            }

            if (addMore == 'N' || addMore == 'n') {
                addingItems = false;
            }
        }

        removeItems(scanner, productNames, productPrices, productQuantities);

        System.out.println();
        System.out.print("Do you still want to add another Product? (Y/N): ");
        char addChoice = scanner.next().charAt(0);
        while (addChoice != 'Y' && addChoice != 'y' && addChoice != 'N' && addChoice != 'n') {
            System.out.println("Invalid input! Please enter 'Y' for yes or 'N' for no.");
            System.out.print("Do you still want to add another Product? (Y/N): ");
            addChoice = scanner.next().charAt(0); 
        }

        if (Character.toUpperCase(addChoice) == 'Y') {
            processTransaction(scanner, productNames, productPrices, productQuantities, loggedInUser);
            return;
        }

        displaySummary(productNames, productPrices, productQuantities, loggedInUser); 
    }

    public static void displayProducts(ArrayList<String> productNames, ArrayList<Double> productPrices) {
        System.out.println("\nExquisite Seafood Delicacies:");
        for (int i = 0; i < productNames.size(); i++) {
            System.out.println((i + 1) + ". " + productNames.get(i) + " - Price: " + productPrices.get(i) + " pesos");
        }
    }

    public static void displaySummary(ArrayList<String> productNames, ArrayList<Double> productPrices, ArrayList<Integer> productQuantities, String loggedInUser) {
        System.out.println("\n=== Order Summary ===");
        boolean hasItems = false;
        double totalPrice = 0;
        String itemsBought = "";

        for (int i = 0; i < productQuantities.size(); i++) {
            if (productQuantities.get(i) > 0) {
                double itemTotal = productPrices.get(i) * productQuantities.get(i);
                String itemLine = productNames.get(i) + " - " + productQuantities.get(i) + " pcs = " + itemTotal + " pesos\n";
                System.out.print(itemLine);
                itemsBought += itemLine;
                totalPrice += itemTotal;
                hasItems = true;
            }
        }

        if (!hasItems) {
            System.out.println("No items are selected.");
        } else {
            System.out.println("Total Price: " + totalPrice + " pesos");
            acceptPayment(new Scanner(System.in), totalPrice);

            Scanner scanner = new Scanner(System.in);
            String datePattern = "\\d{4}-\\d{2}-\\d{2}"; // YYYY-MM-DD
            String timePattern = "\\d{2}:\\d{2}";        // HH:MM 24-hour format
            String date;
            String time;

            // Validation of date input
            while (true) {
                System.out.print("Enter date of transaction (e.g., 2025-05-24): ");
                date = scanner.nextLine();
            if (date.matches(datePattern)) {
                break;
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
            // Validation of time input
            while (true) {
                System.out.print("Enter time of transaction (e.g., 14:35): ");
                time = scanner.nextLine();
            if (time.matches(timePattern)) {
                break;
            } else {
                System.out.println("Invalid time format. Please use HH:MM in 24-hour format.");
            }
        }
            saveTransactionToFile(date, time, loggedInUser, itemsBought, totalPrice);
        }
    }

    public static void acceptPayment(Scanner scanner, double totalPrice) {
        double payment = -1;
        do {
            System.out.print("Enter payment amount: ");
            if (scanner.hasNextDouble()) {
                payment = scanner.nextDouble();
                if (payment < totalPrice) {
                    System.out.println("Insufficient payment. Please enter a valid amount.");
                }
            } else {
                System.out.println("ERROR! Please enter a valid amount.");
                scanner.next();
            }
        } while (payment < totalPrice);
        System.out.println("Change: " + (payment - totalPrice) + " pesos");
    }

    public static void removeItems(Scanner scanner, ArrayList<String> productNames, ArrayList<Double> productPrices, ArrayList<Integer> productQuantities) {
        boolean editingCart = true;
        while (editingCart) {
            if (allItemsRemoved(productQuantities)) {
                System.out.println("No items in the cart.");
                return;
            }

            displayCart(productNames, productQuantities, productPrices);

            char choice;
            while (true) {
                System.out.print("\nDo you want to edit or remove an order? (Y/N): ");
                choice = scanner.next().charAt(0);
                if (choice == 'Y' || choice == 'y' || choice == 'N' || choice == 'n') {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter Y or N only.");
                }
            }
            if (Character.toUpperCase(choice) != 'Y') break;

            System.out.println("\nWHAT DO YOU WANT: ");
            System.out.println("1. Remove whole order");
            System.out.println("2. Update the quantity of order");
            System.out.println("3. Remove quantity of order.");
            System.out.print("Choose a number to proceed: ");

            int option = -1;
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
            } else {
                scanner.next(); 
                System.out.println("Invalid input.");
                continue;
            }

            if (option < 1 || option > 3) {
                System.out.println("Invalid choice.");
                continue;
            }

            displayCart(productNames, productQuantities, productPrices);
            System.out.print("Enter item number: ");
            int index = -1;
            if (scanner.hasNextInt()) {
                index = scanner.nextInt() - 1;
                if (index < 0 || index >= productNames.size() || productQuantities.get(index) == 0) {
                    System.out.println("Invalid item number.");
                    continue;
                }
            } else {
                scanner.next();
                System.out.println("Invalid input.");
                continue;
            }

            if (option == 1) {
                productQuantities.set(index, 0);
                System.out.println(productNames.get(index) + " is successfully removed.");
            } else if (option == 2) {
                System.out.print("Enter number of quantity to ADD: ");
                if (scanner.hasNextInt()) {
                    int addQty = scanner.nextInt();
                    scanner.nextLine();
                    if (addQty > 0) {
                        productQuantities.set(index, productQuantities.get(index) + addQty);
                        System.out.println(productNames.get(index) + " quantity updated.");
                    } else {
                        System.out.println("Invalid quantity.");
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                }
            } else if (option == 3) {
                System.out.print("Enter number of quantity to REMOVE: ");
                if (scanner.hasNextInt()) { 
                    int remQty = scanner.nextInt();
                    scanner.nextLine();
                    if (remQty > 0 && remQty <= productQuantities.get(index)) {
                        productQuantities.set(index, productQuantities.get(index) - remQty);
                        System.out.println(remQty + " quantity removed from " + productNames.get(index) + ".");
                    } else {
                        System.out.println("Invalid quantity.");
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    public static void displayCart(ArrayList<String> productNames, ArrayList<Integer> productQuantities, ArrayList<Double> productPrices) {
        System.out.println("\n=== Your Cart ===");
        for (int i = 0; i < productNames.size(); i++) {
            if (productQuantities.get(i) > 0) {
                double totalItemPrice = productQuantities.get(i) * productPrices.get(i);
                System.out.println((i + 1) + ". " + productNames.get(i) + " - " + productQuantities.get(i) + " pcs - " + totalItemPrice + " pesos");
            }
        }
    }

    public static boolean allItemsRemoved(ArrayList<Integer> productQuantities) {
        for (int quantity : productQuantities) {
            if (quantity > 0) return false;
        }
        return true;
    }

    public static void saveTransactionToFile(String date, String time, String loggedInUser, String items, double total) {
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            writer.write("Date: " + date + "\n");
            writer.write("Time: " + time + "\n");
            writer.write("Cashier: " + loggedInUser + "\n");
            writer.write("Items Purchased:\n" + items);
            writer.write("Total Amount: " + total + " pesos\n");
            writer.write("--------------------------\n");
            writer.close();
            System.out.println("Transaction saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the transaction.");
        }
    }
}