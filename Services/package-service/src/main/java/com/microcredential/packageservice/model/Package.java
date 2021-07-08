package com.microcredential.packageservice.model;

import java.util.List;

public class Package {


    private int packageId;

    private Address address;

    private List<Item> listOfItems;

    public Package() {
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Item> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }

    @Override
    public String toString() {
        return "Package{" +
                "packageId=" + packageId +
                ", address=" + address +
                ", listOfItems=" + listOfItems +
                '}';
    }
}
