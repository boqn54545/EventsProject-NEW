package com.example.EventsProject.Controllers;

import com.example.EventsProject.Enums.InterestsEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Controller
//public class AdminController {
//
 //   private List<InterestsEnum> interests = new ArrayList<>(Arrays.asList(InterestsEnum.values()));

  //  @GetMapping("/interests")
  //  public String interests(Model model) {
     //   model.addAttribute("interests", interests);
   //     return "interests";
  //  }

  //  @PostMapping("/interests/delete")
  //  public String deleteInterest(@RequestParam("interest") String interest, Model model) {
   //     InterestsEnum selectedInterest = null;
  //      for (InterestsEnum intEnum : interests) {
     //       if (intEnum.name().equals(interest)) {
     //           selectedInterest = intEnum;
     //           break;
     //       }
   //     }

    //    if (selectedInterest != null) {
     //       interests.remove(selectedInterest);
       //     model.addAttribute("message", "Deleted interest: " + interest);
       // } else {
       //     model.addAttribute("errorMessage", "Could not delete interest: " + interest + ", interest not found");
      //  }
      //  model.addAttribute("interests", interests);
      //  return "interests";
  //  }

   // @PostMapping("/interests/create")
   // public String createInterest(@RequestParam("interest") String interest, Model model) {
     ///   InterestsEnum selectedInterest = null;
      ////  for (InterestsEnum intEnum : interests) {
      //      if (intEnum.name().equalsIgnoreCase(interest)) {
      //          selectedInterest = intEnum;
      //          break;
      //      }
      //  }

     //   if (selectedInterest != null) {
     ///       model.addAttribute("errorMessage", "Could not create interest: " + interest + ", interest already exists");
    //    } else {
     ///       InterestsEnum newInterest = InterestsEnum.valueOf(interest.toUpperCase());
      ///      interests.add(newInterest);
     // /      model.addAttribute("message", "Created new interest: " + interest);
       /// }
   //   model.addAttribute("interests", interests);
   //     return "interests";
   // }
///}
