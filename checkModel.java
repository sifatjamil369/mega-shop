package sample;

public class checkModel {

    String id, user_id, product_id, approval;

    public checkModel(String id, String user_id, String product_id, String approval) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.approval= approval;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }
}