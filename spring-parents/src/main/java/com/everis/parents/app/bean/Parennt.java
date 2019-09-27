package com.everis.parents.app.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * .
 * @author lriveras.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "parents")
public class Parennt {
  /**
 * ID.
 */
  @Id
  private String id;
  /**
 * fullname.
 */
  @NotEmpty(message = "'fullname' No debe ser vacio!")
 private String fullname;
  /**
 * gender.
 */
  @NotEmpty(message = "'gender' No debe ser vacio!")
 private String gender;
  /**
 * birthdate.
 */
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
 private Date birthdate;
  /**
 * typeID.
 */
  @NotEmpty(message = "'typeID' No debe ser vacio!")
 private String typeID;
  /**
 * numberID.
 */
  @NotEmpty(message = "'numberID' No debe ser vacio!")
  @Size(min = 8, max = 8,message = "'numberID' debe tener 8 caracteres")
 private String numberID;
  /**
 * createAt.
 */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
 private Date createdAt = new Date();
  /**
 * iidFamily.
 */
  @NotEmpty(message = "'idFamily' No debe ser vacio!")
 private String idFamily;

  /**
 * Test.
 */
  public Parennt(final String fullname,
      final String gender, 
      final Date birthdate,
      final String typeID,
      final String numberID,
      final String idFamily) {
    this.fullname = fullname;
    this.gender = gender;
    this.birthdate = birthdate;
    this.typeID = typeID;
    this.numberID = numberID;
    this.idFamily = idFamily;
  }


}
