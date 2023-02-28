package com.example.demo.RestaurantMenu;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantMenuEntryService {

    private final RestaurantMenuEntryRepository restaurantMenuEntryRepository;

    @Autowired
    public RestaurantMenuEntryService(RestaurantMenuEntryRepository restaurantMenuEntryRepository){
        this.restaurantMenuEntryRepository = restaurantMenuEntryRepository;
    }

    public List<RestaurantMenuEntry> getRestaurantMenus(){
        return this.restaurantMenuEntryRepository.findAll();
    }

    public RestaurantMenuEntry getNextMealMenu(){
        //adjust it with time
        return null;
    }

    @Transactional
    public RestaurantMenuEntry addRestaurantMenuEntry(RestaurantMenuEntry menuEntry){
        //potential for business logic: if today's meals are already added etc
        System.out.println("Menu Entry going to db: "+ menuEntry);
        restaurantMenuEntryRepository.save(menuEntry);
        return menuEntry;
    }
    @Transactional
    public void deleteMenuEntry(Long menuEntryId) throws EmptyResultDataAccessException {
        //boolean menuEntryExists;
        System.out.println("Deleting menu entry with id " + menuEntryId);

            restaurantMenuEntryRepository.deleteById(menuEntryId);

    }

    @Transactional
    public RestaurantMenuEntry updateMenuEntry(Long menuEntryId,RestaurantMenuEntry menuEntry) throws ChangeSetPersister.NotFoundException{
        RestaurantMenuEntry entryFromDB = restaurantMenuEntryRepository.findById(menuEntryId).orElseThrow(()-> new ChangeSetPersister.NotFoundException());
        //find a more compact way to do this getClass().getDeclared Fields
        if(!menuEntry.getDessert().isBlank()){
            entryFromDB.setDessert(menuEntry.getDessert());
        }

        if(!menuEntry.getMainCourses().isEmpty()){
            entryFromDB.setMainCourses(menuEntry.getMainCourses());
        }

        if(!menuEntry.getSalad().isBlank()){
            entryFromDB.setSalad(menuEntry.getSalad());
        }

        System.out.println("Updated Menu Entry with ID: " + menuEntryId);
        //if the object has id I can do just save and not all those above
        //if the object has id it just updates the existing one, if not, adds an additional row.

        restaurantMenuEntryRepository.save(entryFromDB);
        return entryFromDB;
    }




}
