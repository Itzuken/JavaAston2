package user_service;

import user_service.models.User;
import user_service.services.UserService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        User user1 = new User("Sergey", "sergey.ereshko@mail.ru", 34, LocalDateTime.now());
        User user2 = new User("Kirill", "kirill.karin@mail.ru", 25, LocalDateTime.of(2019, Month.APRIL, 28,15,33));
        User user3 = new User("Mihail", "mihail.muhin@mail.ru", 16, LocalDateTime.of(2020, Month.JANUARY, 13,12,00));
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        user2.setAge(43);
        userService.updateUser(user2);
        userService.findUser(2);
        userService.deleteUser(user3);

        /* код БД
        create table users(
        ID serial primary key,
        name varchar not null,
        email varchar not null,
        age int not null,
        created_at timestamp not null
        );
*/
    }
}
