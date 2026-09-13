package com.example.java_coursework_malabe_tuktuk.controller;

import com.example.java_coursework_malabe_tuktuk.io.InventoryFileHandler;
import com.example.java_coursework_malabe_tuktuk.logic.InventoryManager;
import com.example.java_coursework_malabe_tuktuk.logic.LowStockMonitor;
import com.example.java_coursework_malabe_tuktuk.logic.PartSorter;
import com.example.java_coursework_malabe_tuktuk.logic.SearchFilter;
import com.example.java_coursework_malabe_tuktuk.model.Part;
import com.example.java_coursework_malabe_tuktuk.io.DealerFileHandler;
import com.example.java_coursework_malabe_tuktuk.logic.DealerSelector;
import com.example.java_coursework_malabe_tuktuk.model.Dealer;
import com.example.java_coursework_malabe_tuktuk.logic.CheckoutService;
import com.example.java_coursework_malabe_tuktuk.model.CartItem;
import com.example.java_coursework_malabe_tuktuk.model.Transaction;
import com.example.java_coursework_malabe_tuktuk.io.AuditLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, String> codeCol;
    @FXML private TableColumn<Part, String> nameCol;
    @FXML private TableColumn<Part, String> brandCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private TableColumn<Part, Integer> qtyCol;
    @FXML private TableColumn<Part, String> categoryCol;
    @FXML private TableColumn<Part, String> dateCol;
    @FXML private TableColumn<Part, Integer> thresholdCol;
    @FXML private TableColumn<Part, String> imageCol;

    @FXML private Label totalPartsLabel;
    @FXML private Label totalValueLabel;
    @FXML private Label lowStockLabel;

    @FXML private TextField keywordField;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private TextField minPriceField;
    @FXML private TextField maxPriceField;

    @FXML private TextField codeInput;
    @FXML private TextField nameInput;
    @FXML private TextField brandInput;
    @FXML private TextField categoryInput;
    @FXML private TextField priceInput;
    @FXML private TextField qtyInput;
    @FXML private TextField thresholdInput;

    @FXML private TableView<Dealer> dealerTable;
    @FXML private TableColumn<Dealer, String> dealerIdCol;
    @FXML private TableColumn<Dealer, String> dealerNameCol;
    @FXML private TableColumn<Dealer, String> dealerLocationCol;
    @FXML private TableColumn<Dealer, String> dealerContactCol;

    private DealerFileHandler dealerFileHandler = new DealerFileHandler();

    @FXML private TableView<Part> posPartTable;
    @FXML private TableColumn<Part, String> posCodeCol;
    @FXML private TableColumn<Part, String> posNameCol;
    @FXML private TableColumn<Part, String> posCategoryCol;
    @FXML private TableColumn<Part, Double> posPriceCol;
    @FXML private TableColumn<Part, Integer> posStockCol;

    @FXML private TextField cartQtyField;

    @FXML private TableView<CartItem> cartTable;
    @FXML private TableColumn<CartItem, String> cartCodeCol;
    @FXML private TableColumn<CartItem, String> cartNameCol;
    @FXML private TableColumn<CartItem, Integer> cartQtyCol;
    @FXML private TableColumn<CartItem, Double> cartLineTotalCol;

    @FXML private Label cartSubtotalLabel;
    @FXML private Label cartDiscountLabel;
    @FXML private Label cartTotalLabel;

    private ObservableList<CartItem> cartList = FXCollections.observableArrayList();

    private AuditLogger auditLogger = new AuditLogger();

    private InventoryFileHandler fileHandler = new InventoryFileHandler();
    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private List<Part> allParts = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeCol.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("addDate"));
        thresholdCol.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        imageCol.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        imageCol.setCellFactory(column -> new TableCell<Part, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(getClass().getResourceAsStream("/images/" + imagePath));
                        imageView.setImage(image);
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        dealerIdCol.setCellValueFactory(new PropertyValueFactory<>("dealerId"));
        dealerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dealerLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        dealerContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        loadDealers();
        loadData();

        posCodeCol.setCellValueFactory(new PropertyValueFactory<>("partCode"));
        posNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        posCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        posPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        posStockCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        posPartTable.setItems(partList);

        cartCodeCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPart().getPartCode()));
        cartNameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPart().getName()));
        cartQtyCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        cartLineTotalCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLineTotal()).asObject());

        cartTable.setItems(cartList);
    }

    private void loadData() {
        List<Part> parts = fileHandler.loadInventory("src/main/resources/data/inventory.txt");

        PartSorter sorter = new PartSorter();
        sorter.sortByCategoryThenCode(parts);

        allParts = parts;
        partList.setAll(parts);
        partTable.setItems(partList);

        populateCategoryDropdown();
        updateSummary();
        updateLowStockWarning();
    }

    private void populateCategoryDropdown() {
        categoryFilter.getItems().clear();

        for (Part p : allParts) {
            String cat = p.getCategory();
            if (!categoryFilter.getItems().contains(cat)) {
                categoryFilter.getItems().add(cat);
            }
        }
    }

    private void updateSummary() {
        int count = partList.size();
        double totalValue = 0;
        for (Part p : partList) {
            totalValue += p.getPrice() * p.getQuantity();
        }
        totalPartsLabel.setText("Total Parts: " + count);
        totalValueLabel.setText(String.format("Total Value: Rs. %.2f", totalValue));
    }

    private void updateLowStockWarning() {
        LowStockMonitor monitor = new LowStockMonitor();
        List<Part> lowStockItems = monitor.findLowStockItems(partList);

        if (lowStockItems.isEmpty()) {
            lowStockLabel.setText("No low stock items.");
        } else {
            StringBuilder sb = new StringBuilder("LOW STOCK: ");
            for (Part p : lowStockItems) {
                sb.append(p.getPartCode()).append(" (").append(p.getQuantity()).append("), ");
            }
            lowStockLabel.setText(sb.toString());
        }
    }

    @FXML
    private void onSearch() {
        String keyword = keywordField.getText();
        String category = categoryFilter.getValue() != null ? categoryFilter.getValue() : null;

        Double minPrice = null;
        Double maxPrice = null;
        try {
            if (!minPriceField.getText().trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceField.getText().trim());
            }
            if (!maxPriceField.getText().trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceField.getText().trim());
            }
        } catch (NumberFormatException e) {
            lowStockLabel.setText("Invalid price entered. Please enter numbers only.");
            return;
        }

        SearchFilter filter = new SearchFilter();
        List<Part> results = filter.search(allParts, keyword, category, minPrice, maxPrice);

        partList.setAll(results);
        updateSummary();
    }

    @FXML
    private void onReset() {
        keywordField.clear();
        categoryFilter.setValue(null);
        minPriceField.clear();
        maxPriceField.clear();

        partList.setAll(allParts);
        updateSummary();
    }

    @FXML
    private void onAddPart() {
        try {
            String code = codeInput.getText().trim();
            String name = nameInput.getText().trim();
            String brand = brandInput.getText().trim();
            String category = categoryInput.getText().trim();
            double price = Double.parseDouble(priceInput.getText().trim());
            int qty = Integer.parseInt(qtyInput.getText().trim());
            int threshold = Integer.parseInt(thresholdInput.getText().trim());

            Part newPart = new Part(code, name, brand, category, price, qty, LocalDate.now(), threshold, "");

            InventoryManager manager = new InventoryManager();
            manager.addPart(allParts, newPart);
            auditLogger.log("ADD_PART", code, qty);

            refreshTableAndClearInputs();
            showAlert("Success", "Part added successfully.");

        } catch (NumberFormatException e) {
            showAlert("Error", "Price, Quantity, and Threshold must be valid numbers.");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onDeletePart() {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a part to delete.");
            return;
        }

        try {
            InventoryManager manager = new InventoryManager();
            manager.deletePart(allParts, selected.getPartCode());
            auditLogger.log("DELETE_PART", selected.getPartCode(), selected.getQuantity());
            refreshTableAndClearInputs();
            showAlert("Success", "Part deleted successfully.");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void onEditPart() {
        Part selected = partTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a part to edit.");
            return;
        }

        try {
            String code = selected.getPartCode(); // keep same code, edit the rest
            String name = nameInput.getText().trim().isEmpty() ? selected.getName() : nameInput.getText().trim();
            String brand = brandInput.getText().trim().isEmpty() ? selected.getBrand() : brandInput.getText().trim();
            String category = categoryInput.getText().trim().isEmpty() ? selected.getCategory() : categoryInput.getText().trim();
            double price = priceInput.getText().trim().isEmpty() ? selected.getPrice() : Double.parseDouble(priceInput.getText().trim());
            int qty = qtyInput.getText().trim().isEmpty() ? selected.getQuantity() : Integer.parseInt(qtyInput.getText().trim());
            int threshold = thresholdInput.getText().trim().isEmpty() ? selected.getThreshold() : Integer.parseInt(thresholdInput.getText().trim());

            Part updatedPart = new Part(code, name, brand, category, price, qty, selected.getAddDate(), threshold, selected.getImagePath());

            InventoryManager manager = new InventoryManager();
            manager.updatePart(allParts, updatedPart);

            refreshTableAndClearInputs();
            showAlert("Success", "Part updated successfully.");

        } catch (NumberFormatException e) {
            showAlert("Error", "Price, Quantity, and Threshold must be valid numbers.");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void refreshTableAndClearInputs() {
        PartSorter sorter = new PartSorter();
        sorter.sortByCategoryThenCode(allParts);

        partList.setAll(allParts);
        updateSummary();
        updateLowStockWarning();

        codeInput.clear();
        nameInput.clear();
        brandInput.clear();
        categoryInput.clear();
        priceInput.clear();
        qtyInput.clear();
        thresholdInput.clear();
    }

    private void saveInventoryToFile() {
        fileHandler.saveInventory("src/main/resources/data/inventory.txt", allParts);
    }

    private void loadDealers() {
        List<Dealer> allDealers = dealerFileHandler.loadDealers("src/main/resources/data/dealers.txt");

        DealerSelector selector = new DealerSelector();
        List<Dealer> chosen = selector.selectRandomDealers(allDealers, 4);

        dealerTable.setItems(FXCollections.observableArrayList(chosen));
    }

    @FXML
    private void onRefreshDealers() {
        loadDealers();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onAddToCart() {
        Part selected = posPartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a part first.");
            return;
        }

        try {
            int qty = Integer.parseInt(cartQtyField.getText().trim());

            if (qty <= 0) {
                showAlert("Error", "Quantity must be greater than zero.");
                return;
            }
            if (qty > selected.getQuantity()) {
                showAlert("Error", "Not enough stock. Available: " + selected.getQuantity());
                return;
            }

            boolean found = false;
            for (CartItem item : cartList) {
                if (item.getPart().getPartCode().equalsIgnoreCase(selected.getPartCode())) {
                    item.setQuantity(item.getQuantity() + qty);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cartList.add(new CartItem(selected, qty));
            }

            cartTable.refresh();
            updateCartTotals();
            cartQtyField.clear();

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid quantity.");
        }
    }

    @FXML
    private void onRemoveFromCart() {
        CartItem selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a cart item to remove.");
            return;
        }
        cartList.remove(selected);
        updateCartTotals();
    }

    @FXML
    private void onClearCart() {
        cartList.clear();
        updateCartTotals();
    }

    @FXML
    private void onCheckout() {
        try {
            CheckoutService checkoutService = new CheckoutService();
            Transaction transaction = checkoutService.checkout(cartList);

            for (CartItem item : transaction.getItems()) {
                auditLogger.log("CHECKOUT", item.getPart().getPartCode(), item.getQuantity());
            }

            showAlert("Checkout Complete", String.format(
                    "Subtotal: Rs. %.2f\nDiscount: Rs. %.2f\nTotal: Rs. %.2f",
                    transaction.getSubtotal(), transaction.getDiscountApplied(), transaction.getTotal()));

            cartList.clear();
            updateCartTotals();

            partTable.refresh();
            posPartTable.refresh();
            updateSummary();
            updateLowStockWarning();

        } catch (IllegalArgumentException e) {
            showAlert("Checkout Failed", e.getMessage());
        }
    }

    private void updateCartTotals() {
        CheckoutService checkoutService = new CheckoutService();

        double subtotal = checkoutService.calculateSubtotal(cartList);
        double discount = cartList.isEmpty() ? 0 : checkoutService.calculateDiscount(cartList);
        double total = subtotal - discount;

        cartSubtotalLabel.setText(String.format("Subtotal: Rs. %.2f", subtotal));
        cartDiscountLabel.setText(String.format("Discount: Rs. %.2f", discount));
        cartTotalLabel.setText(String.format("Total: Rs. %.2f", total));
    }
}