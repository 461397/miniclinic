package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.edu.fju.miniclinic.model.Patient;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepo;

    // 給人類看的網頁
    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepo.findAll());
        return "patients";
    }

    // 給程式看的 API
    @GetMapping("/api/patients")
    @ResponseBody
    public List<Patient> getApiPatients() {
        return patientRepo.findAll();
    }
}