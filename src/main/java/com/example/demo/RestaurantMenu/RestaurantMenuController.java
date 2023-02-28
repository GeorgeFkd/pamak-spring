package com.example.demo.RestaurantMenu;

import java.util.List;
import com.example.demo.RestaurantMenu.RestaurantMenuEntry;
import com.example.demo.RestaurantMenu.RestaurantMenuEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/menu")
@CrossOrigin
public class RestaurantMenuController {

    private final RestaurantMenuEntryService menuService;
    @Autowired
    public RestaurantMenuController(RestaurantMenuEntryService menuService){this.menuService = menuService;}


    //generally handle error cases

    @GetMapping
    public ResponseEntity<List<RestaurantMenuEntry>> getRestaurantMenus(){
        List<RestaurantMenuEntry> entries = menuService.getRestaurantMenus();

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> addNewMenuEntry(@RequestBody RestaurantMenuEntry entry){
        System.out.println(entry);
        menuService.addRestaurantMenuEntry(entry);

        return new ResponseEntity<>("Added to database: \n"+entry.getMenuDate().getMonth() + entry.getMenuDate().getDayOfWeek() + entry.getMenuDate().getDayOfYear(),HttpStatus.OK );
    }

    @DeleteMapping(path="/{menuId}")
    public ResponseEntity<String> deleteMenuEntry(@PathVariable("menuId") Long menuId){
        try{
            menuService.deleteMenuEntry(menuId);
        }catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Menu with id: " + menuId + "was not found",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Deleted menu entry with ID: " + menuId,HttpStatus.OK);
    }

    @PutMapping(path="/{menuId}")
    public ResponseEntity<String> updateMenuEntry(@PathVariable("menuId") Long menuId,@RequestBody RestaurantMenuEntry entry){
        try{
            menuService.updateMenuEntry(menuId,entry);
        }catch (ChangeSetPersister.NotFoundException e){
            return new ResponseEntity<>("Menu with Id " + menuId +  " was not found",HttpStatus.NOT_FOUND);
        }finally {
            System.out.println("Executed put api/v1/menu/" + menuId);
        }

        return new ResponseEntity<>("Updated Menu with id: " + menuId,HttpStatus.OK);

    }

}
