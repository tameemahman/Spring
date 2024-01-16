package com.example.SpringBootSecurity66.controller;

import com.example.SpringBootSecurity66.model.Role;
import com.example.SpringBootSecurity66.model.Token;
import com.example.SpringBootSecurity66.model.User;
import com.example.SpringBootSecurity66.repository.IRoleRepo;
import com.example.SpringBootSecurity66.repository.ITokenRepo;
import com.example.SpringBootSecurity66.repository.IUserRepository;
import com.example.SpringBootSecurity66.util.FileUploadUtil;
import com.sun.security.auth.UserPrincipal;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    IUserRepository userRepo;
    @Autowired
    IRoleRepo roleRepo;

    @Autowired
    ITokenRepo tokenRepo;


    @Autowired
    JavaMailSender javaMailSender;

     long startTime;


    @GetMapping("admin/user/all")
    public String allUser(Model m){
        List<User> userList=userRepo.findAll();
        m.addAttribute("userList", userList);
        return "alluser";

    }

    @RequestMapping("public/user/saveform")
    public String userSaveForm(Model m){
        m.addAttribute("user", new User());
        return  "saveuserform";
    }

    @GetMapping("/public/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") int id) throws IOException {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Assuming user.getImage() contains the file name
            String uploadDirectory = "src/main/resources/static/assets/images/user/";
            String fileName = user.getImage();
            String filePath = Paths.get(uploadDirectory, fileName).toString();

            try {
                Path path = Paths.get(filePath);
                byte[] imageBytes = Files.readAllBytes(path);

                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + path.getFileName().toString())
                        .body(imageBytes);
            } catch (IOException e) {
                // Handle exceptions, log, or return an error response
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("public/user/save")
    public String save(@ModelAttribute @Validated User user, BindingResult result, @RequestParam("image") MultipartFile image) throws IOException, SQLException, MessagingException {

        long s=System.currentTimeMillis();
         startTime =s+200000l;

 //       Start Image
        if (!image.isEmpty()) {
            byte[] bytes = image.getBytes();
            String originalFilename = image.getOriginalFilename();

            // Generate a timestamp to make the filename unique
            long timestamp = System.currentTimeMillis();

            // Extract file extension from the original filename
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Create a new unique filename using timestamp
            String newFileName = "user_image_" + timestamp + fileExtension;

            user.setImage(newFileName);

            // Save the image to the specified directory
            String uploadDirectory = "src/main/resources/static/assets/images/user/";
            Path uploadPath = Paths.get(uploadDirectory);

            // Create the directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);
            Files.write(filePath, bytes);
        }

//        end Image
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole=new Role(1);
        user.addRole(userRole);


        userRepo.save(user);

        Token token=new Token(user);
        tokenRepo.save(token);

        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setTo(user.getEmail());


        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
//                   "Dear " + ccm.getCcCompanyName() + "\n" + "To confirm your account, please click here :" +

                "<div>Welcome <b>" + user.getName() + "</b></div>\n" +
                "\n" +
                "<div>Your Token is  <b>  </b></div>\n" +
                "<div>Plaese Click Here <b> "+ "http://localhost:8088/public/confirm-account?token="+ token.getConfirmationToken() +" </b></div>\n" +
                "<div> Your username is <b>" + " " + "</b></div>\n" +
                "<div> Any Information Please Call <b>" + " 123456789 " + "</b></div>\n" +
                "</body>\n" +
                "</html>\n";

        message.setSubject("Confirm Registration");
        message.setFrom("info@emranhss.com");

        message.setText(html, true);
        javaMailSender.send(mimeMessage);

        return "redirect:/";
    }




    @RequestMapping(value = "/public/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmCorporatUser(@RequestParam("token") String token, Model m) {
        Token confirmationToken = tokenRepo.findByConfirmationToken(token);
        if(token != null){
            long endTime=System.currentTimeMillis();
            System.out.println("Start "+startTime);
            System.out.println("End "+endTime);
            if (startTime>endTime){
               User user= userRepo.findByEmail(confirmationToken.getUser().getEmail());
                System.out.println("+++++++++++++++++++");
               user.setEnable(true);
                System.out.println("--------------------");

               userRepo.save(user);
               m.addAttribute("message","Account Verified" );
            }else{
                System.out.println("Time-----------Out");
            }
        } else {
            m.addAttribute("message", "The link is invalid or broken!");
        }
        return "redirect:/";
    }


    @GetMapping("admin/user/profile")
    public String showUserProfile(Model model, Authentication authentication) {
        // Get the currently authenticated user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Access user details
        model.addAttribute("user", user);

        return "userprofile";

    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password are invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login1";
    }

    @GetMapping("/logout")
    public String logout() {
        // You can perform any additional logout-related logic here
        // (if needed)

        // Redirect to the login page after logout
        return "redirect:/login?logout";
    }



}
