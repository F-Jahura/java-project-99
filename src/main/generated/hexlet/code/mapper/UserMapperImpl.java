package hexlet.code.mapper;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.model.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T12:05:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    @Override
    public User map(UserCreateDTO dto) {
        encryptPassword( dto );

        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public UserDTO map(User model) {
        if ( model == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( model.getId() );
        userDTO.setFirstName( model.getFirstName() );
        userDTO.setLastName( model.getLastName() );
        userDTO.setEmail( model.getEmail() );
        userDTO.setCreatedAt( model.getCreatedAt() );

        return userDTO;
    }

    @Override
    public User map(UserDTO model) {
        if ( model == null ) {
            return null;
        }

        User user = new User();

        user.setId( model.getId() );
        user.setFirstName( model.getFirstName() );
        user.setLastName( model.getLastName() );
        user.setEmail( model.getEmail() );
        user.setCreatedAt( model.getCreatedAt() );

        return user;
    }

    @Override
    public void update(UserUpdateDTO dto, User model) {
        encryptPassword( dto );

        if ( dto == null ) {
            return;
        }

        if ( jsonNullableMapper.isPresent( dto.getFirstName() ) ) {
            model.setFirstName( jsonNullableMapper.unwrap( dto.getFirstName() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getLastName() ) ) {
            model.setLastName( jsonNullableMapper.unwrap( dto.getLastName() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getEmail() ) ) {
            model.setEmail( jsonNullableMapper.unwrap( dto.getEmail() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getPassword() ) ) {
            model.setPassword( jsonNullableMapper.unwrap( dto.getPassword() ) );
        }
    }
}
