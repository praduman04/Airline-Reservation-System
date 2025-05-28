package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.AdminRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.AdminResponse;
import Phoenix.AirlineReservationSystem.Model.Admin;
import Phoenix.AirlineReservationSystem.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PostMapping("/addAdmin")
    public ResponseEntity<AdminResponse> addAdmin(@RequestBody AdminRequest adminRequest){
        AdminResponse adminResponse=adminService.addAdmin(adminRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getById")
    public ResponseEntity<AdminResponse>getAdminById(@RequestParam int adminId){
        AdminResponse adminResponse=adminService.getAdimn(adminId);
        return new ResponseEntity<>(adminResponse,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<Page<AdminResponse>> getAllAdmin(@RequestParam int page,@RequestParam int size){
        Page<AdminResponse> adminList=adminService.getAllAdmin(page,size);
        return new ResponseEntity<>(adminList,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<AdminResponse> deleteAdmin(@RequestParam int adminId){
        AdminResponse adminResponse=adminService.deleteAdminById(adminId);
        return new ResponseEntity<>(adminResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<AdminResponse>updateAdmin(@RequestParam int adminId,@RequestBody AdminRequest adminRequest){
        AdminResponse adminResponse=adminService.updateAdmin(adminId,adminRequest);
        return new ResponseEntity<>(adminResponse,HttpStatus.ACCEPTED);
    }
}
