package com.nile.nile;

/**
 * Created by rieke on 02.07.16.
 */
public class NilePackage {

    private int id;
    private NileUser deliverer;
    private NileUser purchaser;
    private NileUser recipient;
    private String sender;
    private String status;
    private float estimatedDeliveryTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public NileUser getDeliver() { return deliverer; }
    public void setDeliver(NileUser deliverer) { this.deliverer = deliverer; }

    public NileUser getPurchaser() { return purchaser; }
    public void setPurchaser(NileUser purchaser) { this.purchaser = purchaser; }

    public NileUser getRecipient() { return recipient; }
    public void setRecipient(NileUser recipient) { this.recipient = recipient; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public float getEstimatedDeliveryTime() { return estimatedDeliveryTime; }
    public void setEstimatedDeliveryTime(float estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}
