package cn.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Setter
//@Getter
//@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

  private String id;
  private String username;
  private String password;
  private long status;

}
