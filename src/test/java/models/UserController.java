package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserController {

    private int id;
    private String name;
    private String address;

    private long marks;
    public UserController(){

    }

    public UserController(String name, String address, long marks) {
        this.name = name;
        this.address = address;
        this.marks = marks;


    }
    public UserController(int id,String name, String address, long marks) {
        this.name = name;
        this.address = address;
        this.marks = marks;
        this.id=id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 //   public String getName() {return name;}

   // public void setName(String name) {
    //    this.name = name;
  //  }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMarks() {
        return marks;
    }

    public void setMarks(long marks) {
        this.marks = marks;
    }


}