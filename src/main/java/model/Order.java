package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itsix on 6/26/2017.
 */
public class Order {
    private List<Product> products;
    private int tableNo;
    private JSONObject orderJson;

    public Order(JSONObject orderJson) {
        this.tableNo = orderJson.getInt("tableNo");
        this.orderJson = orderJson;
        products = new ArrayList<Product>();

        JSONArray productsJson = orderJson.getJSONArray("products");
        for (int i = 0; i < productsJson.length(); i++) {
            JSONObject product = productsJson.getJSONObject(i);
            products.add(new Product(product.getString("name"), product.getString("shortDescription"), product.getString("longDescription"), product.getInt("price") + "", product.getInt("weight") + ""));
        }
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public JSONObject getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(JSONObject orderJson) {
        this.orderJson = orderJson;
    }
}