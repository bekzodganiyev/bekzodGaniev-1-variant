package uz.pdp.b7begzodganievexam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.b7begzodganievexam.entity.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByUsername(String username);


}
