package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.AdminRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.AdminResponse;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Admin;
import Phoenix.AirlineReservationSystem.Repository.AdminRepo;
import Phoenix.AirlineReservationSystem.Transformer.AdminTransformer;
import Phoenix.AirlineReservationSystem.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;
    @Autowired
    JwtUtil jwtUtil;
    public AdminResponse addAdmin(AdminRequest adminRequest){
        Admin admin= AdminTransformer.adminRequestToAdmin(adminRequest);
        Admin newAdmin=adminRepo.save(admin);
        return AdminTransformer.adminToAdminResponse(newAdmin);
    }
    public AdminResponse getAdimn(HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Admin admin=adminRepo.findByUsername(username);
        if(admin==null){
            throw  new ResourceNotFoundException("Admin not found");
        }
        AdminResponse adminResponse=AdminTransformer.adminToAdminResponse(admin);
        return  adminResponse;
    }
    public Page<AdminResponse> getAllAdmin(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Admin>list=adminRepo.findAll(pageable);
        return list.map(AdminTransformer::adminToAdminResponse);
    }
    @Transactional
    public AdminResponse deleteAdminById(int id){
        Optional<Admin> admin=adminRepo.findByAdminId(id);
        if(admin.isEmpty()){
            throw  new ResourceNotFoundException("Admin not found");
        }
        adminRepo.deleteByAdminId(id);
        AdminResponse adminResponse=AdminTransformer.adminToAdminResponse(admin.get());
        return  adminResponse;

    }
    @Transactional
    public AdminResponse updateAdmin(HttpServletRequest request,AdminRequest adminRequest){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Admin admin=adminRepo.findByUsername(username);
        if(admin==null){
            throw  new ResourceNotFoundException("Admin not found");
        }
        AdminTransformer.updateAdminFromRequest(admin,adminRequest);
        adminRepo.save(admin);
        return AdminTransformer.adminToAdminResponse(admin);
    }
}
