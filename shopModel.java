package sample;

public class shopModel {

     String id, name, brand, category, price;

     public shopModel(String id, String name,String brand,String category,String price){
         this.id=id;
         this.name=name;
         this.brand=brand;
         this.category=category;
         this.price=price;
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
