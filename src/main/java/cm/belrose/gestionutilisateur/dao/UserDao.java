/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.dao;

import cm.belrose.gestionutilisateur.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author PC-NGNAWEN
 * Comme j'ai implementer l'interface JpaRepository plus besoin d'implementer les methodes CRUD par consequant pas besoin
 * de la classe UserDaoImpl
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    //@Query(value = "select u from User u where u.login = ?1")
    Optional<User> findByLogin(String loginParam);

    //	@Query(value = "select user from User user where user.login = ?1 and user.password = ?2")
    User findByLoginAndPassword(String login, String password); //méthode non utilisée pour le moment

}
