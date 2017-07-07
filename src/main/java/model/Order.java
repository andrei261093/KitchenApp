package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONArray;
import org.json.JSONObject;
import staticUtils.StaticUtilsVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itsix on 6/26/2017.
 */
public class Order {
    private List<Product> products;
    private String tableNo;
    private JSONObject orderJson;
    private String status = StaticUtilsVariables.ORDER_STATUS_NONE;

    public Order(JSONObject orderJson) {
        this.tableNo = "Table Number: " + orderJson.getInt("tableNo");
        this.orderJson = orderJson;
        products = new ArrayList<Product>();

        JSONArray productsJson = orderJson.getJSONArray("products");
        for (int i = 0; i < productsJson.length(); i++) {
            JSONObject product = productsJson.getJSONObject(i);
            products.add(new Product(product.getString("name"), product.getString("shortDescription"), product.getString("longDescription"), product.getInt("price") + "", product.getInt("weight") + ""));
        }
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public JSONObject getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(JSONObject orderJson) {
        this.orderJson = orderJson;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
