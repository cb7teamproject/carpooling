package com.carpooling.controllers;

import com.carpooling.entities.Trip;
import com.carpooling.entities.User;
import com.carpooling.services.TripService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TripController {

@Autowired
TripService tripService;

@RequestMapping(value = "/inserttrip", method = RequestMethod.GET)
public String insertTrip(ModelMap mm) {
    Trip trip = new Trip();
    mm.addAttribute("trip", trip);
    return "inserttrip";
}

@RequestMapping(value = "/doinserttrip", method = RequestMethod.POST)
public String doinsertTrip(ModelMap mm, @ModelAttribute("newtrip") Trip trip,BindingResult br) {
    if (br.hasErrors()) {
        return "home";
       } else {
   tripService.insert(trip);
   return"selectaride";
   }
}

@RequestMapping(value = "getalltrips", method = RequestMethod.GET)
public String allTrips(ModelMap mm) {
   List<Trip> trips = tripService.findAllTrips();
   mm.put("trips", trips);
   return "alltrips";
}

@RequestMapping(value = "/updatetrip/{id}", method = RequestMethod.GET)
public String updateTrip(ModelMap mm, @PathVariable int id) {
    Trip oldtrip = tripService.findTripByID(id);
    Trip newtrip = new Trip();
    mm.addAttribute("newtrip", newtrip);
    mm.addAttribute("oldtrip", oldtrip);
    return "updatetrip";
}

@RequestMapping(value = "/updatetrip/doupdatetrip", method = RequestMethod.POST)
public String doupdateTrip(ModelMap mm, @ModelAttribute("newtrip") Trip newtrip) {
    tripService.update(newtrip);
    return "redirect:/alltrips";
}

@RequestMapping(value = "/deletetrip/{id}", method = RequestMethod.GET)
public String deleteTrip(ModelMap mm, @PathVariable int id) {
    tripService.deleteTripByID(id);
    return "redirect:/alltrips";
}

@RequestMapping(value = "/takethetrip", method = RequestMethod.GET)
public String takeTrip(ModelMap mm){
Trip trip = new Trip();
mm.addAttribute("trip",trip);
return "takethetrip";
}

@RequestMapping(value = "/dotaketrip/{tripid}", method = RequestMethod.GET)
public String dotakeTrip(ModelMap mm, @PathVariable int tripid,HttpSession session) {
    Trip trip = tripService.findTripByID(tripid);
    List <User> utl = new ArrayList();
    utl.add((User) session.getAttribute("loggedinuser"));
    trip.setUserList(utl);
    tripService.update(trip);
    return"redirect:/getalltrips";
}

}