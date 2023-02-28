package com.example.demo.RestaurantMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.asm.Advice;
import java.time.*;
import java.time.DayOfWeek;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "restaurant_menu_entry")
public class RestaurantMenuEntry {
    @Id
    @SequenceGenerator(name = "restaurant_menu_sequence",sequenceName = "restaurant_menu_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurant_menu_sequence")
    private Long id;


    // Somehow I want to differentiate between lunch and dinner
    // there is a chance i want that in an abstract class

    @ElementCollection(targetClass = String.class)
    private List<String> mainCourses;
    private LocalDate menuDate;

    //    private String sideDish;
    @Column(name="dessert")
    private String dessert;

    // private String specialOption;
    @Column(name="salad")
     private String salad;


//    public RestaurantMenuEntry(List<String> mainCourses, String sideDish,String dessert, String specialOption,String salad){
//        this.mainCourses = mainCourses;
//        this.sideDish = sideDish;
//        this.dessert = dessert;
//        this.specialOption = specialOption;
//        this.salad = salad;
//    }

}

