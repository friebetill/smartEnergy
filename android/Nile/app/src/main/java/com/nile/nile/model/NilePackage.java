package com.nile.nile.model;

/**
 * Created by rieke on 02.07.16.
 */
public class NilePackage {

    private int id;
    private int deliver_id;
    private int purchaser_id;
    private int recipient_id;
    private String sender;
    private String status;
    private float estimatedDeliveryTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDeliver_id() { return deliver_id; }
    public void setDeliver_id(int deliver_id) { this.deliver_id = deliver_id; }

    public int getPurchaser_id() { return purchaser_id; }
    public void setPurchaser_id(int purchaser_id) { this.purchaser_id = purchaser_id; }

    public int getRecipient_id() { return recipient_id; }
    public void setRecipient_id(int recipient_id) { this.recipient_id = recipient_id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public float getEstimatedDeliveryTime() { return estimatedDeliveryTime; }
    public void setEstimatedDeliveryTime(float estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}
