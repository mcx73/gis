package ru.mcx73.gis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.RoleRepository;
import ru.mcx73.gis.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

 /*
  Содержит методы для бизнес-логики приложения
 */

@Service
public class UserService implements UserDetailsService {
    /*
     работает режим «один Entity Manager на одну транзакцию приложения».
     */
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /*
    saveUser(User user)
    Сначала происходит поиск в БД по имени пользователя, если пользователь с таким именем уже существует метод заканчивает работу.
    Если имя пользователя не занято, добавляется роль ROLE_USER. Чтобы не хранить пароль в «сыром» виде он предварительно
    хэшируется с помощью bCryptPasswordEncoder. Затем новый пользователь сохраняется в БД.
     */

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        List<Role> list = roleService.AllRole();
        Role role;

        if(list == null || list.size() == 0){
            roleService.saveUserEndRoles();
            role = new Role(1L, "ROLE_USER");
        }else{
            role = new Role(3L, "ROLE_USER");
        }


        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
