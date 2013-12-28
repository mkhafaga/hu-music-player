package com.xeeapps.mappers;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 20/12/13
 * Time: 05:51 ص
 * To change this template use File | Settings | File Templates.
 */
public class Mapper {
    protected String name;
    protected int id;


    public Mapper(String name,int id){
        this.name =  name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
