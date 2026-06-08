package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

@Controller
public class PasswordController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/password")
    public String showPasswordForm(HttpSession session, Model model) {
        model.addAttribute("loggedInDoctorName", session.getAttribute("loggedInDoctorName"));
        return "password";
    }

    @PostMapping("/password")
    public String updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session, Model model) {
        String loggedInId = (String) session.getAttribute("loggedInDoctorId");
        Doctor doctor = doctorRepo.findById(loggedInId).orElse(null);

        if (doctor == null || !BCrypt.checkpw(oldPassword, doctor.getPasswordHash())) {
            model.addAttribute("errorMessage", "舊密碼錯誤");
            return "password";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "兩次密碼不相符");
            return "password";
        }
        if (newPassword.length() < 8) {
            model.addAttribute("errorMessage", "密碼至少需要 8 個字元");
            return "password";
        }

        doctor.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        doctorRepo.save(doctor);
        model.addAttribute("successMessage", "密碼修改成功");
        return "password";
    }
}