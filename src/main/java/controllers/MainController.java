package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Order;
import model.Product;
import org.json.JSONObject;
import tcpClient.TcpClient;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Product, String> productTableIDescriptionColumn;

    @FXML
    private TableColumn<Order, String> orderTablelNoColumn;

    @FXML
    private TableColumn<Product, String> productTableINameColumn;

    @FXML
    private TableColumn<Order, String> orderTableIndexColumn;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> productTableIndexColumn;

    private TcpClient tcpClient;
    private List<Order> orderList;

    @FXML
    public void initialize(){
        tcpClient = new TcpClient(this);
        orderList = new ArrayList<Order>();

        orderTableIndexColumn.setCellFactory(new LineNumbersCellFactory());
        orderTablelNoColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNo"));

        productTableIDescriptionColumn.setCellFactory(new LineNumbersCellFactory());
        productTableINameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productTableIndexColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("shortDescription"));
    }

    @FXML
    void markOrderAsDone(ActionEvent event) {
        //mark order as done
    }

    @FXML
    void removeOrderFromTable(ActionEvent event) {
        //remove order from list
    }
    @FXML
    public void connectToServer(ActionEvent actionEvent) {
        tcpClient.connect();
        tcpClient.sentToServer("connected - from kitchen");
    }

    public void addOrder(JSONObject orderJson){
        orderList.add(new Order(orderJson));

        ObservableList<Order> dataOrder = FXCollections.observableArrayList(orderList);
        orderTable.setItems(dataOrder);
    }

    public void showProducts(MouseEvent mouseEvent) {
        Order order = orderTable.getSelectionModel().getSelectedItem();

        ObservableList<Product> productData = FXCollections.observableArrayList(order.getProducts());
        productTable.setItems(productData);
    }

    public class LineNumbersCellFactory<T, E> implements Callback<TableColumn<T, E>, TableCell<T, E>> {
        public LineNumbersCellFactory() {
        }


        public TableCell<T, E> call(TableColumn<T, E> param) {
            return new TableCell<T, E>() {
                @Override
                protected void updateItem(E item, boolean empty) {
                    super.updateItem(item, empty);

                    if (!empty) {
                        setText(this.getTableRow().getIndex() + 1 + "");
                    } else {
                        setText("");
                    }
                }
            };
        }
    }
}
