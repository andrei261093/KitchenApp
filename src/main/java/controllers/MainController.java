package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Order;
import model.Product;
import org.json.JSONObject;
import staticUtils.StaticUtilsVariables;
import tcpClient.TcpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainController {
    @FXML
    private Label statusLbl;

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
    private Thread myThread;
    private List<Order> orderList;
    private Stage connectStage;

    @FXML
    public void initialize() {
        tcpClient = new TcpClient(this);
        myThread = new Thread(tcpClient);
        orderList = new ArrayList<Order>();

        orderTableIndexColumn.setCellFactory(new LineNumbersCellFactory());
        orderTablelNoColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNo"));

        productTableIndexColumn.setCellFactory(new LineNumbersCellFactory());
        productTableINameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productTableIDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("shortDescription"));

        statusLbl.setTextFill(Color.RED);

        orderTable.setRowFactory(tv -> new TableRow<Order>() {
            @Override
            public void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getStatus().equals(StaticUtilsVariables.ORDER_STATUS_DONE)) {
                    setStyle("-fx-background-color: #a5d6a7;");
                } else if (item.getStatus().equals(StaticUtilsVariables.ORDER_STATUS_IN_PROOGRESS)) {
                    setStyle("-fx-background-color: #ffb74d;");
                }
            }
        });

        Set<Node> cells = orderTable.lookupAll(".table-cell");
    }

    @FXML
    void markOrderAsDone(ActionEvent event) {

        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order.getStatus().equals(StaticUtilsVariables.ORDER_STATUS_IN_PROOGRESS)) {
            order.setStatus(StaticUtilsVariables.ORDER_STATUS_DONE);

            orderTable.refresh();

            sendToServer(order.getOrderJson());
        }
    }

    private void sendToServer(JSONObject orderJson) {
        tcpClient.sentToServer(orderJson.toString());
    }

    @FXML
    void removeOrderFromTable(ActionEvent event) {
        //remove order from list
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order.getStatus().equals(StaticUtilsVariables.ORDER_STATUS_DONE)) {
            orderList.remove(order);
        }
        ObservableList<Order> dataOrder = FXCollections.observableArrayList(orderList);
        orderTable.setItems(dataOrder);

        ObservableList<Product> dataProduct = FXCollections.observableArrayList();
        productTable.setItems(dataProduct);

        productTable.refresh();
        orderTable.refresh();
    }

    @FXML
    public void connectToServer(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/connectWindow.fxml"));
            Stage connectStage = new Stage();
            Parent root2 = loader.load();
            connectStage.setTitle("Licenta Andrei Iorga 2017");
            connectStage.setScene(new Scene(root2));
            ConnectWindowController connectWindowController = loader.getController();
            connectWindowController.setMainController(this);
            connectStage.show();
            this.connectStage = connectStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // tcpClient.sentToServer(":connected - from kitchen:chat");
    }

    public void connectToServer() {
        connectStage.close();
        Thread thread = new Thread(tcpClient);
        myThread = thread;
        myThread.start();
    }

    public void addOrder(JSONObject orderJson) {
        orderList.add(new Order(orderJson));

        ObservableList<Order> dataOrder = FXCollections.observableArrayList(orderList);
        orderTable.setItems(dataOrder);
    }

    public void showProducts(MouseEvent mouseEvent) {
        Order order = orderTable.getSelectionModel().getSelectedItem();

        ObservableList<Product> productData = FXCollections.observableArrayList(order.getProducts());
        productTable.setItems(productData);
    }

    public void markOrderInProgress(ActionEvent actionEvent) {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if(!order.getStatus().equals(StaticUtilsVariables.ORDER_STATUS_DONE)){
            order.setStatus(StaticUtilsVariables.ORDER_STATUS_IN_PROOGRESS);
        }

        orderTable.refresh();
    }

    public void disconnectAction(ActionEvent actionEvent) {
        disconnect();
    }

    public void disconnect(){
        if (tcpClient.isConnected) {
            tcpClient.disconnect();
            myThread.interrupt();
        }
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

    public void setStatus(String status) {
        statusLbl.setText(status);
        if (status.equals(StaticUtilsVariables.ONLINE_STATUS)) {
            statusLbl.setTextFill(Color.GREEN);
        } else if (status.equals(StaticUtilsVariables.OFFLINE_STATUS)) {
            statusLbl.setTextFill(Color.RED);
        } else if (status.equals(StaticUtilsVariables.CONNECTING_STATUS)) {
            statusLbl.setTextFill(Color.ORANGE);
        }
    }
}
