package com.zs.ee;

public class A {
     private String id;
     private String name;
     private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public A() {
    }

    public A(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
}
