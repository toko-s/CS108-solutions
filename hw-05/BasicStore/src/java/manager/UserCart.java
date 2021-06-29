package manager;

import java.util.Collection;
import java.util.HashMap;

public class UserCart {
    private HashMap<Item,Integer> cartData;
    private double totalPrice;

    public UserCart(){
        cartData = new HashMap<>();
        totalPrice = 0;
    }

    public void add(Item item){
        cartData.put(item,cartData.getOrDefault(item,0) + 1);
        totalPrice += Double.parseDouble(item.price);
    }

    public void update(Item item, int val){
        int old = cartData.get(item) - val;
        if(val == 0)
            cartData.remove(item);
        else
            cartData.put(item, val);
        totalPrice -= old * Double.parseDouble(item.price);
    }

    public Collection<Item> getItems(){
        return cartData.keySet();
    }

    public Collection<Item> getCopyOfItem(){
        return ((HashMap)cartData.clone()).keySet();
    }

    public int getValue(Item item){
        return cartData.get(item);
    }

    public double getTotalPrice(){
        return totalPrice;
    }
}
