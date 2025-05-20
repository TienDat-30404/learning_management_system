package com.example.user_service.service.user;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.dto.user.UserUpdateDTO;
import com.example.user_service.dto.CustomPageDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    public CustomPageDTO<UserResponseDTO> getAllUsers(Pageable pageable) ;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) ;

    public UserResponseDTO getUserById(Long id);

    public boolean existsById(Long id);

    public List<UserResponseDTO> getUserByIds(List<Long> ids);

    public UserResponseDTO updateUser(Long id, UserUpdateDTO request);
}
